package other.datastore;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class TimeTest {
    static void main(String[] args) {
        LocalDate now = LocalDate.now();
        System.out.println(now);

        LocalDate newYears = LocalDate.of(2020,1,1);
        System.out.println(newYears.getDayOfWeek());
        List<LocalDate> leapYears =LocalDate.now().datesUntil(LocalDate.now().plusYears(10),Period.ofYears(1))
                .filter(LocalDate::isLeapYear)
                .collect(Collectors.toList());
        System.out.println(leapYears);

        System.out.println("Example 2");
        LocalDate d1 = LocalDate.of(2000, 1, 1);
        LocalDate d2 = LocalDate.of(2002, 6, 1);

        LocalTime lt1 = LocalTime.of(10, 30);
        LocalTime lt2 = LocalTime.of(13, 57, 39);
        System.out.println(lt1.equals(lt2));

        LocalDateTime ldt1 = LocalDateTime.of(d1, lt1);
        LocalDateTime ldt2 = LocalDateTime.of(d2, lt2);
        System.out.println(ldt1.equals(ldt2));

        Period diff = Period.between(d1, d2);
        System.out.printf("%d years, %d months, %d days%n",
                diff.getYears(), diff.getMonths(), diff.getDays());

        LocalDateTime xmas = LocalDateTime.of(2021, 12, 25, 20, 00);
        // California - GMT-08
        ZonedDateTime zxmas = ZonedDateTime.of(xmas, ZoneId.of("-8"));
        System.out.println(zxmas.withZoneSameInstant(ZoneId.of("+0")));

        System.out.println(d1.with(TemporalAdjusters.lastDayOfYear()).getDayOfWeek());
    }
}
