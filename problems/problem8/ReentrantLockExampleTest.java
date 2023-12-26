import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReentrantLockExampleTest {

    @Test
    public void testLockAndUnlock() throws InterruptedException {
        ReentrantLockExample reentrantLock = new ReentrantLockExample();

        reentrantLock.lock();
        assertTrue(reentrantLock.isLocked);
        reentrantLock.unlock();
        assertFalse(reentrantLock.isLocked);
    }

    @Test
    public void testReentrantLock() throws InterruptedException {
        ReentrantLockExample reentrantLock = new ReentrantLockExample();

        reentrantLock.lock();
        assertTrue(reentrantLock.isLocked);

        // Nested lock
        reentrantLock.lock();
        assertTrue(reentrantLock.isLocked);

        reentrantLock.unlock();
        assertTrue(reentrantLock.isLocked);

        reentrantLock.unlock();
        assertFalse(reentrantLock.isLocked);
    }

    @Test
    public void testMultipleThreads() throws InterruptedException {
        ReentrantLockExample reentrantLock = new ReentrantLockExample();

        Thread thread1 = new Thread(() -> {
            try {
                reentrantLock.lock();
                assertTrue(reentrantLock.isLocked);
                // Do some work
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                reentrantLock.lock();
                assertTrue(reentrantLock.isLocked);
                // Do some work
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertFalse(reentrantLock.isLocked);
    }
}
