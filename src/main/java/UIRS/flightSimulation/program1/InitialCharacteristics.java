package UIRS.flightSimulation.program1;

public class InitialCharacteristics {

    private static InitialCharacteristics singleton;

    // входные паременные
    private double i=0;       //угол наклона плоскости орбиты
    private double omega0=0;  //долгота восходящего узла орбиты
    private double w0=0;      //начальный аргучент перигея орбиты
    private double Hpi=0;     //высота перигея орбиты
    private double Ha=0;      //высота апогея орбиты
    private int startTime=0;//начальное время

    private InitialCharacteristics (){};

    public static InitialCharacteristics getInitialCharacteristics () {
        if (singleton == null) {
            singleton = new InitialCharacteristics();
        }
        return singleton;
    }

    public InitialCharacteristics setI(double i) {
        this.i = i;
        singleton = this;
        return this;
    }

    public InitialCharacteristics setOmega0(double omega0) {
        this.omega0 = omega0;
        singleton = this;
        return this;
    }

    public InitialCharacteristics setW0(double w0) {
        this.w0 = w0;
        singleton = this;
        return this;
    }

    public InitialCharacteristics setHpi(double hpi) {
        Hpi = hpi;
        singleton = this;
        return this;
    }

    public InitialCharacteristics setHa(double ha) {
        Ha = ha;
        singleton = this;
        return this;
    }

    public InitialCharacteristics setStartTime(int startTime) {
        this.startTime = startTime;
        singleton = this;
        return this;
    }

    public double getI() {
        return i;
    }

    public double getOmega0() {
        return omega0;
    }

    public double getW0() {
        return w0;
    }

    public double getHpi() {
        return Hpi;
    }

    public double getHa() {
        return Ha;
    }

    public int getStartTime() {
        return startTime;
    }
}
