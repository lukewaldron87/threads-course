package org.example;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

public class MainLists {

    public static void main(String[] args) {

        ConcurrentSkipListMap<String, Long> threadMap = new ConcurrentSkipListMap<String, Long>();

        Person[] persons = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .peek( person -> {
                    String threadName = Thread.currentThread().getName()
                            .replace("ForkJoinPool.commonPool-worker-",
                                    "thread_");
                    threadMap.merge(threadName, 1L, Long::sum);
                })
                .toArray(Person[]::new);

        System.out.println("Total = " + persons.length);
        System.out.println(threadMap);
        long total = 0;
        for( long count: threadMap.values() ) {
            total += count;
        }
        System.out.println("ThreadCounts = " + total);

    }
}
