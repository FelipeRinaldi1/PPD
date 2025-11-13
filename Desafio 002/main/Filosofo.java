package main;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Filosofo implements Runnable {

    private final int id;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Random random = new Random();

    private volatile boolean rodando = true;
    private final AtomicInteger vezesComeu = new AtomicInteger(0);

    public Filosofo(int id, Garfo garfoEsquerdo, Garfo garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
    }

    @Override
    public void run() {
        System.out.println("Filósofo " + id + " sentou à mesa.");
        try {
            while (rodando) {
                pensar();
                tentarComer();
            }
        } catch (InterruptedException e) {
            System.out.println("Filósofo " + id + " foi interrompido e levantou.");
            rodando = false;
        }
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + id + " está pensando.");
        Thread.sleep(random.nextInt(100) + 50);
    }

    private void tentarComer() throws InterruptedException {
        Garfo primeiroGarfo = (garfoEsquerdo.getId() < garfoDireito.getId()) ? garfoEsquerdo : garfoDireito;
        Garfo segundoGarfo = (garfoEsquerdo.getId() < garfoDireito.getId()) ? garfoDireito : garfoEsquerdo;

        if (primeiroGarfo.pegar(50)) {
            if (segundoGarfo.pegar(50)) {
                try {
                    comer();
                } finally {
                    segundoGarfo.soltar();
                    primeiroGarfo.soltar();
                }
            } else {
                primeiroGarfo.soltar();
            }
        }
    }

    private void comer() throws InterruptedException {
        System.out.println(">>> Filósofo " + id + " está COMENDO.");
        Thread.sleep(random.nextInt(100) + 50);
        vezesComeu.incrementAndGet();
    }

    public void parar() {
        this.rodando = false;
    }

    public int getVezesComeu() {
        return vezesComeu.get();
    }

    public int getId() {
        return id;
    }

    public Garfo getGarfoEsquerdo() {
        return garfoEsquerdo;
    }

    public Garfo getGarfoDireito() {
        return garfoDireito;
    }
}