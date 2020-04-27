package rain.designpattern.strategy;

public class MuteQuack implements QuackBehavior {
    @Override public void quack() {
        System.out.println("不会叫");
    }
}
