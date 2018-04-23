package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.Coordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

import java.util.Vector;

public interface IDraw {
    /**Класс реализующий данный интерфейс рисует различные объекты. Выдача методом Path обьект
     * необходимо добавить в Pane для дальнейшего отображения*/
    Path getPathTrack(int t, boolean clipPath);
    Path getPoint(int t, Color color, int width);
}
