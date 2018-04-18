package UIRS.flightSimulation;


public class TestMain {

    public int x = 0;

    class FirstLevel {

        public int x = 1;

        void methodInFirstLevel(int x) {
            System.out.println("x = " + x);
            System.out.println("this.x = " + this.x);
            System.out.println("ShadowTest.this.x = " +
                    TestMain.this.x);
        }
    }

    public static void main(String... args) {
        TestMain st = new TestMain();
        TestMain.FirstLevel fl = st.new FirstLevel();
        fl.methodInFirstLevel(23);
    }
}
