package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.MathModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CharacteristicsForm {

    //450,228,2.4,2.4

    @FXML
    public RadioButton radioButtonCircleOrbit;  //выбрана ли круговая орбита
    @FXML
    public TextField lbHightPerigei;            //высота перигея
    @FXML
    public TextField lbAngleNaclonOrbit;        //угол наклона орбиты
    @FXML
    public TextField lbHightApogei;             //высота апогея
    @FXML
    public TextField lbDolgotaVoshodUthla;      //долгота восходящего угла
    @FXML
    public TextField lbBeginArgumentPerigeya;   //начальный аргумент перигея
    @FXML
    public TextField lbStartTIme;               //начальное время

    @FXML
    public void initialize () {

    }

    public void onStart(ActionEvent actionEvent) {
        InitialCharacteristics initialCharacteristics =
                InitialCharacteristics.getInitialCharacteristics().setI(Double.parseDouble(lbAngleNaclonOrbit.getText()))
                .setOmega0(Double.parseDouble(lbDolgotaVoshodUthla.getText()))
                .setW0(Double.parseDouble(lbBeginArgumentPerigeya.getText()))
                .setHpi(Double.parseDouble(lbHightPerigei.getText()))
                .setHa(Double.parseDouble(lbHightApogei.getText()))
                        .setStartTime(Integer.parseInt(lbStartTIme.getText()));

        try {
            openWindowSimulationFlight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //открыть окно симуляции полета
    public void openWindowSimulationFlight () throws IOException {
        System.out.println("action bt simulationWindow project");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Program1/WindowSimulationFlight.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage= new Stage();
        stage.setTitle("Имитация");
        stage.setResizable(false);
        Scene scene = new Scene(root, 940, 623);
        stage.setScene(scene);
        Stage stageThis = (Stage) lbStartTIme.getScene().getWindow();
            stageThis.close();
        stage.show();
    }
}
