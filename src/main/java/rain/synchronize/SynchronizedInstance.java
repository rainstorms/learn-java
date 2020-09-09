package rain.synchronize;

import java.util.concurrent.CountDownLatch;

/**
 * 对象锁
 * 加对象锁的方式
 * 1、普通方法签名上 加 synchronized 例如： public synchronized void method4() {}
 * 2、需要同步的代码块外 加 synchronized 例如： synchronized (this) {// 同步代码块}
 * <p>
 * 每个对象都有自己的对象锁，对象锁之间互不干扰
 * 例如：
 * 情况1：thread1 访问 instance1 的 synchronized method1方法，
 * thread2 还能访问 instance2 的 所有方法 包括 synchronized 的代码块。
 * <p>
 * 情况2：thread1 访问 instance1 的 synchronized method1方法，
 * thread2 还能访问 instance1 除了加了对象锁的方法，其它方法都可以反问，包括 static synchronized 的方法：即类锁和对象锁之间不影响
 */
public class SynchronizedInstance {

    public static synchronized void method1() {
        System.out.println("访问 static synchronized方法");
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

    public synchronized void sleep() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始休息");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "结束休息");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleep1() {
        synchronized (this) {
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
//        SynchronizedInstance synchronizedInstance = new SynchronizedInstance();
        SynchronizedInstance synchronizedInstance1 = new SynchronizedInstance();

        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            synchronizedInstance1.sleep1();

            countDownLatch.countDown();
        }, "t1").start();

        Thread.sleep(100);

        new Thread(() -> {
            System.out.println("t2");
//            synchronizedInstance1.method4();
            synchronizedInstance1.method1();
            synchronizedInstance1.method2();
            synchronizedInstance1.method3();

            countDownLatch.countDown();
        }, "t2").start();

        countDownLatch.await();
    }
}
