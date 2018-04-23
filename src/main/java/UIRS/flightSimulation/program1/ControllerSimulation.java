package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ControllerSimulation {

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

    private ISimulationFlight simulationFlight;
    private IDraw draw;
    private IWriter writer;

    @FXML
    public void initialize() {
        simulationFlight = new ImplSimulationFlight();
        draw = new ImplDraw();
        writer = new ImplWriter();
    }

    @FXML
    public void onStart(ActionEvent actionEvent) {
        idPane.getChildren().clear();
        simulationFlight.startNewSimulation(idPane,idIsTheSun,idAreaTimeOfLight,idParameters);
    }

    @FXML
    public void onStopOrResome(ActionEvent actionEvent) throws InterruptedException {
        if (simulationFlight.isRunSimulation()){
            simulationFlight.stopSimulation();
            iBtStop.setText("Возобновить");
        }else {
            simulationFlight.resumeSimulation();
            iBtStop.setText("Cтоп");
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

    @FXML
    public void onBtFastSimulation(ActionEvent actionEvent) {
        simulationFlight.stopSimulation();
        int t = Integer.parseInt(idLbTimeFastDraw.getText());
        idPane.getChildren().clear();
        idPane.getChildren().add(draw.getPathTrack(t,idTripPath.isSelected()));
        idPane.getChildren().add(draw.getPoint(t,Color.RED,8));
        idPane.getChildren().add(draw.getPoint(t,Color.BLUE,3));
        idTimingFastSimulate.setText(writer.getTiming(t));
        idMainParametersFastSimulate.setText(writer.getMainParameters(t));
        idOtherParametersFastSimulate.setText(writer.getOtheParamerers(t));

    }
}
