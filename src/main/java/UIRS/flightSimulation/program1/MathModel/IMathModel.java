package UIRS.flightSimulation.program1.MathModel;

public interface IMathModel {
    //константы
    int Rz = 6371; //радиус Земли
    int mu = 398700; //гравитационный параметр Земли
    final double omegaZemli = 0.0000729211; //угловая скорость вращения Земли
    double omegaSolnca = 0.000000199106; //угловая скорость вращения Солнца

    void rashetPorb();
    Coordinate flyModel(float t);
}
