import java.util.concurrent.Semaphore;

public class ReadersWritersProblem {
    // Semáforos para controle de acesso
    private static Semaphore mutex = new Semaphore(1); // Protege o contador de leitores
    private static Semaphore wrt = new Semaphore(1);   // Controla o acesso à seção crítica (escrita)
    private static int readCount = 0;  // Contador de leitores

    public static void main(String[] args) {
        // Criando múltiplas threads de leitores e escritores
        Thread reader1 = new Thread(new Reader(), "Reader 1");
        Thread reader2 = new Thread(new Reader(), "Reader 2");
        Thread writer1 = new Thread(new Writer(), "Writer 1");
        Thread writer2 = new Thread(new Writer(), "Writer 2");

        reader1.start();
        reader2.start();
        writer1.start();
        writer2.start();
    }

    static class Reader implements Runnable {
        @Override
        public void run() {
            try {
                // Adquirir o mutex para modificar o contador de leitores
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    // Primeiro leitor bloqueia escritores
                    wrt.acquire();
                }
                mutex.release();

                // Seção Crítica de Leitura
                System.out.println(Thread.currentThread().getName() + " está lendo.");
                Thread.sleep(1500); // Simulando tempo de leitura
                System.out.println(Thread.currentThread().getName() + " terminou de ler.");

                // Seção de saída de leitores
                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    // Último leitor libera escritores
                    wrt.release();
                }
                mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            try {
                // Adquirir o semáforo de escrita
                wrt.acquire();

                // Seção Crítica de Escrita
                System.out.println(Thread.currentThread().getName() + " está escrevendo.");
                Thread.sleep(2000); // Simulando tempo de escrita
                System.out.println(Thread.currentThread().getName() + " terminou de escrever.");

                // Liberar o semáforo de escrita
                wrt.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
