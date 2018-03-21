package UIRS.flightSimulation.program1;

public class Coordinate {

    public Coordinate(double fi, double lambda) {
        this.fi = fi;
        this.lambda = lambda;
    }

    public Coordinate(double fi, double lambda, double additionally) {
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

    private double fi;
    private double lambda;

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
