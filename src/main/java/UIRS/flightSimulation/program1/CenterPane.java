package UIRS.flightSimulation.program1;

import javafx.scene.layout.Pane;

public class CenterPane {

    private double x0i;     //центр по X
    private double y0i;     //центр по Y
    private double mX;      //массштаб по X
    private double mY;      //массштаб по Y

    public CenterPane(double x0i, double y0i, double mX, double mY) {
        this.x0i = x0i;
        this.y0i = y0i;
        this.mX = mX;
        this.mY = mY;
    }

    public CenterPane(Pane pane, double mX, double mY) {
        this.x0i = pane.getWidth()/2;
        this.y0i = pane.getHeight()/2;
        this.mX = mX;
        this.mY = mY;
    }

    public double getX0i() {
        return x0i;
    }

    public double getY0i() {
        return y0i;
    }

    public double getmX() {
        return mX;
    }

    public double getmY() {
        return mY;
    }
}
