package UIRS.flightSimulation.ControllersStartMenu.windowError;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerErrorWindow {

    @FXML
    public Label labelError;


    @FXML
    public void initialize () {
        ErrorWindow errorWindow = new ErrorWindow();
        labelError.setText(errorWindow.getMesageLine());
    }

    public void onOk(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
