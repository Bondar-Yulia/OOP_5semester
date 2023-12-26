public class MyPhaser {
    private int parties;
    private int phase;

    public MyPhaser(int parties) {
        if (parties <= 0) {
            throw new IllegalArgumentException("The number of parties must be positive");
        }
        this.parties = parties;
        this.phase = 0;
    }

    public synchronized int arrive() {
        return arriveAndAwaitAdvance();
    }

    public synchronized int arriveAndAwaitAdvance() {
        if (--parties == 0) {
            parties = this.parties;
            notifyAll();
            return advance();
        } else {
            int currentPhase = phase;
            while (currentPhase == phase) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return currentPhase;
        }
    }

    public synchronized int arriveAndDeregister() {
        if (--parties == 0) {
            parties = this.parties;
            notifyAll();
            return advance();
        } else {
            int currentPhase = phase;
            while (currentPhase == phase) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return currentPhase;
        }
    }

    private int advance() {
        phase++;
        notifyAll();
        return phase;
    }

    public synchronized int awaitAdvance(int phase) {
        while (this.phase == phase) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return this.phase;
    }

    public static void main(String[] args) {
        final int THREAD_COUNT = 3;
        final MyPhaser myPhaser = new MyPhaser(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadNumber = i;
            new Thread(() -> {
                System.out.println("Thread " + threadNumber + " arrives");
                myPhaser.arriveAndAwaitAdvance();

                System.out.println("Thread " + threadNumber + " continues");

                // Simulate some work
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println("Thread " + threadNumber + " departs");
                myPhaser.arriveAndDeregister();
            }).start();
        }
    }
    // Другие методы Phaser, такие как bulkRegister, getPhase и т. д., могут быть добавлены по мере необходимости
}
