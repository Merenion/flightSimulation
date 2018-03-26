package UIRS.flightSimulation;

import UIRS.flightSimulation.ControllersStartMenu.StartMenu;
import UIRS.flightSimulation.program1.MathModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/StartMenu.fxml"));
        primaryStage.setTitle("Полетели");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root,215,277);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
