package UIRS.flightSimulation.program1;

import java.util.logging.Logger;

public class MathModel {
    private static final int Rz = 6371; //радиус Земли
    private static final int mu = 398602; //гравитационный параметр Земли
    private static final double w = 0.0000729211; //угловая скорость вращения Земли
    private static Logger log = Logger.getGlobal();

    // входные переменные
    private double i;
    private double omega0;
    private double w0;
    private double Hpi;
    private double Ha;
    private double t0;

    public void setTestData() {
        i = 98.3;
        omega0 = 1;
        w0 = 1;
        Hpi = 729;
        Ha = 729;
        t0 = 0;
    }

    public Coordinate startTest () {
        setTestData();
        for (int i=1 ; i<1000; i++) {
            Coordinate coordinate1 = new Coordinate();
            coordinate1 = flyModel(i);
            CordinateSinCos coordinate2 = conversionRelativeToThePlaneInSinCos(coordinate1);
            Coordinate coordinate3 = endCoordinatePlane(coordinate2);
            System.out.println(coordinate3+"\n_____________________");
        }
        return  null;
    }

    //другие переменные
    private double tau = 0;
    private static float dt = 1; //шаг расчета по времени

    /**
     * i - угол наклона плоскости орбиты
     * barrel0 - долгота восходящего узла орбиты                                                                            !!
     * w0 - начальный аргучент перигея орбиты
     * Hpi - высота перигея орбиты
     * Ha - высота апогея орбиты
     * t0 - начальное время
     * orbit - тип орбиты (true - круговая, else - элиптическая)
     */
    public Coordinate flyModel(float t) {
        double rpi, ra, e, a, Tzv, p, dOmega, omega, dw, W, n, dt_sr, t_zv, M, E0, M1, dE, cosTetaSmall,
                tetaSmall, r, u, sinFi, fiGa, sinlambda, lambdaGa, fi, lambda;

        rpi = Rz + Hpi;                                           //радиус перигея
        ra = Rz + Ha;                                             //радиус апогея
        e = (ra - rpi) / (ra + rpi);                              //Эксцентриситет орбиты
        a = (rpi + ra) / 2;                                       //Большая полурсь
        Tzv = 2 * Math.PI * Math.sqrt(Math.pow(a, 3) / mu);         //Период обращения звездный
        p = a * (1 - Math.pow(e, 2));
        //Расчет векового возмущения первого порядка:
        dOmega = -35.052 / 60 * Math.PI / 180 * Math.pow((Rz / p), 2) * Math.cos(i);
        omega = omega0 + t / Tzv * dOmega;                    //Текущий угол восходящего узла орбиты, рад
        //Расчет векового возмущения аргумента перигея орбиты первого порядка:
        dw = -17.525 / 60 * Math.PI / 180 * Math.pow((Rz / p), 2) * (1 - 5 * Math.pow(Math.cos(i), 2));
        W = w0 * Math.PI / 180 + t / Tzv * dw;
        n = Math.sqrt(mu / Math.pow(a, 3));                        //Определение среднего движения
        dt_sr = t - tau;                                           //Определение промежутка среднего времени с момента
        //прохождения перигея до момента наблюдения
        t_zv = 1.00273791 * dt_sr;                                 //Определение звездного времени
        M = t_zv * n;                                              //Определение средней аномалии

        double E01 = M + e * Math.sin(M) + (e * e / 2) * Math.sin(2 * M);        //Первый член разложения уравнения Кеплера (E-e*sin(E)=M
        double E02 = e * e * e / 24 * (9 * Math.sin(3 * M) - 3 * Math.sin(M)); //Второй член разложения и т.д.
        double E03 = Math.pow(Math.E, 4) / (24 * 8) * (64 * Math.sin(4 * M) - 32 * Math.sin(2 * M));
        double E04 = Math.pow(Math.E, 4) / (120 * 16) * (625 * Math.sin(5 * M) + 5 * 81 * Math.sin(3 * M) + 10 * Math.sin(M));
        double E05 = Math.pow(Math.E, 5) / (720 * 32) * (36 * 36 * 6 * Math.sin(6 * M) - 6 * 256 * 4 * Math.sin(4 * M) + 15 * 32 * Math.sin(2 * M));
        double Ea = E01 + E02 + E03 + E04 + E05;                           //Эксцентрическая аномалия
        double Ea0 = Ea - 2 * Math.PI * ((int) (Ea / (2 * Math.PI)));         //Эксцентрическая аномалия приведенная к одному витку
        M1 = Ea - e * Math.sin(Ea);
        dE = (M - M1) / (1 - e * Math.cos(Ea));
        double E = Ea + dE; //для Е нужен цикл, для заданной точности                                                              !!
        cosTetaSmall = (Math.cos(E) - e) / (1 - e * Math.cos(E));
        tetaSmall = Math.acos(cosTetaSmall);
        r = a * (1 - e * Math.cos(E));
        u = w + tetaSmall;
        sinFi = Math.sin(i) * Math.sin(u);
        fiGa = Math.asin(sinFi);
        sinlambda = (Math.sin(omega) * Math.cos(u) + Math.cos(omega) * Math.cos(i) * Math.sin(u)) / Math.cos(fiGa);
        lambdaGa = Math.asin(sinlambda);
        fi = fiGa;
        lambda = lambdaGa - w * t - dOmega * t / Tzv;
//        log.info("fi=" + fi + "\n" + "lambda=" + lambda);
        return new Coordinate(fi, lambda);
    }

    /**
     * перевод координат на координаты плоскости
     */
    public CordinateSinCos conversionRelativeToThePlaneInSinCos(Coordinate coordinate) {
        return new CordinateSinCos(Math.sin(coordinate.getFi()),
                (Math.sin(coordinate.getLambda()) * Math.cos(coordinate.getFi()))
                        / Math.cos(Math.asin(Math.sin(coordinate.getFi()))), (Math.cos(coordinate.getFi()) *
                Math.cos(coordinate.getLambda()))
                / Math.cos(Math.asin(Math.sin(coordinate.getFi()))));
    }

    private Coordinate endCoordinatePlane(CordinateSinCos csc) {
        Coordinate coordinate = new Coordinate();
//        log.info("sinFi=" + csc.sinFi + "\n" + "sinLambda=" + csc.sinlambda+ "\n" + "cosLambda=" + csc.coslambda);
        if (csc.sinlambda>0 && csc.coslambda>0)
            coordinate.setLambda(Math.asin(csc.sinlambda));
        if (csc.sinlambda>0 && csc.coslambda<0)
            coordinate.setLambda(180-Math.asin(csc.sinlambda));
        if (csc.sinlambda<0 && csc.coslambda<0)
            coordinate.setLambda(180-Math.asin(csc.sinlambda));
        if (csc.sinlambda<0 && csc.coslambda>0)
            coordinate.setLambda(Math.asin(csc.sinlambda));
        coordinate.setFi(Math.asin(csc.sinFi));
//        log.info(coordinate.toString());
        return coordinate;
    }

    class CordinateSinCos {
        private double sinFi;
        private double sinlambda;
        private double coslambda;

        CordinateSinCos(double sinFi, double sinlambda, double coslambda) {
            this.sinFi = sinFi;
            this.sinlambda = sinlambda;
            this.coslambda = coslambda;
        }
    }
}