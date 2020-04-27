package rain.designpattern.observer;

public class Subscribe2 implements Subcribe{
    @Override public void update() {
        System.out.println("订阅者2号 更新了");
    }
}
