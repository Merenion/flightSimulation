package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.Coordinate;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class DrawTrack {

    private static volatile Coordinate coordinate;
    private static volatile Coordinate coordinateNew;

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
}
