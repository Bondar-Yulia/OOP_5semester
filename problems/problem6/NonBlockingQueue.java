import java.util.concurrent.atomic.AtomicReference;

public class NonBlockingQueue<T> {
    private static class Node<T> {
        T data;
        AtomicReference<Node<T>> next;

        public Node(T data) {
            this.data = data;
            this.next = new AtomicReference<>(null);
        }
    }

    private AtomicReference<Node<T>> head;
    private AtomicReference<Node<T>> tail;

    public NonBlockingQueue() {
        Node<T> dummyNode = new Node<>(null);
        head = new AtomicReference<>(dummyNode);
        tail = new AtomicReference<>(dummyNode);
    }

    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);

        while (true) {
            Node<T> currentTail = tail.get();
            Node<T> next = currentTail.next.get();

            if (currentTail == tail.get()) {
                if (next == null) {
                    // The tail is pointing to the last node
                    if (currentTail.next.compareAndSet(next, newNode)) {
                        // Enqueue operation successful
                        tail.compareAndSet(currentTail, newNode);
                        return;
                    }
                } else {
                    // Another thread has updated the tail, help it by moving the tail
                    tail.compareAndSet(currentTail, next);
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> currentHead = head.get();
            Node<T> currentTail = tail.get();
            Node<T> first = currentHead.next.get();

            if (currentHead == head.get()) {
                if (currentHead == currentTail) {
                    if (first == null) {
                        // The queue is empty
                        return null;
                    }
                    // Another thread is in the process of updating the tail, help it
                    tail.compareAndSet(currentTail, first);
                } else {
                    // Read the value and attempt to move the head
                    T value = first.data;
                    if (head.compareAndSet(currentHead, first)) {
                        // Dequeue operation successful
                        return value;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        NonBlockingQueue<Integer> queue = new NonBlockingQueue<>();

        // Enqueue operation
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        // Dequeue operation
        System.out.println(queue.dequeue()); // Output: 1
        System.out.println(queue.dequeue()); // Output: 2
        System.out.println(queue.dequeue()); // Output: 3
        System.out.println(queue.dequeue()); // Output: null (queue is empty)
    }
}
