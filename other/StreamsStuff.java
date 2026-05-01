package other;

import oop.Employee;
import oop.Programmer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.function.Predicate.not;

public class StreamsStuff {
    static void main(String[] args) {

        try {
            Files.lines(Path.of("other/employees.txt"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String peopleText = """
                Flinstone, Fred, 1/1/1900, Programmer, {locpd=2000,yoe=10,iq=140}
                Flinstone2, Fred2, 1/1/1900, Programmer, {locpd=1300,yoe=14,iq=100}
                Flinstone3, Fred3, 1/1/1900, Programmer, {locpd=2300,yoe=8,iq=105}
                Flinstone4, Fred4, 1/1/1900, Programmer, {locpd=1630,yoe=3,iq=115}
                Flinstone5, Fred5, 1/1/1900, Programmer, {locpd=5,yoe=10,iq=100}
                Rubble, Barney, 2/2/1905, Manager, {orgSize=300,dr=10}
                Rubble2, Barney2, 2/2/1905, Manager, {orgSize=100,dr=4}
                Rubble3, Barney3, 2/2/1905, Manager, {orgSize=200,dr=2}
                Rubble4, Barney4, 2/2/1905, Manager, {orgSize=500,dr=8}
                Rubble5, Barney5, 2/2/1905, Manager, {orgSize=175,dr=20}
                Flinstone, Wilma, 3/3/1910, Analyst, {projectCount=3}
                Flinstone2, Wilma2, 3/3/1910, Analyst, {projectCount=4}
                Flinstone3, Wilma3, 3/3/1910, Analyst, {projectCount=5}
                Flinstone4, Wilma4, 3/3/1910, Analyst, {projectCount=6}
                Flinstone5, Wilma5, 3/3/1910, Analyst, {projectCount=9}
                """;
        peopleText.lines()
                .map(Employee::createEmployee)
                .forEach(System.out::println);

        Collection<String> nums = Set.of("one", "two", "three", "four");
        nums
                .stream()
                .map(String::hashCode)
                .map(Integer::toHexString)
                .forEach(System.out::println);


        record Car(String make, String model) {
        }
        Stream.of(new Car("Ford", "Bronco"), new Car("Tesla", "X"))
                .filter(c -> "Tesla".equals(c.make))
                .forEach(System.out::println);

        IntStream.rangeClosed(1, 5)
                .mapToObj(String::valueOf)
                .map(s -> s.concat("-"))
                .forEach(System.out::println);

        String[] names = {"terry", "sam", "jake"};
        Arrays.stream(names)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        int sum = peopleText
                .lines()
                .filter(not(s ->s.contains("Programmer")))
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(not(e -> e instanceof Programmer))
                .filter(e -> e.getSalary() > 5000)
                .distinct() // don't allow duplicates or collect(Collectors.toSet()).stream()-> output set. Stream can be called from any implemtation of collection.
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .mapToInt(StreamsStuff::showEmpAndGetSalary)
                .sum();
        System.out.println(sum);

        // Predicate definitions with map reduce
        Predicate<Employee> dummyEmpSelector = employee -> "N/A".equals(employee.getLastName());
        Predicate<Employee> overFiveKSelector = e -> e.getSalary() > 5000;
        Predicate<Employee> noDummiesAndOverFiveK = dummyEmpSelector.negate().and(overFiveKSelector);

        long result = peopleText
                .lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(noDummiesAndOverFiveK)
                .collect(Collectors.toSet()).stream()
                .sorted(comparing(Employee::getLastName)
                        .thenComparing(Employee::getFirstName)
                        .thenComparing(Employee::getSalary))
                .mapToInt(StreamsStuff::showEmpAndGetSalary)
                .reduce(0,(a,b) -> a > b ? a : b);
        System.out.println(result);

        peopleText
                .lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee)e)
                .map(Employee::getFirstName)
                .map(firstName ->firstName.split(""))//["F""r""e""d"]
                .flatMap(Arrays::stream)
                .distinct() // remove duplicate
                .forEach(System.out::print);

        System.out.println("    ");

        boolean allOver2K = peopleText
                .lines()
                .map(Employee::createEmployee)
                .map(e->(Employee) e)
                .filter(dummyEmpSelector.negate())
                .allMatch(e ->e.getSalary()>2000);
        System.out.println(allOver2K);

        Predicate<Employee> dummySelector = e -> e.getLastName().equals("N/A");

        Optional<Employee> optionEmp = peopleText
                .lines()
                .map(Employee::createEmployee)
                .map(e -> (Employee) e)
                .filter(e -> e.getSalary() < 0)
                .findFirst();

        System.out.println(optionEmp
                .map(Employee::getFirstName)
                .orElse("Nobody"));

        Optional<String> output = Stream.of("tom", "jerry", "mary", "sam")
                .reduce((a, b) -> a.toUpperCase().concat("_").concat(b.toUpperCase()));

        System.out.println(output.orElse("")); //TOM_JERRY_MARY_SAM


    }

    public static int showEmpAndGetSalary(Employee e) {
        System.out.println(e);
        return e.getSalary();
    }
}