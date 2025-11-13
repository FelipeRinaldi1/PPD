package main;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Garfo {

    private final int id;
    private final ReentrantLock lock = new ReentrantLock();

    public Garfo(int id) {
        this.id = id;
    }

    public boolean pegar(long timeoutMs) throws InterruptedException {
        return lock.tryLock(timeoutMs, TimeUnit.MILLISECONDS);
    }

    public void soltar() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Garfo-" + id;
    }
}