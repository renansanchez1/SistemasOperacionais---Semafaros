import java.util.concurrent.Semaphore;

public class CountingSemaphoreExample {
    // Criando um semáforo contador com 3 permissões
    private static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) {
        // Criando múltiplas threads que tentarão acessar a seção crítica
        for (int i = 1; i <= 5; i++) {
            Thread t = new Thread(new CriticalSection(), "Thread " + i);
            t.start();
        }
    }

    static class CriticalSection implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " está tentando acessar a seção crítica.");

                // P (wait) - Tentando adquirir o semáforo
                semaphore.acquire();

                System.out.println(Thread.currentThread().getName() + " entrou na seção crítica.");

                // Simulando alguma operação na seção crítica
                Thread.sleep(2000);

                System.out.println(Thread.currentThread().getName() + " saindo da seção crítica.");

                // V (signal) - Liberando o semáforo
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
