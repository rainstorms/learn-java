package rain.synchronize;

import lombok.Setter;

import java.util.concurrent.CountDownLatch;

/**
 * 对象锁
 * 前面真的 synchronized (属性) 是对象锁
 *
 * thread1 访问 sleep1
 * thread2可以访问 method1 不能method2
 *
 * 总结说明 多线程只对 synchronized 相同属性的 代码块存在同步
 * 而 public synchronized void method1() {} 等同于 public void method1() {{synchronized (this)} }
 */
@Setter
public class SynchronizedInstance2 {

    private String a;

    public synchronized void method1() {
        System.out.println("访问 synchronized method1 方法");
    }

    public void method2() {
        System.out.println("访问 method2 方法1");
        synchronized (a) {
            System.out.println("访问 method2 方法2");
        }
    }

    public void sleep1() {
        synchronized (a) {
            try {
                System.out.println(Thread.currentThread().getName() + "开始休息");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "结束休息");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedInstance2 synchronizedInstance2 = new SynchronizedInstance2();
        synchronizedInstance2.setA("1");

        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            synchronizedInstance2.sleep1();

            countDownLatch.countDown();
        }, "t1").start();

        Thread.sleep(100);

        new Thread(() -> {
            System.out.println("t2");
            synchronizedInstance2.method1();
            synchronizedInstance2.method2();

            countDownLatch.countDown();
        }, "t2").start();

        countDownLatch.await();
    }
}
