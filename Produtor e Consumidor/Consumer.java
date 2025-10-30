public class Consumer implements Runnable {

    private final SharedList sharedList;

    public Consumer(SharedList sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                sharedList.consume();
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumidor foi interrompido");
            }
        }
        System.out.println("Consumidor finalizou seu trabalho.");
    }
}