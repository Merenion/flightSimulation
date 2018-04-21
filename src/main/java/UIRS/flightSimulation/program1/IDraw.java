package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.Coordinate;
import UIRS.flightSimulation.program1.MathModel.IMathModel;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public interface IDraw {
    Task<Void> createTask(Pane idPane, IMathModel mathModel);
    Path getPathTrack (int t,IMathModel mathModel,boolean clipPath);
    Path getPoint (Coordinate xY, Color color, int width);
    void setStartTime(int startTime);
    void setEndTime(int endTime);
    void setLineWidth(int lineWidth);
    void setStepTime(int stepTime);
    void setDrawingStep(int drawingStep);
}
