package UIRS.flightSimulation.program1;

import UIRS.flightSimulation.program1.MathModel.IMathModel;
import UIRS.flightSimulation.program1.MathModel.ImplMathModel;

public class ImplWriter implements IWriter {
    /**реализация интерфейса ISimulationFlight, описание приведенно под сигнатурой интерфейса*/

    //создаем математическую панель, из которой мы можем получить необходимые нам расчеты
    private IMathModel mathModel = new ImplMathModel();

    /**Выдает тайминг(секунды,минуты,часы и т.д.) времени t*/
    @Override
    public String getTiming(int t) {
        return mathModel.getTimingFlyght(t);
    }

    /**выдает основные параметры полета во время t*/
    @Override
    public String getMainParameters(int t) {
        return mathModel.getMainParameters(t);
    }

    /**выдает дополнительные параметры полета во время t*/
    @Override
    public String getOtheParamerers(int t) {
        return mathModel.getOtheParameters(t);
    }
}
