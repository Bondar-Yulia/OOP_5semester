import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
public interface CyclicBarrierInterface {
    void await() throws InterruptedException,
            BrokenBarrierException;
    void await(long timeout, TimeUnit timeUnit) throws
            InterruptedException, BrokenBarrierException, TimeoutException;
    int getNumberWaiting();
    int getParties();
    boolean isBroken();
    void reset();
}