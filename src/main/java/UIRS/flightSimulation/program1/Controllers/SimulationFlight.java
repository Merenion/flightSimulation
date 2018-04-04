package UIRS.flightSimulation.program1.Controllers;

import UIRS.flightSimulation.program1.Coordinate;
import UIRS.flightSimulation.program1.MathModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class SimulationFlight implements Runnable {

    //берем данные с предыдущей формы.
    private static MathModel mathModel = CharacteristicsWindow.getMathModel();
    private float timeBegin = CharacteristicsWindow.getTime();


    //берем обьекты с формы для рисования и вывода данных
    @FXML
    public TextArea idAreaTimeOfLight;
    @FXML
    public volatile Pane paneCoordinate;

    volatile Coordinate coordinateNew = null;
    volatile Coordinate coordinate = null;

    @FXML
    public void initialize() {
        mathModel.rashetPorb();   //рассчет начальных параметров орбиты
        coordinate = mathModel.flyModel(timeBegin); //координаты от которых рисуем линию впервый раз
        coordinateNew = mathModel.flyModel(timeBegin+1);
    }

    @Override
    public void run() {
        Path path = new Path();
        path.setStrokeWidth(2);
        path.setFill(Color.YELLOW);
        MoveTo moveTo = new MoveTo(0, 0);
        LineTo lineTo = new LineTo(100, 100);
        path.getElements().addAll(moveTo, lineTo);
        paneCoordinate.getChildren().add(new Path(new MoveTo(0, 0),new LineTo(100, 100)));
    }

//    class ThreadRaschet extends Thread {
//        @Override
//        public void run() {
//            for (float i = timeBegin + 1; i < 30200; i++) {
//                coordinateNew = mathModel.flyModel(i);
//                ThreadRaschet.yield();
//            }
//        }
//    }

    class ThreadShowImage extends Thread {
        private Parent parent;
        private FXMLLoader fxmlLoader = new FXMLLoader();
        private SimulationFlight simulationFlight;

        private void loadingControllerSimulation () {
            try {
                fxmlLoader.setLocation(getClass().getResource("/src/main/resources/Program1/WindowSimulationFlight.fxml"));
                parent = fxmlLoader.load();
                simulationFlight= fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public synchronized void run() {
            loadingControllerSimulation();
            System.out.println(simulationFlight.getIdAreaTimeOfLight().getText());
        }
    }

    public void onStart(ActionEvent actionEvent) {
        Path path = new Path();
        path.setStrokeWidth(2);
        path.setFill(Color.YELLOW);
        for (float i = timeBegin + 1; i < 12200; i++) {
            path = new Path();
            path.setStrokeWidth(2);
            path.setStroke(Color.YELLOW);
            MoveTo moveTo = new MoveTo(coordinate.getLambda(), coordinate.getFi());
            coordinateNew = mathModel.flyModel(i);
            System.out.println(coordinate + "\n___________________");
            LineTo lineTo = new LineTo(coordinateNew.getLambda(), coordinateNew.getFi());
            path.getElements().addAll(moveTo, lineTo);
            paneCoordinate.getChildren().add(path);
            coordinate = coordinateNew;

        }
    }

    public TextArea getIdAreaTimeOfLight() {
        return idAreaTimeOfLight;
    }

    public void onStop(ActionEvent actionEvent) {
        idAreaTimeOfLight.setText("lol");
//        ThreadRaschet threadRaschet = new ThreadRaschet();
//        //ThreadShowImage threadShowImage = new ThreadShowImage();
//        threadRaschet.start();
//        //threadShowImage.start();
//        Coordinate coordinateNew2 = coordinateNew;
//        Path path;
//        for (int i = 0; i < 30200; i++) {
//            ThreadShowImage.yield();
//            if (!coordinateNew2.equals(coordinateNew)) {
//                path = new Path();
//                path.setStrokeWidth(2);
//                path.setStroke(Color.YELLOW);
//                MoveTo moveTo = new MoveTo(coordinate.getLambda(), coordinate.getFi());
//                LineTo lineTo = new LineTo(coordinateNew.getLambda(), coordinateNew.getFi());
//                path.getElements().addAll(moveTo, lineTo);
//                paneCoordinate.getChildren().add(path);
//                coordinate = coordinateNew;
//                coordinateNew2 = coordinateNew;
//                System.out.println("1");
//            }
//        }
    }

    public void onBack(ActionEvent actionEvent) {
        (new ThreadShowImage()).start();
    }
}
