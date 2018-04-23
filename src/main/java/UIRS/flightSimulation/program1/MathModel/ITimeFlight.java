package UIRS.flightSimulation.program1.MathModel;

public interface ITimeFlight {
    void addSecond(int value);
    void setSecond(int value);
    int getTime(TypeTime typeTime);
    String getAllTime(int t);
    void resetTime();
    String getStringTime(TypeTime typeTime);
}
