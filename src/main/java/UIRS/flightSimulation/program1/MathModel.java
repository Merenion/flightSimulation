package UIRS.flightSimulation.program1;

public class MathModel{
    private static final int Rz = 6371; //радиус Земли
    private static final int mu = 398602; //гравитационный параметр Земли
    private static final double w = 0.0000729211; //угловая скорость вращения Земли

    private static float dt = 0; //шаг расчета по времени

    //переменные
    private double  rpi, ra, e, a, Tzv, p, dOmega, omega, dw, W, n, dt_sr, t_zv, M, E0, M1, dE, cosTetaSmall,
        tetaSmall, r, u, sinFi, fiGa, sinlambda, lambdaGa, fi, lambda;

    // i - угол наклона плоскости орбиты
    // barrel0 - долгота восходящего узла орбиты                                                                            !!
    //  w0 - начальный аргучент перигея орбиты
    //  Hpi - высота перигея орбиты
    //  Ha - высота апогея орбиты
    //  t0 - начальное время
    // orbit - тип орбиты (true - круговая, else - элиптическая)
    public void flyModel(float i, float omega0, float w0, float Hpi, float Ha, float t0, boolean orbit){
/*
        rpi = Rz + Hpi;
        ra = Rz + Ha;                                   
        e = (ra - rpi) / (ra + rpi);                      
        a = (rpi + ra) / 2;
        Tzv = 2 * Math.PI * Math.sqrt(Math.pow(a, 3)/mu);
        p = a * (1 - Math.pow(e, 2));
        dOmega = -35.052 / 60 * Math.pow((Rz / p), 2) * Math.cos(i); //Что такое черточка???                                !!
        omega = omega0 + t / Tzv * dOmega;
        dw = -17.525 / 60 * Math.pow((Rz / p), 2) * (1 - 5 * Math.pow(Math.cos(i), 2));
        W = w0 + t / Tzv * dw;
        n = Math.sqrt(mu / Math.pow(a, 3));
        dt_sr = t - tau;
        t_zv = 1.00273791 * dt_sr;
        M = t_zv * n;
        E0 = M + e * Math.sin(M) + Math.pow(e, 2) / 2 * Math.sin(2 * M) +
        Math.pow(e, 3) / (2 * 3 * 2) * (Math.pow(3, 2) * Math.sin(3 * M) - 3 * Math.sin(M)); //как далеко идти в ряд?       !!
        M1 = E0 - e * Math.sin(E0);
        dE = (M - M1) / (1 - e * Math.cos(E0));
        E = E0 + dE; //для Е нужен цикл, для заданной точности                                                              !!
        cosTetaSmall = (Math.cos(E) - e)/(1 - e * Math.cos(E));
        tetaSmall = Math.acos(cosTetaSmall);
        r = a * (1 - e * Math.cos(E));
        // 0 - круговая орбита, else элиптическая
        if (orbit){
            fi = Math.asin(Math.sin(i) * Math.sin(2 * Math.PI * t / Tzv));
            lambda = omega + Math.atan(Math.cos(i) * Math.tan(2 * Math.PI * t / Tzv)) - w * t +
                dOmega / Tzv;
        } else {
            u = w + tetaSmall;
            sinFi = Math.sin(i) * Math.sin(u);
            fiGa = Math.asin(sinFi);
            sinlambda = (Math.sin(omega) * Math.cos(u) + Math.cos(omega) * Math.cos(i) * Math.sin(u)) / Math.cos(fiGa);
            lambdaGa = Math.asin(sinlambda);
            fi = fiGa;
            lambda = lambdaGa - w * t - dOmega * t / Tzv;
        } */
    }
}