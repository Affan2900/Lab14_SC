import java.util.concurrent.atomic.AtomicInteger;

public class BankTransactionDemo {
    static class BankAccount {
        private AtomicInteger balance = new AtomicInteger(0);

        public void deposit(int amount) {
            balance.addAndGet(amount);
            System.out.println(Thread.currentThread().getName() + " deposited: " + amount + ", Balance: " + balance.get());
        }

        public void withdraw(int amount) {
            if (balance.get() >= amount) {
                balance.addAndGet(-amount);
                System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + ", Balance: " + balance.get());
            } else {
                System.out.println(Thread.currentThread().getName() + " attempted to withdraw: " + amount + ", Insufficient balance");
            }
        }

        public int getBalance() {
            return balance.get();
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Runnable clientTask = () -> {
            for (int i = 0; i < 5; i++) {
                int amount = (int) (Math.random() * 100);
                if (Math.random() > 0.5) {
                    account.deposit(amount);
                } else {
                    account.withdraw(amount);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread client1 = new Thread(clientTask, "Client-1");
        Thread client2 = new Thread(clientTask, "Client-2");
        Thread client3 = new Thread(clientTask, "Client-3");

        client1.start();
        client2.start();
        client3.start();

        try {
            client1.join();
            client2.join();
            client3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final Balance: " + account.getBalance());
    }
}
