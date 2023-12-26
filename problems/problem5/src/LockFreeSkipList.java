import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeSkipList<T extends Comparable<? super T>> {
    private static final int MAX_LEVEL = 16;
    private final Node<T> head;
    private final Node<T> tail;

    public LockFreeSkipList() {
        head = new Node<>(Integer.MIN_VALUE);
        tail = new Node<>(Integer.MAX_VALUE);
        for (int i = 0; i < head.next.length; i++) {
            head.next[i] = new AtomicMarkableReference<>(tail, false);
        }
    }

    // Добавляем методы в класс LockFreeSkipList
    public boolean add(T item) {
        int topLevel = randomLevel(); // Генерация случайного уровня для нового узла
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];

        while (true) {
            boolean found = find(item, preds, succs);
            if (found) {
                return false; // Элемент уже существует
            } else {
                Node<T> newNode = new Node<>(item, topLevel);
                for (int i = 0; i <= topLevel; i++) {
                    Node<T> succ = succs[i];
                    newNode.next[i].set(succ, false);
                }
                Node<T> pred = preds[topLevel];
                Node<T> succ = succs[topLevel];
                if (!pred.next[topLevel].compareAndSet(succ, newNode, false, false)) {
                    continue; // Повторить попытку, если не удалось вставить узел
                }

                for (int level = topLevel - 1; level >= 0; level--) {
                    while (true) {
                        pred = preds[level];
                        succ = succs[level];
                        if (pred.next[level].compareAndSet(succ, newNode, false, false)) {
                            break;
                        }
                        find(item, preds, succs); // Найти предыдущие и следующие узлы заново
                    }
                }
                return true; // Успешное добавление
            }
        }
    }

    private int randomLevel() {
        // Генерация случайного уровня для узла
        // Реализуйте согласно вашим требованиям к распределению уровней узлов
        int lvl = 0;
        while (Math.random() < 0.5 && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }


    public boolean contains(T item) {
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        boolean found = find(item, preds, succs);
        return found && succs[0].next[0].getReference() != tail && !succs[0].next[0].isMarked();
    }

    private boolean find(T item, Node<T>[] preds, Node<T>[] succs) {
        int bottomLevel = 0;
        boolean[] marked = {false}; // Используйте массив boolean вместо одиночного значения
        Node<T> pred, curr = null, succ;
        retry:
        while (true) {
            pred = head;
            for (int level = MAX_LEVEL; level >= bottomLevel; level--) {
                curr = pred.next[level].getReference();
                while (true) {
                    succ = curr.next[level].get(marked);
                    while (marked[0]) {
                        succ = curr.next[level].get(marked);
                        if (!pred.next[level].compareAndSet(curr, succ, false, false)) continue retry;
                        curr = pred.next[level].getReference();
                        succ = curr.next[level].get(marked);
                    }
                    if (curr.value.compareTo(item) < 0) {
                        pred = curr;
                        curr = succ;
                    } else {
                        break;
                    }
                }
                preds[level] = pred;
                succs[level] = curr;
            }
            return (curr.value.compareTo(item) == 0);
        }
    }

    public boolean remove(T item) {
        int bottomLevel = 0;
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T> succ;
        boolean[] marked = {false}; // Используйте массив boolean
        while (true) {
            boolean found = find(item, preds, succs);
            if (!found) {
                return false;
            } else {
                Node<T> nodeToRemove = succs[bottomLevel];
                for (int level = nodeToRemove.topLevel; level >= bottomLevel + 1; level--) {
                    succ = nodeToRemove.next[level].get(marked);
                    while (!marked[0]) {
                        nodeToRemove.next[level].attemptMark(succ, true);
                        succ = nodeToRemove.next[level].get(marked);
                    }
                }
                succ = nodeToRemove.next[bottomLevel].get(marked);
                while (true) {
                    boolean iMarkedIt = nodeToRemove.next[bottomLevel].compareAndSet(succ, succ, false, true);
                    succ = succs[bottomLevel].next[bottomLevel].get(marked);
                    if (iMarkedIt) {
                        find(item, preds, succs);
                        return true;
                    } else if (marked[0]) return false;
                }
            }
        }
    }


    private static class Node<T> {
        final T value;
        final AtomicMarkableReference<Node<T>>[] next;
        private int topLevel;

        // Конструктор для обычных узлов
        public Node(T value, int topLevel) {
            this.value = value;
            this.topLevel = topLevel;
            this.next = (AtomicMarkableReference<Node<T>>[]) new AtomicMarkableReference[MAX_LEVEL + 1];
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
        }

        // Конструктор для головы и хвоста списка
        public Node(int value) {
            this.value = (T) Integer.valueOf(value);
            this.topLevel = MAX_LEVEL;
            this.next = (AtomicMarkableReference<Node<T>>[]) new AtomicMarkableReference[MAX_LEVEL + 1];
            for (int i = 0; i < next.length; i++) {
                next[i] = new AtomicMarkableReference<>(null, false);
            }
        }
    }

    public boolean publicFind(T item) {
        Node<T>[] preds = (Node<T>[]) new Node[MAX_LEVEL + 1];
        Node<T>[] succs = (Node<T>[]) new Node[MAX_LEVEL + 1];
        return find(item, preds, succs);
    }

}