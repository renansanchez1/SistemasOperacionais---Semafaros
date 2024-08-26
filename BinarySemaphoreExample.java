import java.util.concurrent.Semaphore;

public class BinarySemaphoreExample {
    // Criando um semáforo binário com valor inicial 1
    private static Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        // Criando duas threads que tentarão acessar a seção crítica
        Thread t1 = new Thread(new CriticalSection(), "Thread 1");
        Thread t2 = new Thread(new CriticalSection(), "Thread 2");

        t1.start();
        t2.start();
    }

    static class CriticalSection implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " está tentando acessar a seção crítica.");

                // P (wait) - Tentando adquirir o semáforo
                mutex.acquire();

                System.out.println(Thread.currentThread().getName() + " entrou na seção crítica.");

                // Simulando alguma operação na seção crítica
                Thread.sleep(2000);

                System.out.println(Thread.currentThread().getName() + " saindo da seção crítica.");

                // V (signal) - Liberando o semáforo
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
