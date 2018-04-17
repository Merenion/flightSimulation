package UIRS.flightSimulation.ControllersStartMenu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartMenu {
    @FXML
    public AnchorPane forma;

    @FXML
    public void initialize () {
        Canvas canvas = new Canvas();
        canvas.
    }

    public void onBtProgram1(ActionEvent actionEvent) throws IOException {
        System.out.println("action bt open program 1");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/Program1/CharacteristicsWindow.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage= new Stage();
        stage.setTitle("Характеристики");
        stage.setResizable(false);
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        Stage stageThis = (Stage) forma.getScene().getWindow();
        stageThis.close();
        stage.show();
    }

    public void onBtProgram3(ActionEvent actionEvent) {
    }

    public void onBtProgram2(ActionEvent actionEvent) {
    }

    public void onBtHelp(ActionEvent actionEvent) {
    }

             /**вызываем модальное окно где будет инфо о программе*/
    public void onBtAboutTheProgram(ActionEvent actionEvent) throws IOException {
        System.out.println("action bt info project");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/AboutTheProgram.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage= new Stage();
        stage.setTitle("О программе");
        stage.setResizable(false);
        Scene scene = new Scene(root, 695, 443);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }
}
