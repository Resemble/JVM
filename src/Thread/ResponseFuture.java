package Thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2018/7/30 下午11:21
 */
public class ResponseFuture implements Future<String> {
    private final ResponseCallback callback;
    private String responsed;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public ResponseFuture(ResponseCallback callback) {
        this.callback = callback;
    }


    @Override public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override public boolean isDone() {
        return null != this.responsed;
    }

    @Override public String get() throws InterruptedException, ExecutionException {
        if (!isDone()) {
            try {
                this.lock.lock();
                while (!this.isDone()) {
                    condition.await();
                    if (this.isDone()) break;
                }
            } finally {
                this.lock.unlock();
            }
        }
        return this.responsed;
    }

    // 返回完成
    public void done(String responsed) throws Exception {
        this.responsed = responsed;
        try {
            this.lock.lock();
            this.condition.signal();
            if (null != this.callback) this.callback.call(this.responsed);
        } finally {
            this.lock.unlock();
        }
    }

    @Override public String get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
        throw new UnsupportedOperationException();
    }
}
