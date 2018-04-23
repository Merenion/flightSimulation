package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class ImplDraw implements IDraw{
    /**реализация интерфейса ISimulationFlight, описание приведенно под сигнатурой интерфейса*/

    //создаем математическую панель, из которой мы можем получить необходимые нам расчеты
    private IMathModel mathModel = new ImplMathModel();
    //Центр Панели на которую в дальнейшем будет добавлен рисунок(Path,Трасса) пока задается в ручную
    private CenterPane centerPane = new CenterPane(450, 228, 2.38, 2.38);

    /**Метод Выдает Path, тобишь нарисованную траекторию полета спутника до времени t (от нулевого времени
     * если clipPath = false, и от t-40000сек если clipPath = true). Сначала определяет местоположение в момент t,
     * и от нее рисует траекторию до начального времени*/
    @Override
    public Path getPathTrack(int t, boolean clipPath) {
        int startTime = t;
        if (clipPath) {
            startTime = 40000;
        }
        Path path = new Path();
        path.setStroke(Color.ORANGE);
        path.setStrokeWidth(2);
        Coordinate coordinate1 = mathModel.getCoordinate2DMap(t, centerPane);
        Coordinate coordinate2;
        MoveTo moveTo = new MoveTo(coordinate1.getX(), coordinate1.getY());
        path.getElements().add(moveTo);
        for (int i = t; (i >= 0 && i >= t - startTime); i -= 1) {
            coordinate2 = mathModel.getCoordinate2DMap(i, centerPane);
            if (coordinate1.getX() - coordinate2.getX() >= 3 || (coordinate1.getX() < coordinate2.getX())) {
                LineTo lineTo = new LineTo(coordinate2.getX(), coordinate2.getY());
                if (coordinate1.getX() < coordinate2.getX()) {
                    path.getElements().add(lineTo);
                } else {
                    MoveTo moveTo1 = new MoveTo(coordinate2.getX(), coordinate2.getY());
                    path.getElements().add(moveTo1);
                }
                coordinate1 = coordinate2;
            }
        }
        return path;
    }

    /**Выдает нарисованную точку на траектории полета*/
    @Override
    public Path getPoint(int t, Color color, int width) {
        Path path = new Path();
        path.setStroke(color);
        path.setStrokeWidth(width);
        path.getElements().addAll(new MoveTo(mathModel.getCoordinate2DMap(t,centerPane).getX(),
                mathModel.getCoordinate2DMap(t,centerPane).getY()),
                new LineTo(mathModel.getCoordinate2DMap(t,centerPane).getX(),
                        mathModel.getCoordinate2DMap(t,centerPane).getY()));
        return path;
    }
}
