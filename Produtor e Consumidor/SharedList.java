import java.util.LinkedList;
import java.util.Queue;

public class SharedList {

    private final Queue<Integer> list = new LinkedList<>();
    private final int capacity;

    public SharedList(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int value) throws InterruptedException {
        while (list.size() == capacity) {
            System.out.println("Lista cheia. Produtor (T1) aguardando...");
            wait();
        }

        list.add(value);
        System.out.println("Produtor (T1) produziu: " + value + " [Tamanho da lista: " + list.size() + "]");
        
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (list.isEmpty()) {
            System.out.println("Lista vazia. Consumidor (T2) aguardando...");
            wait();
        }

        int consumedValue = list.poll();
        System.out.println("Consumidor (T2) consumiu: " + consumedValue + " [Tamanho da lista: " + list.size() + "]");
        
        notifyAll();

        return consumedValue;
    }
}