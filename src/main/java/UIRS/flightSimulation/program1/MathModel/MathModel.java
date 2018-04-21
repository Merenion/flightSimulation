package UIRS.flightSimulation.program1.MathModel;

import UIRS.flightSimulation.program1.CenterPane;
import UIRS.flightSimulation.program1.InitialCharacteristics;

import java.util.logging.Logger;

public class MathModel implements IMathModel {
    public MathModel(CenterPane centerPane, int t) {
        this.centerPane = centerPane;
        this.t = t;
    }

    private static Logger log = Logger.getGlobal();


    //координаты центра и массштаб
    private CenterPane centerPane;

    // входные паременные
    private InitialCharacteristics initCh = InitialCharacteristics.getInitialCharacteristics();
    private int t;

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

    //угол между эклиптикой и экватором
    private double dc = 23.343 * Math.PI / 180;

    //другие переменные
    private double tau;

    //проекции вектора S в неподвижной геоцентрической СК
    private double ac;
    private double Sx;
    private double Sy;
    private double Sz;

    //целое количество суток полета
    private int Nsut;
    private int Ncoil;

    //Определениен координат КА в абсолютной геоцентрической СК (переход от оскулирующей СК)
    private double Xga;
    private double Yga;
    private double Zga;

    //координат единичного вектора направлени на Солнце
    private double shirotaZenitnTochki;
    private double dolgotaZenitnTochki;


    @Override
    public void rashetPorb() {  //Расчет начальных параметров орбиты
        iRad = initCh.getI() * Math.PI / 180;
        omega0Rad = initCh.getOmega0() * Math.PI / 180;
        w0Rad = initCh.getW0() * Math.PI / 180;
        ra = Rz + initCh.getHa();                                       //радиус апогея
        rpi = Rz + initCh.getHpi();                                     //радиус перигея
        e = (ra - rpi) / (ra + rpi);                                    //Эксцентриситет орбиты
        a = (rpi + ra) / 2;                                             //Большая полурсь
        p = a * (1 - e * e);                                            //расчет фокального параметра орбиты
        r = p / (1 + e * Math.cos(tetaSmall));                          //радиус-вектор КА от центра
        H = r - Rz;                                                     //высота полета
        Tzv = 2 * Math.PI * Math.sqrt(Math.pow(a, 3) / mu);             //Период обращения звездный
        //Расчет векового возмущения первого порядка:
        dOmega = -35.052 / 60 * Math.PI / 180 * Math.pow((Rz / p), 2) * Math.cos(iRad);
        //Расчет векового возмущения аргумента перигея орбиты первого порядка:
        dw = -17.525 / 60 * Math.PI / 180 * Math.pow((Rz / p), 2) * (1 - 5 * Math.pow(Math.cos(iRad), 2));
    }


