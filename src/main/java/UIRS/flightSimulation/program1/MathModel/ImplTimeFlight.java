package UIRS.flightSimulation.program1.MathModel;

public class ImplTimeFlight implements ITimeFlight {



    @Override
    public void addSecond(int value) {
        TypeTime.SEC.setValue(TypeTime.SEC.getValue()+value);
        refreshAllTime();
    }

    @Override
    public void setSecond(int value) {
        TypeTime.SEC.setValue(value);
        refreshAllTime();
    }

    @Override
    public int getTime(TypeTime typeTime) {
        return typeTime.getValue();
    }

    @Override
    public String getStringTime(TypeTime typeTime) {
        return typeTime+": "+typeTime.getValue();
    }

    @Override
    public void resetTime() {
    for (TypeTime tt : TypeTime.values())
        tt.setValue(0);
    }

    @Override
    public String toString() {
        return getStringTime(TypeTime.SEC)+"\n"
                +getStringTime(TypeTime.MIN)+"\n"
                +getStringTime(TypeTime.HOUR)+"\n"
                +getStringTime(TypeTime.DAY);
    }

    @Override
    public String getAllTime(int t) {
        setSecond(t);
        return toString();
    }

    private void refreshAllTime(){
    TypeTime.MIN.setValue((int)(TypeTime.SEC.getValue()/60));
    TypeTime.HOUR.setValue((int)(TypeTime.MIN.getValue()/60));
    TypeTime.DAY.setValue((int)(TypeTime.HOUR.getValue()/24));
    }
}
