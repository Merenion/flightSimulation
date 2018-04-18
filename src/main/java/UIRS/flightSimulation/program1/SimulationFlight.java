package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class SimulationFlight {

    private IMathModel mathModel;
    private InitialCharacteristics initCh = InitialCharacteristics.getInitialCharacteristics();
    private volatile static ITimeFlight flightTime;
    private Task<Void> task;

    @FXML
    public TextArea idAreaTimeOfLight;
    @FXML
    public Pane idPane;

    private static volatile Coordinate coordinate;
    private static volatile Coordinate coordinateNew;

    @FXML
    public void initialize() {
        mathModel = new MathModel( new CenterPane(450,228,2.38,2.38),initCh);
        mathModel.rashetPorb();   //рассчет начальных параметров орбиты
        flightTime = new TimeFlight();
    }

    private Task<Void> createTask() {
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
                coordinate = mathModel.flyModel(initCh.getStartTime());
                for (int i = initCh.getStartTime(); i < 100000; i+=1) {
                flightTime.setSecond(i);
                    if (isCancelled()) {
                        updateMessage("Рисование прервано");
                        return;
                    }

                    Platform.runLater(new Runnable() {

                        int i;

                        @Override
                        public  void run() {
                            Path path = new Path();
                            path.setStrokeWidth(1);
                            path.setStroke(Color.YELLOW);
                            MoveTo moveTo = new MoveTo(coordinate.getX(), coordinate.getY());
                            coordinateNew = mathModel.flyModel(i);
                            if (coordinate.getX()-coordinateNew.getX()>1 || (coordinate.getX() < coordinateNew.getX() )) {
                                LineTo lineTo = new LineTo(coordinateNew.getX(), coordinateNew.getY());
                                path.getElements().addAll(moveTo, lineTo);
                                if (coordinate.getX() < coordinateNew.getX()) {
                                    flightTime.addCoil(1);
                                } else {
                                    idPane.getChildren().add(path);
                                }
                                if (idPane.getChildren().size() > 2000)
                                    idPane.getChildren().remove(0);
                                coordinate = coordinateNew;
                            }
                            idAreaTimeOfLight.setText("timing:\n"+flightTime);

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

    public void onStart(ActionEvent actionEvent) {
        if (task != null && task.isRunning()) {
            task.cancel();
        }

        task = createTask();
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

//        label.textProperty().bind(task.messageProperty());
//        progressBar.progressProperty().bind(task.progressProperty());
//
//        buttonStart.disableProperty().bind(task.runningProperty());
//        buttonStop.disableProperty().bind(task.runningProperty().not());

//        Coordinate coordinateNew;
//        Coordinate coordinate = mathModel.flyModel(timeBegin); //координаты от которых рисуем линию впервый раз
//        for (float i = timeBegin + 1; i < 12200; i++) {
//            Path path = new Path();
//            path.setStrokeWidth(2);
//            path.setStroke(Color.YELLOW);
//            MoveTo moveTo = new MoveTo(coordinate.getX(), coordinate.getY());
//            coordinateNew = mathModel.flyModel(i);
//            LineTo lineTo = new LineTo(coordinateNew.getX(), coordinateNew.getY());
//            path.getElements().addAll(moveTo, lineTo);
//            idPane.getChildren().add(path);
//            coordinate = coordinateNew;
//        }
    }

    public void onStop(ActionEvent actionEvent) {
    }

    //этой кнопкой запускаем выше созданный поток (кнопка на форме назад) сделал так просто чтоб поэкспериментировать
    public void onBack(ActionEvent actionEvent) {
    }
}
