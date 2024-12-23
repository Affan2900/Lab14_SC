// Task 1: Creating two threads, one for numbers and one for squares
public class MultithreadingDemo {
    public static void main(String[] args) {
        // Thread 1: Prints numbers from 1 to 10
        Thread numbersThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Number: " + i);
                try {
                    Thread.sleep(100); // Adding a small delay for better observation
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Prints squares of numbers from 1 to 10
        Thread squaresThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Square: " + (i * i));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        numbersThread.start();
        squaresThread.start();
    }
}
