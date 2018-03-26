package UIRS.flightSimulation.program1;

import java.util.logging.Logger;

public class MathModel {
    private static Logger log = Logger.getGlobal();

    public MathModel(double x0i, double y0i, double mX, double mY) {
        this.x0i = x0i;
        this.y0i = y0i;
        this.mX = mX;
        this.mY = mY;
    }

    //константы
    private static final int Rz = 6371; //радиус Земли
    private static final int mu = 398700; //гравитационный параметр Земли
    private static final double omegaZemli = 0.0000729211; //угловая скорость вращения Земли
    private static final double omegaSolnca = 0.000000199106; //угловая скорость вращения Солнца

    //координаты центра и массштаю
    private double x0i;     //центр по X
    private double y0i;     //центр по Y
    private double mX;      //массштаб по X
    private double mY;      //массштаб по Y

    // входные переменные
    private double i;       //угол наклона плоскости орбиты
    private double omega0;  //долгота восходящего узла орбиты
    private double w0;      //начальный аргучент перигея орбиты
    private double Hpi;     //высота перигея орбиты
    private double Ha;      //высота апогея орбиты

    //переведенные входные переменные
    private double iRad;       //угол наклона плоскости орбиты
    private double omega0Rad;  //долгота восходящего узла орбиты
    private double w0Rad;      //начальный аргучент перигея орбиты

    //начальные параметры орбиты
    private double rpi;             //радиус перигея
    private double ra;              //радиус апогея
    private double e;               //Эксцентриситет орбиты
    private double a;               //Большая полурсь
    private double p;
    private double r;               //r которая хз
    private double H;               //Высота полета
    private double Tzv;             //Период обращения звездный
    private double dOmega;
    private double dw;

    //переменные орбиты в полете
    private double omega;           //Текущий угол восходящего узла орбиты, рад
    private double dt_sr;
    private double W;
    private double n;
    private double t_zv;
    private double tetaSmall;
    private double M;
    private double M1;
    private double dE;
    private double cosTetaSmall;
    private double sinTetaSmall;
    private double u;
    private double sinFi;
    private double cosFi;
    private double sinlambda;
    private double coslambda;
    private double fi;
    private double fiGa;
    private double lambdaGa;
    private double lambda;
    private double x;
    private double y;


    public void setTestData() {
        i = 98.3;
        omega0 = 1;
        w0 = 1;
        Hpi = 729;
        Ha = 729;
    }


    //другие переменные
    private double tau = 0;
    private static float dt = 1; //шаг расчета по времени


    public void rashetPorb() {  //Расчет начальных параметров орбиты
        iRad = i * Math.PI / 180;
        omega0Rad = omega0 * Math.PI / 180;
        w0Rad = w0 * Math.PI / 180;
        ra = Rz + Ha;                                             //радиус апогея
        rpi = Rz + Hpi;                                           //радиус перигея
        e = (ra - rpi) / (ra + rpi);                              //Эксцентриситет орбиты
        a = (rpi + ra) / 2;                                       //Большая полурсь
        p = a * (1 - e*e);                                        //расчет фокального параметра орбиты
        r = p/(1+e*Math.cos(tetaSmall));                          //радиус-вектор КА от центра
        H = r - Rz;                                               //высота полета
        Tzv = 2 * Math.PI * Math.sqrt(Math.pow(a, 3) / mu);         //Период обращения звездный
        //Расчет векового возмущения первого порядка:
        dOmega = -35.052 / 60 * Math.PI / 180 * Math.pow((Rz / p), 2) * Math.cos(iRad);                                    //*
        //Расчет векового возмущения аргумента перигея орбиты первого порядка:
        dw = -17.525 / 60 * Math.PI / 180 * Math.pow((Rz / p), 2) * (1 - 5 * Math.pow(Math.cos(iRad), 2));                 //*
    }

