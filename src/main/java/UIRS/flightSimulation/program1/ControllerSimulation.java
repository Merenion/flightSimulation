package UIRS.flightSimulation.program1;

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
    public Button iBtStopResume;                    //кнопка остановки и возобновления
    @FXML
    public TextArea idOtherParametersForm2;         //текстовое поле Дполнительных параметров (расположенно на 2 вкладке)
    @FXML
    public TextArea idMainParametersForm2;          //текстовое поле основных параметров (расположенно на 2 вкладке)
    @FXML
    public TextArea idTimingForm2;                  //текстовое поле времени(расположенно на 2 вкладке)
    @FXML
    public CheckBox idTripPath;                     //check обрезать или нет, хвост трассы (расположенно на 2 вкладке)
    @FXML
    public TextField idLbTimeFastDraw;              //текстовая строка ввода времени (расположенно на 2 вкладке)
    @FXML
    public CheckBox idIsTheSun;                     //check отображать ли при прорисовке, места полета КА на солнца
    @FXML
    public TextArea idParametersForm1;              //текстовое поле параметров (расположенно на 1 вкладке)
    @FXML
    public TextArea idTimingForm1;                  //текстовое поле тайминга (расположенно на 1 вкладке)
    @FXML
    public Pane idPane;                             //поле на котором отображается симуляция

    private ISimulationFlight simulationFlight;
    private IDraw draw;
    private IWriter writer;

    @FXML
    public void initialize() {
        simulationFlight = new ImplSimulationFlight();
        draw = new ImplDraw();
        writer = new ImplWriter();
    }

    /**экшен кнопки старт -запустить симуляцию*/
    @FXML
    public void onStart(ActionEvent actionEvent) {
        idPane.getChildren().clear();
        simulationFlight.startNewSimulation(idPane,idIsTheSun, idTimingForm1, idParametersForm1);
    }

    /**экшен кнопки стоп - остановить или возобновить симуляцию*/
    @FXML
    public void onStopOrResome(ActionEvent actionEvent) throws InterruptedException {
        if (simulationFlight.isRunSimulation()){
            simulationFlight.stopSimulation();
            iBtStopResume.setText("Возобновить");
        }else {
            simulationFlight.resumeSimulation();
            iBtStopResume.setText("Cтоп");
        }

    }

    /**экшен кнопки назад - вернуться на форму характеристик*/
    @FXML
    public void onBack(ActionEvent actionEvent) {
    }

    /**экшенен idIsTheSum - задает текст и цвет*/
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
        idTimingForm2.setText(writer.getTiming(t));
        idMainParametersForm2.setText(writer.getMainParameters(t));
        idOtherParametersForm2.setText(writer.getOtheParamerers(t));

    }
}
