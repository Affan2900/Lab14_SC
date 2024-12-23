import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentDataStructureDemo {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        Runnable writerTask = () -> {
            for (int i = 1; i <= 5; i++) {
                sharedList.add(i);
                System.out.println(Thread.currentThread().getName() + " added: " + i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable readerTask = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " reads: " + sharedList);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread writerThread = new Thread(writerTask, "Writer");
        Thread readerThread = new Thread(readerTask, "Reader");

        writerThread.start();
        readerThread.start();

        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final List: " + sharedList);
    }
}
