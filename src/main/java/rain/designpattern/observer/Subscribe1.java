package rain.designpattern.observer;

public class Subscribe1 implements Subcribe{
    @Override public void update() {
        System.out.println("订阅者1号 更新了");
    }
}
