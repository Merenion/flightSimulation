package UIRS.flightSimulation.program1;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Draw {


//    private Task<Void> createTask() {
//        return new Task<Void>() {
//            @Override
//            protected Void call() {
//                try {
//                    draw();
//                } catch (Exception ex) {
//                    updateMessage(ex.getMessage());
//                }
//                return null;
//            }
//
//            private void draw() {
//
//                Platform.runLater(() -> idPane.getChildren().clear());
//                updateMessage("Рисование начато");
//                coordinate = mathModel.flyModel(initCh.getStartTime());
//                for (int i = initCh.getStartTime(); i < 100000; i+=1) {
//                    flightTime.setSecond(i);
//                    if (isCancelled()) {
//                        updateMessage("Рисование прервано");
//                        return;
//                    }
//
//                    Platform.runLater(new Runnable() {
//
//                        int i;
//
//                        @Override
//                        public  void run() {
//                            Path path = new Path();
//                            path.setStrokeWidth(1);
//                            path.setStroke(Color.YELLOW);
//                            MoveTo moveTo = new MoveTo(coordinate.getX(), coordinate.getY());
//                            coordinateNew = mathModel.flyModel(i);
//                            if (coordinate.getX()-coordinateNew.getX()>1 || (coordinate.getX() < coordinateNew.getX() )) {
//                                LineTo lineTo = new LineTo(coordinateNew.getX(), coordinateNew.getY());
//                                path.getElements().addAll(moveTo, lineTo);
//                                if (coordinate.getX() < coordinateNew.getX()) {
//                                    flightTime.addCoil(1);
//                                } else {
//                                    idPane.getChildren().add(path);
//                                }
//                                if (idPane.getChildren().size() > 2000)
//                                    idPane.getChildren().remove(0);
//                                coordinate = coordinateNew;
//                            }
//                            idAreaTimeOfLight.setText("timing:\n"+flightTime);
//
//                        }
//
//                        Runnable param(int i) {
//                            this.i = i;
//                            return this;
//                        }
//                    }.param(i));
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException interrupted) {
//                        if (isCancelled()) {
//                            updateMessage("Рисование прервано");
//                            return;
//                        }
//                    }
//                }
//                updateMessage("Рисование завершено");
//            }
//
//
//
//            @Override
//            protected void updateMessage(String message) {
//                System.out.println(message);
//                super.updateMessage(message);
//            }
//
//        };
//    }

}
