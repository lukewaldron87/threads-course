package org.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {


        Object[] persons = Stream.generate(Person::new)
                .limit(10)
                .sorted(Comparator.comparing(Person::lastName))
                .toArray();

        for (Object person: persons){
            System.out.println(person);
        }

        System.out.println("------------------------------------");

        Arrays.stream(persons)
                .limit(10)
                .parallel()
//                .sorted(Comparator.comparing(Person::lastName))
                .forEach(System.out::println);

        System.out.println("------------------------------------");

        int sum = IntStream.range(1, 101)
                .parallel()
                .reduce(0, Integer::sum);

        System.out.println("The sum of the number is: " + sum);

        System.out.println("------------------------------------");

        String humptyDumpty = """
                Humpty Dumpty sat on a wall.
                Humpty Dumpty had a great fall.
                All the king's horses and all the king's men
                couldn't put Humpty together again.
                """;

        List<String> words = new Scanner(humptyDumpty).tokens().toList();
        words.forEach(System.out::println);

        System.out.println("------------------------------------");

        String backTogetherAgain = words.parallelStream()
                        .collect(Collectors.joining(" "));

        System.out.println(backTogetherAgain);

        System.out.println("------------------------------------");

        Map<String, Long> lastNameCounts = Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .collect(Collectors.groupingByConcurrent(
                        Person::lastName,
                        Collectors.counting()));

        lastNameCounts.entrySet().forEach(System.out::println);

        long total = 0;

        for(long count: lastNameCounts.values()){
            total += count;
        }
        System.out.println("Total = " + total);
        System.out.println(lastNameCounts.getClass().getName());

        System.out.println("------------------------------------");

//        Map<String, Long> lastCounts = new ConcurrentSkipListMap<>();
        Map<String, Long> lastCounts = Collections.synchronizedMap(new TreeMap<String, Long>());
        Stream.generate(Person::new)
                .limit(10000)
                .parallel()
                .forEach(person -> lastCounts.merge(person.lastName(), 1L, Long::sum));

        System.out.println(lastCounts);

        total = 0;

        for(long count: lastCounts.values()){
            total += count;
        }
        System.out.println("Total = " + total);
        System.out.println(lastCounts.getClass().getName());


    }
}