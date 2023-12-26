public class LockFreeSkipListUsage {
    public static void main(String[] args) {
        // Создание экземпляра LockFreeSkipList для Integer
        LockFreeSkipList<Integer> skipList = new LockFreeSkipList<>();

        // Добавление элементов
        skipList.add(10);
        skipList.add(20);

        // Проверка наличия элемента
        System.out.println("Contains 10: " + skipList.contains(10)); // true

        // Поиск элемента (используя внутренний метод find через вспомогательный метод)
        System.out.println("Find 20: " + skipList.publicFind(20)); // true

        // Удаление элемента
        System.out.println("Removed 10: " + skipList.remove(10)); // true

        // Проверка наличия элемента после удаления
        System.out.println("Contains 10 after removal: " + skipList.contains(10)); // false
    }
}

