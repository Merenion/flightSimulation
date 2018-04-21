package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private IDraw draw;
    private Thread thread;

    @FXML
    public Button iBtStop;
    @FXML
    public TextArea idOtherParametersFastSimulate;
    @FXML
    public TextArea idMainParametersFastSimulate;
    @FXML
    public TextArea idTimingFastSimulate;
    @FXML
    public CheckBox idTripPath;
    @FXML
    public TextField idLbTimeFastDraw;
    @FXML
    public Button idBtGetParInTime;
    @FXML
    public CheckBox idIsTheSun;
    @FXML
    public TextArea idParameters;
    @FXML
    public TextArea idAreaTimeOfLight;
    @FXML
    public Pane idPane;

    private static volatile Coordinate coordinate;
    private static volatile Coordinate coordinateNew;
    boolean isStopDrawing = false;

    @FXML
    public void initialize() {
        draw = new DrawElement2(idAreaTimeOfLight,idParameters,idIsTheSun);
        mathModel = new MathModel(new CenterPane(450, 228, 2.37, 2.37),initCh);
        mathModel.rashetPorb();   //рассчет начальных параметров орбиты
        flightTime = new TimeFlight();
    }

    @FXML
    public void onStart(ActionEvent actionEvent) {
        if (task != null && task.isRunning()) {
            task.cancel();
        }
        isStopDrawing = false;
        task = draw.createTask(idPane,mathModel);
        thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void onStop(ActionEvent actionEvent) throws InterruptedException {
        if (isStopDrawing){
            thread.resume();
            isStopDrawing = false;
            iBtStop.setText("Стоп");

        }else {
            if (task.isRunning()) {
                thread.suspend();
                isStopDrawing = true;
                iBtStop.setText("Возобновить");
            }
        }

    }

    //этой кнопкой запускаем выше созданный поток (кнопка на форме назад) сделал так просто чтоб поэкспериментировать
    @FXML
    public void onBack(ActionEvent actionEvent) {
    }

    @FXML
    public void onCheckSun(ActionEvent actionEvent) {
        idIsTheSun.setText("In The Sun");
        idIsTheSun.setTextFill(Color.BLACK);
    }

    public void onBtFastSimulation(ActionEvent actionEvent) {
        idPane.getChildren().clear();
        idPane.getChildren().addAll(draw.getPathTrack(Integer.parseInt(idLbTimeFastDraw.getText()),mathModel,idTripPath.isSelected()));
        idPane.getChildren().add(draw.getPoint(mathModel.flyModel(Integer.parseInt(idLbTimeFastDraw.getText())),Color.RED,7));
        idPane.getChildren().add(draw.getPoint(mathModel.flyModel(Integer.parseInt(idLbTimeFastDraw.getText())),Color.BLUE,3));
        idTimingFastSimulate.setText("Timing:\n"+flightTime.toString());
        idMainParametersFastSimulate.setText("Main Parameters:\n"+mathModel);
        if (mathModel.isInTheSun(Integer.parseInt(idLbTimeFastDraw.getText()))) {
            idOtherParametersFastSimulate.setText("Other Parameters:\n" + mathModel.getOtheParameters() + "\nКА на Солнце");
        }else {
            idOtherParametersFastSimulate.setText("Other Parameters:\n"+mathModel.getOtheParameters()+"\nКА в Тени");
        }
    }
}
