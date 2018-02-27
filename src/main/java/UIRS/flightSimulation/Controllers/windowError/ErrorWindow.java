package UIRS.flightSimulation.Controllers.windowError;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorWindow {

    static private String mesageLine;


    static public String getMesageLine() {
        return mesageLine;
    }

    public ErrorWindow() {}


    public ErrorWindow(String masageLine) {
    windowError(masageLine);
    }

        public void windowError(String masageLin) {
        mesageLine = masageLin;
        try {
            Stage stage =new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/ErrorWindow.fxml"));
            stage.setTitle("Ошибка");
            stage.setResizable(false);
            Scene scene = new Scene(root,352,100);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
