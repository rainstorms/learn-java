package rain.designpattern.singleton;

public class xx {
    public static void main(String[] args) {
        EagerSingleton eagerSingleton = EagerSingleton.getInstance();

        LazySingleton lazySingleton = LazySingleton.getInstance();

        EnumEagerSingleton singleton = EnumEagerSingleton.getInstance();
    }
}