    private Coordinate flyModel() {
        rashetPorb();
        Ncoil =(int)(t/Tzv);                                       //Целое количество витков с начала полета (с 21 марта)
        Nsut = (int) (t / (24 * 3600));                            //сутки полета
        omega = omega0Rad + t / Tzv * dOmega;                      //Текущий угол восходящего узла орбиты, рад
        W = w0Rad * Math.PI / 180 + t / Tzv * dw;                  //текущее значение аргумента перигея
        n = Math.sqrt(mu / Math.pow(a, 3));                        //Определение среднего движения
        dt_sr = t - tau;                                           //Определение промежутка среднего времени с момента прохождения перигея до момента наблюдения
        t_zv = 1.00273791 * dt_sr;                                 //Определение звездного времени
        M = t_zv * n;                                              //Определение средней аномалии
        double Tpsv=2*Math.PI*Math.sqrt(a*a*a/mu);                 //Период обращения звездный
        double E01 = M + e * Math.sin(M) + (e * e / 2) * Math.sin(2 * M);        //Первый член разложения уравнения Кеплера (E-e*sin(E)=M
        double E02 = e * e * e / 24 * (9 * Math.sin(3 * M) - 3 * Math.sin(M)); //Второй член разложения и т.д.
        double E03 = Math.pow(e, 4) / (24 * 8) * (64 * Math.sin(4 * M) - 32 * Math.sin(2 * M));
        double E04 = Math.pow(e, 4) / (120 * 16) * (625 * Math.sin(5 * M) + 5 * 81 * Math.sin(3 * M) + 10 * Math.sin(M));
        double E05 = Math.pow(e, 5) / (720 * 32) * (36 * 36 * 6 * Math.sin(6 * M) - 6 * 256 * 4 * Math.sin(4 * M) + 15 * 32 * Math.sin(2 * M));
        double Ea = E01 + E02 + E03 + E04 + E05;                           //Эксцентрическая аномалия
        double Ea0 = Ea - 2 * Math.PI * ((int) (Ea / (2 * Math.PI)));         //Эксцентрическая аномалия приведенная к одному витку
        sinTetaSmall = Math.sqrt(1 - e * e) * Math.sin(Ea) / (1 - e * Math.cos(Ea));
        cosTetaSmall = (Math.cos(Ea) - e) / (1 - e * Math.cos(Ea));

        //расчт угла истинной аномалии
        tetaSmall = Math.atan(Math.sqrt(1 - cosTetaSmall * cosTetaSmall) / cosTetaSmall);
        if (sinTetaSmall > 0 && cosTetaSmall < 0) tetaSmall = tetaSmall + Math.PI;
        if (sinTetaSmall < 0 && cosTetaSmall < 0) tetaSmall = Math.PI - tetaSmall;
        if (sinTetaSmall < 0 && cosTetaSmall > 0) tetaSmall = 2 * Math.PI - tetaSmall;

        u = W + tetaSmall;
        sinFi = Math.sin(iRad) * Math.sin(u);
        cosFi = Math.sqrt(1 - sinFi * sinFi);
        fi = Math.atan(sinFi / Math.sqrt(1 - sinFi * sinFi));
        sinlambda = (Math.sin(omega) * Math.cos(u) + Math.cos(omega) * Math.cos(iRad) * Math.sin(u)) / Math.cos(fi);
        coslambda = (Math.cos(omega) * Math.cos(u) - Math.sin(omega) * Math.cos(iRad) * Math.sin(u)) / Math.cos(fi);
        if (sinlambda > 0 && coslambda > 0)
            lambda = Math.atan(sinlambda / Math.sqrt(1 - sinlambda * sinlambda));
        if (sinlambda > 0 && coslambda < 0)
            lambda = Math.PI + Math.atan(Math.sqrt(1 - coslambda * coslambda) / coslambda);
        if (sinlambda < 0 && coslambda < 0)
            lambda = Math.PI - Math.atan(sinlambda / Math.sqrt(1 - sinlambda * sinlambda));
        if (sinlambda < 0 && coslambda > 0)
            lambda = -Math.atan(Math.sqrt(1 - coslambda * coslambda) / coslambda);
        double kk = lambda;
        lambda = lambda - omegaZemli * (t - (24 * 3600) * ((int) (t / (24 * 3600)))) - dOmega * t / Tzv; //----------------------------------------------------------
        if (lambda < -Math.PI) lambda = lambda + 2 * Math.PI;
        if (lambda < -Math.PI) lambda = lambda + 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;
        if (lambda > Math.PI) lambda = lambda - 2 * Math.PI;

        x = centerPane.getX0i() + ((int) (centerPane.getmX() * (float) lambda * 180 / Math.PI));
        y = centerPane.getY0i() - ((int) (centerPane.getmY() * (float) fi * 180 / Math.PI));
        return new Coordinate(y, x);
    }

    //Вычисление проекции вектора S в неподвижной геоцентрической СК
    private void calculationProjectionVectorS(int t) {
        ac = 2 * Math.PI * t / (365 * 24 * 3600);
        Sx = Math.cos(ac);
        Sy = Math.sin(ac) * Math.cos(dc);
        Sz = Math.sin(ac) * Math.sin(dc);
    }

