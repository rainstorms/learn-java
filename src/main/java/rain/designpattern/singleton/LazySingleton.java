package rain.designpattern.singleton;

public class LazySingleton {
    private volatile static LazySingleton lazySingletonInstance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (lazySingletonInstance == null) {
            synchronized (LazySingleton.class) {
                lazySingletonInstance = new LazySingleton();
            }
        }
        return lazySingletonInstance;
    }
}
