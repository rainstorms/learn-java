package rain.volatileL;

public class LearVolatile {

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                for (int i1 = 0; i1 < 1000; i1++) {
//                    counter.add();
//                }
//                System.out.println("done");
//            }).start();
//        }
//
//        Thread.sleep(500);
//        System.out.println(counter.i);

//        boolean condition = false;
//        Double value1 = 1.0D;
//        Double value2 = 2.0D;
//        Double value3 = null;
//        Integer value4 = null;
//// 返回类型为Double,不抛出空指针异常
//        Double result1 = condition ? value1 : value3;
//// 返回类型为double,会抛出空指针异常
////        Double result2 = condition ? value1 : value4;
//// 返回类型为double,不抛出空指针异常
//        Double result3 = !condition ? value1 * value2 : value3;
//        Double result5 = condition ? value3 : value1 * value2;
//// 返回类型为double,会抛出空指针异常
//        Double result4 = condition ? value1 * value2 : value3;
//        short s1 = 1; s1 = (short) (s1 + 1);
//        short s1 = 1; s1 += 1;
//        System.out.println(10/2+1);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1001; i1++) {
                    counter.add();
                }
                System.out.println("done");
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    counter.dec();
                }
                System.out.println("done");
            }).start();
        }

        Thread.sleep(500);
        System.out.println(counter.i);
    }

}
