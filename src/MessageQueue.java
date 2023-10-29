import java.util.Queue;
import java.util.LinkedList;

public class MessageQueue {
    private final int maxCount;
    private int messageCount = 0;
    private Queue<Object> buffer = new LinkedList<>();

    public MessageQueue(int maxCount) {
        this.maxCount = maxCount;
    }

    public synchronized void send(Object message) {
        while (messageCount == maxCount) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        buffer.offer(message);
        messageCount++;
        if (messageCount == 1) {
            notify();
        }
    }

    public synchronized Object receive() {
        while (messageCount == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Object message = buffer.poll();
        messageCount--;
        if (messageCount == maxCount - 1) {
            notify();
        }
        return message;
    }
}
