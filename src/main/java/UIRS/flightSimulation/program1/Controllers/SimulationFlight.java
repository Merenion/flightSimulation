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
    private static MathModel mathModel = new  MathModel(450,228,2.7,2.7);
    @FXML
    public Pane paneCoordinate;

    @FXML
    public void initialize () {
        mathModel.setTestData();  //задаем тест. данные
        Coordinate coordinateNew; //координаты до которых рисуем линию
        Coordinate coordinate = mathModel.flyModel(0); //координаты от которых рисуем линию
        Path path = new Path();
        path.setFill(Color.RED);
        for (int i=1; i<1000; i+=1){
            MoveTo moveTo = new MoveTo(coordinate.getLambda(),coordinate.getFi());
            System.out.println(coordinate);
            coordinateNew = mathModel.flyModel(i);
            LineTo lineTo = new LineTo(coordinateNew.getLambda(),coordinateNew.getFi());
            path.getElements().addAll(moveTo,lineTo);
            coordinate = coordinateNew;
        }
        paneCoordinate.getChildren().add(path);
    }
}
