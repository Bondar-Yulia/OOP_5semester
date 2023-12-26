import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NonBlockingQueueTest {

    @org.junit.Test
    @Test
    public void testEnqueueDequeue() {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        // Enqueue operation
        queue.enqueue(1);
        queue.enqueue(2);

        // Dequeue operation
        assertEquals(1, queue.dequeue());
        assertEquals(2, queue.dequeue());
    }

    @org.junit.Test
    @Test
    public void testEmptyQueueDequeue() {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        // Dequeue from an empty queue should return null
        assertNull(queue.dequeue());
    }

    @org.junit.Test
    @Test
    public void testConcurrentEnqueueDequeue() throws InterruptedException {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        // Create two threads to enqueue and dequeue concurrently
        Thread enqueueThread = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                queue.enqueue(i);
            }
        });

        Thread dequeueThread = new Thread(() -> {
            for (int i = 1; i <= 1000; i++) {
                Integer value = queue.dequeue();
                assertNotNull(value);
                assertEquals(i, value);
            }
        });

        enqueueThread.start();
        dequeueThread.start();

        enqueueThread.join();
        dequeueThread.join();
    }
}
