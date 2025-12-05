package org.example;

import java.util.concurrent.ThreadFactory;

public class ColorThreadFactory implements ThreadFactory {

    String threadName;

    public ColorThreadFactory(ThreadColor threadColor) {
        this.threadName = threadColor.name();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(threadName);
        return thread;
    }
}
