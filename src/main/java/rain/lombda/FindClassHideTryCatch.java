package rain.lombda;

public class FindClassHideTryCatch {
    // 1、先定义函数式接口
    // 意味着 可以将行为（即方法体）变成参数
    @FunctionalInterface
    public interface ClassFindInterface {
        Class<?> className2Class(String className) throws ClassNotFoundException;
    }

    // 2、将想隐藏的代码放在新方法中，通过函数接口去调用
    public static Class<?> classFind(ClassFindInterface classFindInterface, String className) {
        try {
            return classFindInterface.className2Class(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // 3、调用新的方法，
        // 第一个参数是方法体，即原来的 Class.forName(className)
        // 第二个参数是className
        classFind(className -> Class.forName(className), "xx");

        //可以简化为：
        classFind(Class::forName, "xx");

        // 一般写法
        try {
            Class<?> xx = Class.forName("xx");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
