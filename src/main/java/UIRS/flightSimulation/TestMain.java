package UIRS.flightSimulation;

public class TestMain {

    static volatile int i =0;

    public static void main(String[] args) {
        new ThreadRead().start();
        new ThreadWriter().start();
        System.out.println("end");
    }

    static class ThreadRead extends Thread{
        @Override
        public void run() {
            while (i<50) {
                System.out.println("увеличение до" + (++i));
                ThreadRead.yield();
            }
        }
    }

    static class ThreadWriter extends Thread{
        @Override
        public  void run() {
            int inew = i;
            while (inew<50) {
                if (inew!=i) {
                    System.out.println(" inew less i");
                    inew = i;
                }
            }
        }
    }

}