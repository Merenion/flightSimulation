package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.Coordinate;
import UIRS.flightSimulation.program1.MathModel.MathModel;
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

    //берем данные с предыдущей формы.
    private static MathModel mathModel = CharacteristicsW.getMathModel();
    private float timeBegin = CharacteristicsW.getTime();
    private Task<Void> task;

    //берем обьекты с формы для рисования и вывода данных
    @FXML
    public TextArea idAreaTimeOfLight;
    @FXML
    public Pane idPane;

    private static volatile Coordinate coordinate;
    private static volatile Coordinate coordinateNew;

    @FXML
    public void initialize() {
        mathModel.rashetPorb();   //рассчет начальных параметров орбиты
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
                coordinate = mathModel.flyModel(timeBegin);
                for (float i = timeBegin; i < 100000; i++) {

                    if (isCancelled()) {
                        updateMessage("Рисование прервано");
                        return;
                    }

                    Platform.runLater(new Runnable() {
                        float i;

                        @Override
                        public void run() {
                            Path path = new Path();
                            path.setStrokeWidth(2);
                            path.setStroke(Color.YELLOW);
                            MoveTo moveTo = new MoveTo(coordinate.getLambda(), coordinate.getFi());
                            coordinateNew = mathModel.flyModel(i);
                            LineTo lineTo = new LineTo(coordinateNew.getLambda(), coordinateNew.getFi());
                            path.getElements().addAll(moveTo, lineTo);
                            idPane.getChildren().add(path);
                            coordinate = coordinateNew;
                        }

                        Runnable param(float i) {
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
//            MoveTo moveTo = new MoveTo(coordinate.getLambda(), coordinate.getFi());
//            coordinateNew = mathModel.flyModel(i);
//            LineTo lineTo = new LineTo(coordinateNew.getLambda(), coordinateNew.getFi());
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
