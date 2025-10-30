public class Producer implements Runnable {

    private final SharedList sharedList;

    public Producer(SharedList sharedList) {
        this.sharedList = sharedList;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            try {
                sharedList.produce(i);
                Thread.sleep((long) (Math.random() * 500));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Produtor foi interrompido");
            }
        }
        System.out.println("Produtor finalizou seu trabalho.");
    }
}