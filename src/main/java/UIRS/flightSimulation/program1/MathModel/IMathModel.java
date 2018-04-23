package UIRS.flightSimulation.program1.MathModel;

import UIRS.flightSimulation.program1.CenterPane;

public interface IMathModel {
    //константы
    int Rz = 6371; //радиус Земли
    int mu = 398700; //гравитационный параметр Земли
    final double omegaZemli = 0.0000729211; //угловая скорость вращения Земли
    double omegaSolnca = 0.000000199106; //угловая скорость вращения Солнца

    boolean isInTheSun(int t);
    int getNcoil(int t);
    Coordinate getCoordinate2DMap (int t,CenterPane centerPane);
    double getW(int t);
    double getTeta(int t);
    double getFi(int t);
    double getLambda(int t);
    double getSinlambda(int t);
    double getCoslambda(int t);
    String getMainParameters(int t);
    String getOtheParameters (int t);
    String getTimingFlyght (int t);
}
