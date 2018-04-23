package UIRS.flightSimulation.program1;

public interface IWriter {
    /**Класс реализующий данный интерфейс, выдает различные характиристики (Строкой), которые
     * в дальнейшем можно поместить к примеру в TextArea. Использует для расчета математическую модель*/
    String getTiming (int t);
    String getMainParameters (int t);
    String getOtheParamerers (int t);
}
