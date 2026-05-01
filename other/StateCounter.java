package other;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateCounter {
    public static void main(String[] args) throws IOException {
        Map<String, Long> stateCounts = new HashMap<>();

        // Reading the CSV file, skipping the header, and splitting by comma
        Files.lines(Path.of("other/Hr5m.csv"))
                .skip(1)
                .map(l -> l.split(","))
                .forEach(a -> stateCounts.compute(a[32], (k, v) -> v == null ? 1L : v + 1));

        System.out.println(stateCounts);

        // Manual check/update for a specific state ("CA")
        Long curPopulation = stateCounts.get("CA");
        if (curPopulation == null) {
            stateCounts.put("CA", 1L);
        } else {
            stateCounts.put("CA", ++curPopulation);
        }


        Map<String, Long> stateCounts2 = new HashMap<>();

        Files.lines(Path.of("other/Hr5m.csv"))
                .skip(1)
                .map(l -> l.split(","))
                // Метод merge: если ключа нет, ставит 1L. Если есть — суммирует (x + y)
                .forEach(a -> stateCounts2.merge(a[32], 1L, (x, y) -> x + y));

        System.out.println(stateCounts);

        List<String> animals = new ArrayList<>(List.of("cat", "dog"));
        animals.removeIf(s -> s.startsWith("c"));
    }
}
