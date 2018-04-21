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

public class Draw implements IDraw {

    protected InitialCharacteristics initCh = InitialCharacteristics.getInitialCharacteristics();
    protected volatile static ITimeFlight flightTime = new TimeFlight();
    protected static volatile Coordinate coordinate;
    protected TextArea idTime = new TextArea();
    protected TextArea idParametrs = new TextArea();
    protected CheckBox isShowIsTheSun = new CheckBox();
    protected int startTime;
    protected int endTime = 10000000;
    protected int lineWidth = 2;
    protected int stepTime = 8;
    protected int drawingStep = 3;
    protected Color colorTrack = Color.PURPLE;

    Draw() {
        startTime = initCh.getStartTime();
    }

    public Draw(TextArea idTime) {
        this.idTime = idTime;
    }

    Draw(TextArea idTime, TextArea idParametrs) {
        this.idTime = idTime;
        this.idParametrs = idParametrs;
    }

    Draw(TextArea idTime, TextArea idParametrs, CheckBox isShowIsTheSun) {
        this.idTime = idTime;
        this.idParametrs = idParametrs;
        this.isShowIsTheSun = isShowIsTheSun;
    }

    @Override
    public Task<Void> createTask(Pane idPane, IMathModel mathModel) {
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
                flightTime.resetTime();
                for (int i = startTime; i <= endTime; i += stepTime) {
                    flightTime.setSecond(i);
                    if (isCancelled()) {
                        updateMessage("Рисование прервано");
                        return;
                    }

                    Platform.runLater(new Runnable() {

                        int i;

                        @Override
                        public void run() {
                            drawElement(idPane, i, mathModel);
                            idTime.setText("timing:\n" + flightTime);
                            idParametrs.setText(mathModel.toString());
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
                    flightTime.setCoil(mathModel.getNcoil());
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

    protected void drawElement(Pane idPane, int i, IMathModel mathModel) {
        Coordinate coordinateNew = mathModel.flyModel(i);
        if (idPane.getChildren().size()==0) {
            Path path = new Path();
            path.getElements().add(new MoveTo(coordinate.getX(),coordinate.getY()));
            path.setStrokeWidth(lineWidth);
            path.setStroke(colorTrack);
            idPane.getChildren().add(path);
        }
        if (coordinate.getX() - coordinateNew.getX() >= drawingStep || (coordinate.getX() < coordinateNew.getX())) {
            LineTo lineTo = new LineTo(coordinateNew.getX(), coordinateNew.getY());
            Path path = (Path) idPane.getChildren().get(0);
            if (coordinate.getX() < coordinateNew.getX()) {
                path.getElements().add(new MoveTo(coordinateNew.getX(),coordinateNew.getY()));
            } else {
                path.getElements().add(lineTo);
            }
            if (idPane.getChildren().size() > 800)
                idPane.getChildren().remove(0);
            coordinate = coordinateNew;
        }
    }

    @Override
    public Path getPathTrack (int t,IMathModel mathModel,boolean clipPath) {
        flightTime.setSecond(t);
        int startTime = t;
        if (clipPath) {
            startTime =40000;
        }
        Path path = new Path();
        path.setStroke(Color.ORANGE);
        path.setStrokeWidth(2);
        Coordinate coordinate1 = mathModel.flyModel(t);
        flightTime.setCoil(mathModel.getNcoil());
        Coordinate coordinate2 ;
        MoveTo moveTo = new MoveTo(coordinate1.getX(),coordinate1.getY());
        path.getElements().add(moveTo);
        for (int i =t ; (i>=0 && i>=t -startTime); i-=1 ) {
            coordinate2 = mathModel.flyModel(i);

            if (coordinate1.getX() - coordinate2.getX() >= 3 || (coordinate1.getX() < coordinate2.getX())) {
                LineTo lineTo = new LineTo(coordinate2.getX(),coordinate2.getY());
                if (coordinate1.getX() < coordinate2.getX()) {
                    path.getElements().add(lineTo);
                } else {
                    MoveTo moveTo1 = new MoveTo(coordinate2.getX(),coordinate2.getY());
                    path.getElements().add(moveTo1);
                    flightTime.addCoil(1);
                }
                coordinate1 = coordinate2;
            }
        }
        return path;
    }

    @Override
    public Path getPoint (Coordinate xY,Color color, int width) {
        Path path = new Path();
        path.setStroke(color);
        path.setStrokeWidth(width);
        path.getElements().addAll(new MoveTo(xY.getX(),xY.getY()),new LineTo(xY.getX(),xY.getY()));
        return path;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public void setDrawingStep(int drawingStep) {
        this.drawingStep = drawingStep;
    }

//    public Task<Void> createTask(Pane idPane, IMathModel mathModel) {
//        return new Task<Void>() {
//
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
//                Platform.runLater(() -> idPane.getChildren().clear());
//                updateMessage("Рисование начато");
//                coordinate = mathModel.flyModel(initCh.getStartTime());
//                flightTime.resetTime();
//                for (int i = startTime; i <= endTime; i += stepTime) {
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
//                        public void run() {
//                            drawElement(idPane, i, mathModel);
//                            idTime.setText("timing:\n" + flightTime);
//                            idParametrs.setText(mathModel.toString());
//                            if (isShowIsTheSun.isSelected()) {
//                                if (mathModel.isInTheSun(i)) {
//                                    colorTrack = Color.ORANGE;
//                                    isShowIsTheSun.setTextFill(Color.ORANGE);
//                                    isShowIsTheSun.setText("SUN");
//                                } else {
//                                    colorTrack = Color.PURPLE;
//                                    isShowIsTheSun.setTextFill(Color.PURPLE);
//                                    isShowIsTheSun.setText("SHADOW");
//                                }
//                            }
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
//                    flightTime.setCoil(mathModel.getNcoil());
//                }
//                updateMessage("Рисование завершено");
//            }
//
//            @Override
//            protected void updateMessage(String message) {
//                System.out.println(message);
//                super.updateMessage(message);
//            }
//
//        };
//    }


//    private void drawElement(Pane idPane, int i, IMathModel mathModel) {
//        Coordinate coordinateNew = mathModel.flyModel(i);
//        Path path = new Path();
//        path.setStrokeWidth(lineWidth);
//        path.setStroke(colorTrack);
//        MoveTo moveTo = new MoveTo(coordinate.getX(), coordinate.getY());
//        if (coordinate.getX() - coordinateNew.getX() >= drawingStep || (coordinate.getX() < coordinateNew.getX())) {
//            LineTo lineTo = new LineTo(coordinateNew.getX(), coordinateNew.getY());
//            path.getElements().addAll(moveTo, lineTo);
//            if (coordinate.getX() < coordinateNew.getX()) {
//            } else {
//                idPane.getChildren().add(path);
//            }
//            if (idPane.getChildren().size() > 800)
//                idPane.getChildren().remove(0);
//            coordinate = coordinateNew;
//        }
//    }

}
