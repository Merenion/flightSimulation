package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.Coordinate;
import UIRS.flightSimulation.program1.MathModel.IMathModel;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public interface ISimulationFlight {
    /**интерфейс реализацией которого является класс симулирующий полет КА, рисующий трассу полета
     * и выводящий параметры (время и характеристики орбиты) на Панель(Pane) и текстовые поля(TextArea)*/

    Task<Void> createSimulation (Pane idPane,CheckBox isShowIsTheSun, TextArea idTime, TextArea idParameters);
    void startNewSimulation (Pane idPane,CheckBox isShowIsTheSun, TextArea idTime, TextArea idParameters);
    void stopSimulation ();
    void resumeSimulation ();
    boolean isRunSimulation ();
    void setStartTime(int startTime);
    void setEndTime(int endTime);
    void setLineWidth(int lineWidth);
    void setStepTime(int stepTime);
    void setDrawingStep(int drawingStep);
}
