

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadSynchronizationDemoTest {

    @Test
    public void testCounterIncrementBySingleThread() throws InterruptedException {
        // Reset the counter
        ThreadSynchronizationDemo.counter = 0;

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                ThreadSynchronizationDemo.incrementCounter();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        thread.join(); // Wait for the thread to complete

        // Verify the counter value is 100
        assertEquals(100, ThreadSynchronizationDemo.counter, "Counter should be incremented to 100 by a single thread.");
    }

    @Test
    public void testCounterIncrementByMultipleThreads() throws InterruptedException {
        // Reset the counter
        ThreadSynchronizationDemo.counter = 0;

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                ThreadSynchronizationDemo.incrementCounter();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        Thread thread3 = new Thread(task);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join(); // Wait for all threads to complete

        // Verify the counter value is 300 (100 increments per thread)
        assertEquals(300, ThreadSynchronizationDemo.counter, "Counter should be incremented to 300 by three threads.");
    }

    @Test
    public void testCounterIncrementWithNoSynchronization() throws InterruptedException {
        // This test demonstrates the need for synchronization (should fail if incrementCounter is not synchronized)
        ThreadSynchronizationDemo.counter = 0;

        // Override incrementCounter to simulate unsynchronized behavior
        Runnable unsynchronizedTask = () -> {
            for (int i = 0; i < 100; i++) {
                ThreadSynchronizationDemo.counter++; // Direct increment without synchronization
            }
        };

        Thread thread1 = new Thread(unsynchronizedTask);
        Thread thread2 = new Thread(unsynchronizedTask);
        Thread thread3 = new Thread(unsynchronizedTask);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join(); // Wait for all threads to complete

        // Verify the counter value is likely less than 300 due to race conditions
        assertTrue(ThreadSynchronizationDemo.counter < 300, "Without synchronization, the counter value should be less than 300 due to race conditions.");
    }
}
