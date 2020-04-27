package rain.designpattern.strategy;

public class FlyWithWings implements FlyBehavior {
    @Override public void fly() {
        System.out.println("通过翅膀飞");
    }
}
