package UIRS.flightSimulation.program1.Controllers;

import UIRS.flightSimulation.program1.MathModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class CharacteristicsWindow {

    private static MathModel mathModel = new MathModel(450,228,2.4,2.4);
    //450,228,2.4,2.4
    private static float timeBegin;

    public static float getTime() {
        return timeBegin;
    }

    public static MathModel getMathModel() {
        return mathModel;
    }


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
        mathModel.setI(Double.parseDouble(lbAngleNaclonOrbit.getText()));
        mathModel.setOmega0(Double.parseDouble(lbDolgotaVoshodUthla.getText()));
        mathModel.setW0(Double.parseDouble(lbBeginArgumentPerigeya.getText()));
        mathModel.setHpi(Double.parseDouble(lbHightPerigei.getText()));
        mathModel.setHa(Double.parseDouble(lbHightApogei.getText()));
        timeBegin = Float.parseFloat(lbStartTIme.getText());
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