    public Coordinate flyModel(float t) {
        rashetPorb();
        omega = omega0Rad * Math.PI / 180 + t / Tzv * dOmega;      //Текущий угол восходящего узла орбиты, рад             //*
        W = w0Rad * Math.PI / 180 + t / Tzv * dw;                  //текущее значение аргумента перигея                    //*
        n = Math.sqrt(mu / Math.pow(a, 3));                        //Определение среднего движения
        dt_sr = t - tau;                                           //Определение промежутка среднего времени с момента прохождения перигея до момента наблюдения
        t_zv = 1.00273791 * dt_sr;                                 //Определение звездного времени
        M = t_zv * n;                                              //Определение средней аномалии
        double E01 = M + e * Math.sin(M) + (e * e / 2) * Math.sin(2 * M);        //Первый член разложения уравнения Кеплера (E-e*sin(E)=M
        double E02 = e * e * e / 24 * (9 * Math.sin(3 * M) - 3 * Math.sin(M)); //Второй член разложения и т.д.
        double E03 = Math.pow(Math.E, 4) / (24 * 8) * (64 * Math.sin(4 * M) - 32 * Math.sin(2 * M));
        double E04 = Math.pow(Math.E, 4) / (120 * 16) * (625 * Math.sin(5 * M) + 5 * 81 * Math.sin(3 * M) + 10 * Math.sin(M));
        double E05 = Math.pow(Math.E, 5) / (720 * 32) * (36 * 36 * 6 * Math.sin(6 * M) - 6 * 256 * 4 * Math.sin(4 * M) + 15 * 32 * Math.sin(2 * M));
        double Ea = E01 + E02 + E03 + E04 + E05;                           //Эксцентрическая аномалия
        double Ea0 = Ea - 2 * Math.PI * ((int) (Ea / (2 * Math.PI)));         //Эксцентрическая аномалия приведенная к одному витку
        sinTetaSmall = Math.sqrt(1 - e * e) * Math.sin(Ea) / (1 - e * Math.cos(Ea));
        cosTetaSmall = (Math.cos(Ea) - e) / (1 - e * Math.cos(Ea));
        tetaSmall = Math.asin(sinTetaSmall);
        if (sinTetaSmall > 0 && cosTetaSmall < 0) tetaSmall = tetaSmall + Math.PI;
        if (sinTetaSmall < 0 && cosTetaSmall < 0) tetaSmall = Math.PI - tetaSmall;
        if (sinTetaSmall < 0 && cosTetaSmall > 0) tetaSmall = 2 * Math.PI - tetaSmall;


        //r = a * (1 - e * Math.cos(E));
        u = W + tetaSmall;
        sinFi = Math.sin(iRad) * Math.sin(u);
        cosFi = Math.sqrt(1 - sinFi * sinFi);
        fi = Math.asin(sinFi);
        sinlambda = (Math.sin(omega) * Math.cos(u) + Math.cos(omega) * Math.cos(iRad) * Math.sin(u)) / Math.cos(fi);
        coslambda = (Math.cos(omega) * Math.cos(u) - Math.sin(omega) * Math.cos(iRad) * Math.sin(u)) / Math.cos(fi);

        if (sinlambda > 0 && coslambda > 0)
            lambda = Math.asin(sinlambda);
        if (sinlambda > 0 && coslambda < 0)
            lambda = Math.PI + Math.asin(sinlambda);
        if (sinlambda < 0 && coslambda < 0)
            lambda = Math.PI - Math.asin(sinlambda);
        if (sinlambda < 0 && coslambda > 0)
            lambda = -Math.asin(sinlambda);

        //lambda = lambda - omegaZemli * t - dOmega * t / Tzv; //----------------------------------------------------------
        lambda = lambda - omegaZemli * (t-(24*3600)*((int)(t/(24*3600)))) - dOmega * t / Tzv; //----------------------------------------------------------
        if (lambda < -Math.PI) lambda = lambda + 2 * Math.PI;
        if (lambda < -Math.PI) lambda = lambda + 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;

        x = x0i + ((int)(mX * lambda * 180 / Math.PI));
        y = y0i - ((int)(mY * fi * 180 / Math.PI));
//        log.info("fi=" + fi + "\n" + "lambda=" + lambda);
        return new Coordinate(y, x);
    }
}