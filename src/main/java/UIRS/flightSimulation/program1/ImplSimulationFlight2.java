package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.Coordinate;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ImplSimulationFlight2 extends ImplSimulationFlight {

    ImplSimulationFlight2() {
        super();
    }

    /**Рисует по другому - добавляет на форму не множество Path, а один Path и в него MoveTo/ LineTo
     * Не завершенный, но работающий
     * Рисует не ограниченно без перезаполнения памяти и не отрезает хвот траектории*/
    @Override
    protected void drawElement(Pane idPane,int i) {
        Coordinate coordinateNew = mathModel.getCoordinate2DMap(i, centerPane);
        if (idPane.getChildren().size() == 0) {
            Path path = new Path();
            path.getElements().add(new MoveTo(coordinate.getX(), coordinate.getY()));
            path.setStrokeWidth(lineWidth);
            path.setStroke(colorTrack);
            idPane.getChildren().add(path);
        }
        if (coordinate.getX() - coordinateNew.getX() >= drawingStep || (coordinate.getX() < coordinateNew.getX())) {
            LineTo lineTo = new LineTo(coordinateNew.getX(), coordinateNew.getY());
            Path path = (Path) idPane.getChildren().get(0);
            if (coordinate.getX() < coordinateNew.getX()) {
                path.getElements().add(new MoveTo(coordinateNew.getX(), coordinateNew.getY()));
            } else {
                path.getElements().add(lineTo);
            }
            if (idPane.getChildren().size() > 800)
                idPane.getChildren().remove(0);
            coordinate = coordinateNew;
        }
    }
}
