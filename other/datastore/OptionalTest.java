package other.datastore;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public class OptionalTest {
    record Car(String make, String model, String color, Year year) {}
    record Person(String firstName, String lastName, Car car) {}

    public static void main(String[] args) {
        Person p1 = new Person("Tom", "Thumb", new Car("Tesla", "X", "Red", Year.of(2018)));
        Person p2 = new Person("Jerry", "Thumb", new Car("Tesla", "Y", "Red", Year.of(2018)));

        Optional<Person> p11 = Optional.ofNullable(p2);
        p11.stream()
                .map(Person::firstName)
                .forEach(System.out::println);

        List<Optional<Person>> people22 = List.of(Optional.of(p1), Optional.of(p2));
        people22.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Person::firstName)
                .forEach(System.out::println);
    }
}
