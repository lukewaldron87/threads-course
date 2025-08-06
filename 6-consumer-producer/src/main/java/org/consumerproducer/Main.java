package org.consumerproducer;

public class Main {
    public static void main(String[] args) {

        MessageRepository messageRepository = new MessageRepository();
        Thread writerThread = new Thread(new MessageWriter(messageRepository));
        Thread readerThread = new Thread(new MessageReader(messageRepository));
        writerThread.start();
        readerThread.start();
    }
}