package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Jantar {

    private final int N;
    private final List<Filosofo> filosofos;
    private final List<Garfo> garfos;
    private ExecutorService threadPool;

    public Jantar(int numeroFilosofos) {
        if (numeroFilosofos < 2) {
            throw new IllegalArgumentException("O jantar precisa de pelo menos 2 filósofos.");
        }
        this.N = numeroFilosofos;
        this.garfos = new ArrayList<>(N);
        this.filosofos = new ArrayList<>(N);
    }

    public void iniciar() {
        for (int i = 0; i < N; i++) {
            garfos.add(new Garfo(i));
        }

        for (int i = 0; i < N; i++) {
            Garfo garfoEsquerdo = garfos.get(i);
            Garfo garfoDireito = garfos.get((i + 1) % N);

            filosofos.add(new Filosofo(i, garfoEsquerdo, garfoDireito));
        }

        this.threadPool = Executors.newFixedThreadPool(N);
    }

    public void executarPor(long duracaoMs) throws InterruptedException {
        if (threadPool == null) {
            throw new IllegalStateException("O método iniciar() deve ser chamado antes de executar.");
        }

        for (Filosofo f : filosofos) {
            threadPool.submit(f);
        }

        Thread.sleep(duracaoMs);

        parar();
    }

    public void parar() {
        if (threadPool != null) {
            System.out.println("Solicitando parada do jantar...");

            for (Filosofo f : filosofos) {
                f.parar();
            }

            threadPool.shutdownNow();
            try {
                if (!threadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.err.println("Pool de threads não encerrou graciosamente.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public List<Filosofo> getFilosofos() {
        return Collections.unmodifiableList(filosofos);
    }

    public List<Garfo> getGarfos() {
        return Collections.unmodifiableList(garfos);
    }
}