    //Расчет координат единичного вектора в направлени на Солнце в неподвижной геоцентрической системе координат
    private void rashetEdVektNapravlNaSolnzVNepodvSistKoord(int t) {
        //Расчет угла между направлением на точку весеннего равноденствия
        // и направлением на Солнце
        double ac = 2 * Math.PI / (365.2422 * 24 * 3600) * t;

        //Расчет проекций вектора
        double Sx = Math.cos(ac);
        double Sy = Math.sin(ac) * Math.cos(dc);
        double Sz = Math.sin(ac) * Math.sin(dc);

        //Расчет координат центра светового пятна
        // Расчет широты точки поверхности Земли, в которой Солнце нахдится в зените
        shirotaZenitnTochki = Math.asin(Sz);                                                //Ранее была ошибка arctan(Sz) (Науменко);

        // Расчет долготы точки поверхности Земли, в которой Солнце нахдится в зените
        double Cos_Dolgota_Zenitn_Tochki = Sx / Math.cos(shirotaZenitnTochki);
        double Sin_Dolgota_Zenitn_Tochki = Sy / Math.cos(shirotaZenitnTochki);

//        //Предохранение от неточностей расчета
//        if (Sin_Dolgota_Zenitn_Tochki > 1)  Sin_Dolgota_Zenitn_Tochki=1;
//        if (Sin_Dolgota_Zenitn_Tochki < -1) Sin_Dolgota_Zenitn_Tochki=-1;
//        if (Cos_Dolgota_Zenitn_Tochki > 1) Cos_Dolgota_Zenitn_Tochki=1;
//        if (Cos_Dolgota_Zenitn_Tochki < -1) Cos_Dolgota_Zenitn_Tochki=-1;

        //Определение квадранта, в котором находится Зенитна точка
        //в неподвижной системе координат
        if ((Sin_Dolgota_Zenitn_Tochki > 0) && (Cos_Dolgota_Zenitn_Tochki > 0))
            dolgotaZenitnTochki = Math.asin(Sin_Dolgota_Zenitn_Tochki);
        if ((Sin_Dolgota_Zenitn_Tochki > 0) && (Cos_Dolgota_Zenitn_Tochki < 0))
            dolgotaZenitnTochki = Math.PI - Math.asin(Sin_Dolgota_Zenitn_Tochki);
        if ((Sin_Dolgota_Zenitn_Tochki < 0) && (Cos_Dolgota_Zenitn_Tochki < 0))
            dolgotaZenitnTochki = -Math.PI - Math.asin(Sin_Dolgota_Zenitn_Tochki);
        if ((Sin_Dolgota_Zenitn_Tochki < 0) && (Cos_Dolgota_Zenitn_Tochki > 0))
            dolgotaZenitnTochki = Math.asin(Sin_Dolgota_Zenitn_Tochki);
        //Учет скорости вращения Земли
        dolgotaZenitnTochki = dolgotaZenitnTochki - omegaZemli * (t - (24 * 3600) * (int) t / (24 * 3600)) - t / Tzv * dOmega;

    }

    //Определениен координат КА в абсолютной геоцентрической СК (переход от оскулирующей СК)
    private void koordKAxyzGa() {
        Xga = r * (Math.cos(omega) * Math.cos(u) - Math.sin(omega) * Math.sin(u) * Math.cos(iRad));
        Yga = r * (Math.sin(omega) * Math.cos(u) + Math.cos(omega) * Math.sin(u) * Math.cos(iRad));
        Zga = r * Math.sin(u) * Math.sin(iRad);
    }

    @Override
    public boolean isInTheSun() {
        rashetEdVektNapravlNaSolnzVNepodvSistKoord(t);
        double alfa_Ten_Semli = Math.acos(Rz / (Rz + H));  //Определение центрального угла для КА, находящегося на границе тени Ранее была ошибка (Науменко) arcsin(Rs/(Rs+H));
        double Shirota_Zentra_Teti = -shirotaZenitnTochki; //Определение широты центра теневого пятна
        double Dolgota_Zentra_Teti = 0;

        if ((dolgotaZenitnTochki >= 0) && (dolgotaZenitnTochki <= Math.PI)) //Определение долготы центра теневого пятна
            Dolgota_Zentra_Teti = -Math.PI - dolgotaZenitnTochki;
        if ((dolgotaZenitnTochki > -Math.PI) && (dolgotaZenitnTochki < 0))
            Dolgota_Zentra_Teti = Math.PI + dolgotaZenitnTochki;

        //Условие вхождения КА в тень Земли
        return Math.abs(Math.acos(Math.sin(Shirota_Zentra_Teti) * Math.sin(fi)
                + Math.cos(Shirota_Zentra_Teti) * Math.cos(fi) * Math.cos(lambda - Dolgota_Zentra_Teti)))
                < alfa_Ten_Semli;
    }

    //округление любого числа
    private double okrug(double value, int n) {
        return (Math.rint(Math.pow(10, n) * value) / Math.pow(10, n));
    }


    @Override
    public double getW() {
        return W;
    }

    @Override
    public double getTeta() {
        return tetaSmall;
    }

    @Override
    public double getFi() {
        return fi;
    }

    @Override
    public double getLambda() {
        return Math.asin(sinlambda);
    }

    @Override
    public double getSinlambda() {
        return sinlambda;
    }

    @Override
    public double getCoslambda() {
        return coslambda;
    }

    @Override
    public int getNcoil() {
        return Ncoil;
    }

    @Override
    public String toString() {
        return "Арг.Пер.= " + okrug(W, 7) +
                "\nУг.ист.ан.= " + okrug(tetaSmall, 4) +
                "\nШирота= " + okrug(fi, 4) +
                "\nДолгота= " + okrug(getLambda(), 4) +
                "\nsinLambda= " + okrug(sinlambda, 4) +
                "\ncosLambda= " + okrug(coslambda, 4);
    }

    public String getOtheParameters () {
        koordKAxyzGa();
        return "Коорд. КА в абс. геоцентр. СК"
                +"\nX=" + Xga
                +"\nY=" + Yga
                +"\nZ=" + Zga;
    }
}