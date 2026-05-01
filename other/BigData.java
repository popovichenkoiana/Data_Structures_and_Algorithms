package other;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.*;

public class BigData {
    record Person(String firstName, String lastName, long salary, String state) {}
    record Person2(String firstName, String lastName, BigDecimal salary, String state, char gender) {}

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            Long result = Files.lines(Path.of("/Users/ianapopovichenko/projects/Data_Structures_and_Algorithms/other/Hr5m.csv")).parallel()
                    .skip(1)
                    .limit(10)
                    .map(s -> s.split(","))
                    .map(arr -> arr[25])
                    .collect(summingLong(sal -> Long.parseLong(sal)));
            long endTime = System.currentTimeMillis();
            System.out.printf("$%,d.00%n", result);

            System.out.println(endTime - startTime);

            //Domain Model with Stream
            Files.lines(Path.of("/Users/ianapopovichenko/projects/Data_Structures_and_Algorithms/other/Hr5m.csv"))
                    .skip(1)
                    .limit(10)
                    .map(s -> s.split(","))
                    .map(a -> new Person(a[2], a[4], parseLong(a[25]), a[32]))
                    .collect(groupingBy(Person::state, TreeMap::new,
                            collectingAndThen(summingLong(Person::salary),  NumberFormat.getCurrencyInstance()::format)))
                    .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary)); //CA -> €262.585,00

            //Nested groupings and reducing with collect
            Files.lines(Path.of("/Users/ianapopovichenko/projects/Data_Structures_and_Algorithms/other/Hr5m.csv"))
                    .skip(1)
                    .limit(10)
                    .map(s -> s.split(","))
                    .map(a -> new Person2(a[2], a[4], new BigDecimal(a[25]), a[32], a[5].strip().charAt(0)))
                    .collect(
                            groupingBy(Person2::state, TreeMap::new,
                                    groupingBy(Person2::gender,
                                            collectingAndThen(
                                                    reducing(BigDecimal.ZERO,Person2::salary,(a,b)->a.add(b)),
                                                    NumberFormat.getCurrencyInstance()::format)
                                    )
                            ))
                   .forEach((state, salary) -> System.out.printf("%s -> %s%n", state, salary)); //CA -> {F=€103.839,00, M=€158.746,00}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
