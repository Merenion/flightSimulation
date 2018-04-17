package UIRS.flightSimulation.program1.MathModel;

public class Coordinate {

    private double y;
    private double x;

    public Coordinate(double y, double x) {
        this.y = y;
        this.x = x;
    }

    public Coordinate() {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "y=" + y +
                ", x=" + x +
                '}';
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}
