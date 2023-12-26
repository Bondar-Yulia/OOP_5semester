public class ReentrantLockExample {
    public boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockCount = 0;

    public synchronized void lock() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        while (isLocked && lockedBy != currentThread) {
            wait();
        }
        isLocked = true;
        lockedBy = currentThread;
        lockCount++;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == lockedBy) {
            lockCount--;

            if (lockCount == 0) {
                isLocked = false;
                lockedBy = null;
                notify();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample reentrantLock = new ReentrantLockExample();

        // Example usage
        new Thread(() -> {
            try {
                reentrantLock.lock();
                System.out.println("Thread 1 locked");
                // Do some work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println("Thread 1 unlocked");
            }
        }).start();

        new Thread(() -> {
            try {
                reentrantLock.lock();
                System.out.println("Thread 2 locked");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
                System.out.println("Thread 2 unlocked");
            }
        }).start();
    }
}
