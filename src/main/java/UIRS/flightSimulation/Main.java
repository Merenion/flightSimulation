package UIRS.flightSimulation;

import UIRS.flightSimulation.ControllersStartMenu.StartMenu;
import UIRS.flightSimulation.program1.MathModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private static MathModel mathModel = new MathModel();

    public static void main(String[] args) {
        mathModel.setTestData();
        for (int t=0; t<8000;t++) {
            System.out.println("_____#" + t);
            System.out.println(mathModel.flyModel(t));
        }
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
