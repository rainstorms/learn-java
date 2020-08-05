package rain.multithread;

import java.util.concurrent.*;

public class TestMain {
//    public static void main(String[] args) {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());
//        System.out.println("核心线程空闲超时是否关闭:" + executor.allowsCoreThreadTimeOut());//核心线程空闲超时是否关闭:false
//        executor.allowCoreThreadTimeOut(true);
//        for (int i = 0; i < 15; i++) {
//            MyTask myTask = new MyTask(i);
//            executor.execute(myTask);
//            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
//                    executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
//        }
//
//        while (true) {
//            System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
//                    executor.getQueue().size() + "，已执行完的任务数目：" + executor.getCompletedTaskCount());
//
//        }
//    }


    public static void main(String[] args) {
        String processName =
                java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(Long.parseLong(processName.split("@")[0]));

        for (int i = 0; i < 10; i++) {
            ThreadPoolExecutor.CallerRunsPolicy handler = new ThreadPoolExecutor.CallerRunsPolicy();
            ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 10,
                    10, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(5), handler);
            executorService.allowCoreThreadTimeOut(true);

            int finalI = i;
            executorService.execute(() -> {
                SimpleTask simpleTask = new SimpleTask(finalI);
                System.out.println(simpleTask.getmIndex());
            });
//            executorService.shutdown();
        }
        try {
            //给System.gc()提供一定的时间
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
