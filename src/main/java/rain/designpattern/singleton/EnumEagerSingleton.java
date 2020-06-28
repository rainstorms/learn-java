package rain.designpattern.singleton;

public class EnumEagerSingleton {

    // 枚举类型是线程安全的，并且只会装载一次
    private enum EagerSingletonEnum {
        INSTANCE;
        // 声明单例对象
        private final EnumEagerSingleton instance;

        // 实例化
        EagerSingletonEnum() {
            instance = new EnumEagerSingleton();
        }

        private EnumEagerSingleton getInstance() {
            return instance;
        }
    }

    // 获取实例（单例对象）
    public static EnumEagerSingleton getInstance() {
        return EagerSingletonEnum.INSTANCE.getInstance();
    }

    private EnumEagerSingleton() {
    }

}
