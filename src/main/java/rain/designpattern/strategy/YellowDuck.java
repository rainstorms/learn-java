package rain.designpattern.strategy;

public class YellowDuck extends Duck {
    public YellowDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new MuteQuack();
    }

    public static void main(String[] args) {
        Duck yellowDuck = new YellowDuck();
        yellowDuck.performFly();
        yellowDuck.performQuack();
    }
}
