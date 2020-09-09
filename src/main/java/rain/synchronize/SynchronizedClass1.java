package rain.synchronize;

import lombok.Setter;

import java.util.concurrent.CountDownLatch;

/**
 * 类锁测试
 * synchronized (成员属性) 到底是不是类锁
 * 情况1：thread1 访问 synchronized (SynchronizedClass1.class)后
 * thraed2 还是可以正常访问 method1
 * 结论：不存在同步关系
 * <p>
 * 情况1：thread1 访问 static synchronized 后
 * thraed2 还是可以正常访问 method1
 * 结论：不存在同步关系
 *
 * 总结：synchronized (成员属性) 不是类锁
 */
@Setter
public class SynchronizedClass1 {

    public static final String a = "1";

    // synchronized  (成员属性a)
    public static void method1() {
        System.out.println("访问 method1 方法1");
        synchronized (a) {
            System.out.println("访问 method1 方法2");
        }
    }

    public static void sleep() {
        synchronized (SynchronizedClass1.class) {
            try {
                System.out.println(Thread.currentThread().getName() + "开始休息");
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "结束休息");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static synchronized void method2() {
        System.out.println("访问 method1 方法1");
    }

    public static synchronized void sleep1() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始休息");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "结束休息");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedClass1 synchronizedClass = new SynchronizedClass1();

        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            sleep1();

            countDownLatch.countDown();
        }, "add1").start();

        Thread.sleep(100);

        new Thread(() -> {
            System.out.println("进入add2");
            synchronizedClass.method1();
            countDownLatch.countDown();
        }, "add2").start();

        countDownLatch.await();
    }
}
