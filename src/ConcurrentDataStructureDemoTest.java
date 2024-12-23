

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcurrentDataStructureDemoTest {

    @Test
    public void testWriterThreadAddsElements() throws InterruptedException {
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        Runnable writerTask = () -> {
            for (int i = 1; i <= 5; i++) {
                sharedList.add(i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread writerThread = new Thread(writerTask, "Writer");
        writerThread.start();
        writerThread.join(); // Wait for the writer thread to complete

        // Verify that the writer thread added all elements
        assertEquals(5, sharedList.size(), "Shared list should contain 5 elements.");
        for (int i = 1; i <= 5; i++) {
            assertTrue(sharedList.contains(i), "Shared list should contain " + i);
        }
    }

    @Test
    public void testReaderThreadReadsElements() throws InterruptedException {
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 5; i++) {
            sharedList.add(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Runnable readerTask = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " reads: " + sharedList);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread readerThread = new Thread(readerTask, "Reader");
        readerThread.start();
        readerThread.join(); // Wait for the reader thread to complete

        String output = outputStream.toString();
        // Verify the reader thread read the list 5 times
        assertTrue(output.contains("Reader reads: [1, 2, 3, 4, 5]"), "Reader thread output should match the shared list.");
    }

    @Test
    public void testConcurrentReadAndWrite() throws InterruptedException {
        CopyOnWriteArrayList<Integer> sharedList = new CopyOnWriteArrayList<>();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Runnable writerTask = () -> {
            for (int i = 1; i <= 5; i++) {
                sharedList.add(i);
                System.out.println(Thread.currentThread().getName() + " added: " + i);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable readerTask = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " reads: " + sharedList);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread writerThread = new Thread(writerTask, "Writer");
        Thread readerThread = new Thread(readerTask, "Reader");

        writerThread.start();
        readerThread.start();

        writerThread.join();
        readerThread.join();

        // Verify the final state of the shared list
        assertEquals(5, sharedList.size(), "Shared list should contain 5 elements after writing.");
        for (int i = 1; i <= 5; i++) {
            assertTrue(sharedList.contains(i), "Shared list should contain " + i);
        }

        String output = outputStream.toString();
        assertTrue(output.contains("Writer added: 1") && output.contains("Reader reads:"), "Output should include concurrent writes and reads.");
    }
}
