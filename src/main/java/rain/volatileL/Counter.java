package rain.volatileL;

public class Counter {
     int i = 0;

    public synchronized void add() {
        i = i + 1;
    }

    public synchronized void dec() {
        i = i - 1;
    }
}
