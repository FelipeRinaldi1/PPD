public class main {

    public static void main(String[] args) {
        SharedList sharedList = new SharedList(5);

        Runnable producerTask = new Producer(sharedList);
        Runnable consumerTask = new Consumer(sharedList);

        Thread t1 = new Thread(producerTask, "T1");
        Thread t2 = new Thread(consumerTask, "T2");

        System.out.println("Iniciando a execução paralela do Produtor e Consumidor...");

        t1.start();
        t2.start();
    }
}