package org.example;

public class Main {
    public static void main(String[] args) {

        MessageRepository messageRepository = new MessageRepository();
        Thread writer = new Thread(new MessageWriter(messageRepository), "Writer");
        Thread reader = new Thread(new MessageReader(messageRepository), "Reader");

        writer.setUncaughtExceptionHandler( (thread, throwable) -> {
            System.out.println("Writer had exception: " + throwable);
            if (reader.isAlive()) {
                System.out.println("Going to interrupt reader");
                reader.interrupt();
            }
        });

        reader.setUncaughtExceptionHandler( (thread, throwable) -> {
            System.out.println("Reader had exception: " + throwable);
            if (writer.isAlive()) {
                System.out.println("Going to interrupt writer");
                writer.interrupt();
            }
        });
        writer.start();
        reader.start();
    }
}