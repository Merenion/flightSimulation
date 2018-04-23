package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ImplSimulationFlight implements ISimulationFlight {
    /**реализация интерфейса ISimulationFlight, описание приведенно под сигнатурой интерфейса*/

    //получаем начальные (входные) параметр с формы характеристик
    protected InitialCharacteristics initCh = InitialCharacteristics.getInitialCharacteristics();
    //создаем математическую панель, из которой мы можем получить необходимые нам расчеты
    protected IMathModel mathModel = new ImplMathModel();
    //растояние до центра панели на которой рисуем (пока что задаем в ручную)
    protected CenterPane centerPane = new CenterPane(450, 228, 2.38, 2.38);
    //данное поле с координатами используется для рисования в другом потоке траектории
    protected static volatile Coordinate coordinate;

    protected int startTime = initCh.getStartTime();    //стартовое время симуляции
    protected int endTime = 10000000;                   //конечное время симуляции
    protected int lineWidth = 2;                        //ширина линии рисования траектории
    protected int stepTime = 7;                         //шаг по времени
    protected int drawingStep = 3;                      //шаг прорисовки по Х (в пикселях)
    protected Color colorTrack = Color.ORANGE;          //стандартный цвет рисования трассы

    protected Thread thread;                            //поток который симулирует полет
    protected Task<Void> task;                          //выполняемая задача потока - симуляция
    private boolean isRunSimulation = false;            //выполнение симуляции (false если поток остановлен или не создан)

    /**Выдает задачу которая рисует трассу полета КА и добавляет в Area поля тайминг и параметры орбиты/полета*/
    @Override
    public Task<Void> createSimulation(Pane idPane,CheckBox isShowIsTheSun, TextArea idTime, TextArea idParameters) {
        return new Task<Void>() {

            @Override
            protected Void call() {
                try {
                    draw();
                } catch (Exception ex) {
                    updateMessage(ex.getMessage());
                }
                return null;
            }

            private void draw() {
                Platform.runLater(() -> idPane.getChildren().clear());
                updateMessage("Рисование начато");
                coordinate = mathModel.getCoordinate2DMap(initCh.getStartTime(),centerPane);
                for (int i = startTime; i <= endTime; i += stepTime) {
                    if (isCancelled()) {
                        updateMessage("Рисование прервано");
                        return;
                    }

                    Platform.runLater(new Runnable() {

                        int i;

                        @Override
                        public void run() {
                            drawElement(idPane, i);
                            idTime.setText(mathModel.getTimingFlyght(i));
                            idParameters.setText(mathModel.getMainParameters(i));
                            if (isShowIsTheSun.isSelected()) {
                                if (mathModel.isInTheSun(i)) {
                                    colorTrack = Color.ORANGE;
                                    isShowIsTheSun.setTextFill(Color.ORANGE);
                                    isShowIsTheSun.setText("SUN");
                                } else {
                                    colorTrack = Color.PURPLE;
                                    isShowIsTheSun.setTextFill(Color.PURPLE);
                                    isShowIsTheSun.setText("SHADOW");
                                }
                            }

                        }

                        Runnable param(int i) {
                            this.i = i;
                            return this;
                        }
                    }.param(i));
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException interrupted) {
                        if (isCancelled()) {
                            updateMessage("Рисование прервано");
                            return;
                        }
                    }
                }
                updateMessage("Рисование завершено");
            }

            @Override
            protected void updateMessage(String message) {
                System.out.println(message);
                super.updateMessage(message);
            }

        };
    }

    /**Рисует элемент на форме - линию от ранее расчитанных координат, до заного посчитанных в этом метода*/
    protected void drawElement(Pane idPane, int i) {
        Coordinate coordinateNew = mathModel.getCoordinate2DMap(i,centerPane);
        Path path = new Path();
        path.setStrokeWidth(lineWidth);
        path.setStroke(colorTrack);
        MoveTo moveTo = new MoveTo(coordinate.getX(), coordinate.getY());
        if (coordinate.getX() - coordinateNew.getX() >= drawingStep || (coordinate.getX() < coordinateNew.getX())) {
            LineTo lineTo = new LineTo(coordinateNew.getX(), coordinateNew.getY());
            path.getElements().addAll(moveTo, lineTo);
            if (coordinate.getX() < coordinateNew.getX()) {
            } else {
                idPane.getChildren().add(path);
            }
            if (idPane.getChildren().size() > 800)
                idPane.getChildren().remove(0);
            coordinate = coordinateNew;
        }
    }

    /**Запустить новую симуляцию*/
    @Override
    public void startNewSimulation(Pane idPane, CheckBox isShowIsTheSun, TextArea idTime, TextArea idParameters) {
        if (task != null && task.isRunning()) {
            task.cancel();
        }
        isRunSimulation = true;
        task = createSimulation(idPane, isShowIsTheSun,idTime,idParameters);
        thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**Остановить симуляцию*/
    @Override
    public void stopSimulation() {
        if (isRunSimulation){
            thread.suspend();
            isRunSimulation = false;
        }
    }

    /**Возобновить симуляцию*/
    @Override
    public void resumeSimulation() {
        if (task.isRunning() && !isRunSimulation) {
            thread.resume();
            isRunSimulation = true;
        }
    }

    /**Выдает true если происходит симуляция полета КА, false если симуляция приостановлена или не начата*/
    @Override
    public boolean isRunSimulation () {
        return isRunSimulation;
    }

    /**Задает стартовое время, от которого начинается моделирование полета*/
    @Override
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**Задает конечное время - до которого моделируется полет*/
    @Override
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**Задает ширину прорисовки траекторию (ширину линии)*/
    @Override
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**Задает шаг моделирования по времени (в секундах)*/
    @Override
    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    /**Задает шаг прорисовки траектории по координате Х (чем меньше тем качественее изображение траектории)*/
    @Override
    public void setDrawingStep(int drawingStep) {
        this.drawingStep = drawingStep;
    }
}