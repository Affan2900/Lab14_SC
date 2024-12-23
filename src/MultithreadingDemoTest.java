

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultithreadingDemoTest {

    @Test
    public void testNumbersThreadOutput() throws InterruptedException {
        // Redirect system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Thread numbersThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Number: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        numbersThread.start();
        numbersThread.join(); // Wait for the thread to finish

        String output = outputStream.toString();
        // Verify the output contains all the expected numbers
        for (int i = 1; i <= 10; i++) {
            assertTrue(output.contains("Number: " + i), "Output should contain 'Number: " + i + "'");
        }
    }

    @Test
    public void testSquaresThreadOutput() throws InterruptedException {
        // Redirect system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

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

        squaresThread.start();
        squaresThread.join(); // Wait for the thread to finish

        String output = outputStream.toString();
        // Verify the output contains all the expected squares
        for (int i = 1; i <= 10; i++) {
            assertTrue(output.contains("Square: " + (i * i)), "Output should contain 'Square: " + (i * i) + "'");
        }
    }

    @Test
    public void testConcurrentExecution() throws InterruptedException {
        // Redirect system output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Thread numbersThread = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Number: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

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

        numbersThread.start();
        squaresThread.start();

        numbersThread.join();
        squaresThread.join();

        String output = outputStream.toString();

        // Check if both "Number" and "Square" outputs exist in the captured output
        assertTrue(output.contains("Number: 1") && output.contains("Square: 1"), "Output should contain both numbers and squares");
        assertTrue(output.contains("Number: 10") && output.contains("Square: 100"), "Output should contain both numbers and squares till 10");
    }
}
