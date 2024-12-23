

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTransactionDemoTest {
    static class BankAccount {
        private AtomicInteger balance = new AtomicInteger(0);

        public void deposit(int amount) {
            balance.addAndGet(amount);
        }

        public void withdraw(int amount) {
            if (balance.get() >= amount) {
                balance.addAndGet(-amount);
            }
        }

        public int getBalance() {
            return balance.get();
        }
    }

    @Test
    public void testBankAccountThreadSafety() throws InterruptedException {
        BankAccount account = new BankAccount();

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                account.deposit(10);
                account.withdraw(5);
            }
        };

        Thread client1 = new Thread(task);
        Thread client2 = new Thread(task);
        Thread client3 = new Thread(task);

        client1.start();
        client2.start();
        client3.start();

        client1.join();
        client2.join();
        client3.join();

        // Each thread deposits 1000 and withdraws 500, so the final balance should be (1000 - 500) * 3 = 1500.
        assertEquals(1500, account.getBalance(), "Final balance should be exactly 1500.");
    }
}
