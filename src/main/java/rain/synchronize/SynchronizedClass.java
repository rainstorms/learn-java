package rain.synchronize;

import java.util.concurrent.CountDownLatch;

/**
 * 类锁
 * 加类锁的方式（只有两种，给 synchronized (成员属性) 是对象锁）
 * 1、静态方法 加 synchronized 例如: public static synchronized void method1() {}
 * 2、方法体外 加 synchronized 不管方法签名有没有static 例如:
 * public void method1() {
 * System.out.println("synchronized 方法体外 的别的逻辑");
 * synchronized (SynchronizedClass.class) {}
 * }
 * 第一种方式等同于
 * public void method1() {
 * //进来就锁住
 * synchronized (SynchronizedClass.class) {}
 * }
 *
 * <p>
 * 推荐第二种
 * 因为 加上类锁后，别的线程不能再访问 同样加了类锁的代码（加在方法上的，不能访问方法；加在代码块外面的，不能访问代码块）
 * （除了加了类锁的方法，其它方法都可以反问，包括 synchronized 的方法：即类锁和对象锁之间不影响）
 * <p>
 * 第一种是对整个方法加锁，第二种对代码块加锁（缩小的加锁范围，可以访问的内容）
 */
public class SynchronizedClass {

    public static synchronized void method1() {
        System.out.println("访问 static synchronized方法");
    }

    public void method11() {
        System.out.println("访问 static synchronized 方法体外");
        synchronized (SynchronizedClass.class) {
            System.out.println("访问 static synchronized方法体");
        }
    }

    public static void method2() {
        System.out.println("访问 static 方法");
    }

    public synchronized void method4() {
        System.out.println("访问 synchronized 方法");
    }

    public void method3() {
        System.out.println("访问 普通 方法");
    }

    public static synchronized void sleep() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始休息");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "结束休息");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep1() {
        synchronized (SynchronizedClass.class) {
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
        SynchronizedClass synchronizedClass = new SynchronizedClass();
        SynchronizedClass synchronizedClass1 = new SynchronizedClass();

        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            synchronizedClass.sleep1();

            countDownLatch.countDown();
        }, "add1").start();

        Thread.sleep(100);

        new Thread(() -> {
            System.out.println("进入add2");
            synchronizedClass1.method11();
            synchronizedClass1.method2();
            synchronizedClass1.method3();
            synchronizedClass1.method4();
            countDownLatch.countDown();
        }, "add2").start();

        countDownLatch.await();
    }
}
