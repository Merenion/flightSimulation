package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.Coordinate;
import UIRS.flightSimulation.program1.MathModel.IMathModel;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class DrawElement2 extends Draw {

    DrawElement2() {
        super();
    }

    public DrawElement2(TextArea idTime) {
    }

    DrawElement2(TextArea idTime, TextArea idParametrs) {
        super(idTime, idParametrs);
    }

    DrawElement2(TextArea idTime, TextArea idParametrs, CheckBox isShowIsTheSun) {
        super(idTime, idParametrs, isShowIsTheSun);
    }

    @Override
    protected void drawElement(Pane idPane, int i, IMathModel mathModel) {
        Coordinate coordinateNew = mathModel.flyModel(i);
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

    @Override
    public Task<Void> createTask(Pane idPane, IMathModel mathModel) {
        return super.createTask(idPane, mathModel);
    }

    @Override
    public Path getPathTrack(int t, IMathModel mathModel, boolean clipPath) {
        return super.getPathTrack(t, mathModel, clipPath);
    }

    @Override
    public Path getPoint(Coordinate xY, Color color, int width) {
        return super.getPoint(xY, color, width);
    }

    @Override
    public void setStartTime(int startTime) {
        super.setStartTime(startTime);
    }

    @Override
    public void setEndTime(int endTime) {
        super.setEndTime(endTime);
    }

    @Override
    public void setLineWidth(int lineWidth) {
        super.setLineWidth(lineWidth);
    }

    @Override
    public void setStepTime(int stepTime) {
        super.setStepTime(stepTime);
    }

    @Override
    public void setDrawingStep(int drawingStep) {
        super.setDrawingStep(drawingStep);
    }
}
