import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadHierarchyPrinter {
    public static void printThreadHierarchy(ThreadGroup group) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        int maxDepth = 10; // Максимальная глубина иерархии потоков

        ThreadGroup parentGroup = group.getParent();
        System.out.println("Иерархия потоков в группе: " + group.getName());

        while (parentGroup != null && maxDepth > 0) {
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), maxDepth);
            for (ThreadInfo threadInfo : threadInfos) {
                if (threadInfo != null && threadInfo.getThreadName() != null && threadInfo.getThreadName().startsWith(parentGroup.getName())) {
                    System.out.println("Поток: " + threadInfo.getThreadName());
                }
            }
            parentGroup = parentGroup.getParent();
            maxDepth--;
        }
    }

    public static void main(String[] args) {
        ThreadGroup group1 = new ThreadGroup("Группа 1");
        ThreadGroup group2 = new ThreadGroup("Группа 2");

        Thread thread1 = new Thread(group1, () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Поток 1: Шаг " + i);
                try {
                    Thread.sleep(1000); // Приостановка выполнения потока на 1 секунду
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(group2, () -> {
            for (int i = 0; i < 3; i++) {
                System.out.println("Поток 2: Шаг " + i);
                try {
                    Thread.sleep(1500); // Приостановка выполнения потока на 1.5 секунды
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        printThreadHierarchy(group1);
        printThreadHierarchy(group2);
    }
}
