package UIRS.flightSimulation.program1.Controllers;

import UIRS.flightSimulation.program1.Coordinate;
import UIRS.flightSimulation.program1.MathModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class SimulationFlight {
    private static MathModel mathModel = CharacteristicsWindow.getMathModel();
    @FXML
    public Pane paneCoordinate;

    @FXML
    public void initialize () {
        mathModel.setTestData();
        Coordinate coordinateNew;
        Coordinate coordinate = mathModel.flyModel(0);
        for (int i=1; i<10000; i+=5){
            Path path = new Path();
            path.setFill(Color.RED);
            MoveTo moveTo = new MoveTo(coordinate.getLambda(),coordinate.getFi());
            System.out.println(coordinate);
            coordinateNew = mathModel.flyModel(i);
            LineTo lineTo = new LineTo(coordinateNew.getLambda(),coordinateNew.getFi());
            path.getElements().add(moveTo);
            path.getElements().add(lineTo);
            paneCoordinate.getChildren().add(path);
            coordinate = coordinateNew;
        }
    }
}
