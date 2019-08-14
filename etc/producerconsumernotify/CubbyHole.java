package threadexamples.producerconsumernotify;

public class CubbyHole {
    private int contents;
    private boolean available = false;

    public synchronized int get(Consumer c) {
        while (available == false) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        available = false;
        System.out.println("Consumer #" + c.number + " got: " + contents);
        notifyAll();
        //notify();
        return contents;
    }

    public synchronized void put(Producer p, int value) {
        while (available == true) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        contents = value;
        available = true;
        System.out.println("Producer #" + p.number + " put: " + value);
        notifyAll();
        //notify();
    }
}
