public class MessageQueueConnector {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(3);

        // Create the producer thread
        Thread producerThread = new Thread(() -> {
//            System.out.println("Producer is sending messages:");
            System.out.println("Sending message: ('add', 4)");
            messageQueue.send(new Message("add", 4));
            System.out.println("Sending message: ('multiply', 1)");
            messageQueue.send(new Message("multiply", 1));
            System.out.println("Sending message: ('multiply', 8)");
            messageQueue.send(new Message("multiply", 8));
            System.out.println("Sending message: ('add', 2)");
            messageQueue.send(new Message("add", 2));
            System.out.println("Sending message: ('add', 3)");
            messageQueue.send(new Message("add", 3));
            System.out.println("Sending message: ('add', 99)");
            messageQueue.send(new Message("add", 99));
            System.out.println("Sending message: ('multiply', 53)");
            messageQueue.send(new Message("multiply", 53));
            System.out.println("Sending message: ('end', 0)");
            messageQueue.send(new Message("end", 0)); // Sending the ending message
        });

        // Create the consumer thread
        Thread consumerThread = new Thread(() -> {
            while (true) {
                Message message = (Message) messageQueue.receive();
                if (message.getOperation().equals("end") && message.getValue() == 0) {
                    break; // Terminate the program on the ending message
                }

                if (message.getOperation().equals("add")) {
                    AddCalculation.add(message.getValue());
                } else if (message.getOperation().equals("multiply")) {
                    MultiplyCalculation.multiply(message.getValue());
                }
            }
        });

        // Start the producer and consumer threads
        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
