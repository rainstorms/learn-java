package rain.multithread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SimpleTask {
    private final int mIndex;
    private Executor mExecutors = Executors.newSingleThreadExecutor();

    public SimpleTask(int index) {
        mIndex = index;
    }

    public void runTask(int y) {
        mExecutors.execute(() -> {
//            System.out.println(mIndex);
            System.out.println(y);
            System.out.println(Thread.currentThread().getName());
        });

    }

    public Executor getmExecutors() {
        return mExecutors;
    }

    public int getmIndex() {
        return mIndex;
    }
}
