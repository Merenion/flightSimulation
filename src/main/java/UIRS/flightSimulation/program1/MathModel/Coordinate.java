package UIRS.flightSimulation.program1.MathModel;

public class Coordinate {

    private double fi;
    private double lambda;

    public Coordinate(double fi, double lambda) {
        this.fi = fi;
        this.lambda = lambda;
    }

    public Coordinate() {
        this.lambda = lambda;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "fi=" + fi +
                ", lambda=" + lambda +
                '}';
    }

    public double getFi() {
        return fi;
    }

    public void setFi(double fi) {
        this.fi = fi;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }
}
