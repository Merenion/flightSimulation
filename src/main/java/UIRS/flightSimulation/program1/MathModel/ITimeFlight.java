package UIRS.flightSimulation.program1.MathModel;

public interface ITimeFlight {
    void addSecond(int value);
    void setSecond(int value);
    void addCoil(int value);
    void setCoil (int value);
    void setTimePlot(TypeTime timePlot, int value);
    int getTimePlot(TypeTime typeTime);
    void resetTime();
    String getStringTime(TypeTime typeTime);
}
