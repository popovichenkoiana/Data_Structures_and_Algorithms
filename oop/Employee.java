package oop;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Employee {
    protected final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    private final NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
    private static String peopleRegex = "(?<lastName>\\w+),\\s*(?<firstName>\\w+),\\s*(?<dob>\\d{1,2}/\\d{1,2}/\\d{4}),\\s*(?<role>\\w+)(?:,\\s*\\{(?<details>.*)\\})?";
    protected static Pattern peoplePat = Pattern.compile(peopleRegex);

    protected String lastName;
    protected String firstName;
    protected LocalDate dob;
    protected final Matcher peopleMat;

    public Employee(String personText) {
        peopleMat = peoplePat.matcher(personText);
        if (peopleMat.find()) {
            this.lastName = peopleMat.group("lastName");
            this.firstName = peopleMat.group("firstName");
            this.dob = LocalDate.from(dtFormatter.parse(peopleMat.group("dob")));
        }
    }
    // Статический фабричный метод для создания объектов
    // Flinstone5, Fred5, 1/1/1900, Programmer, {locpd=5,yoe=10,iq=100}
    public static final IEmployee createEmployee(String employeeText) {
        Matcher peopleMat = Employee.peoplePat.matcher(employeeText);
        if (peopleMat.find()) {
            return switch (peopleMat.group("role")) {
                case "Programmer" -> new Programmer(employeeText);
                case "Manager" -> new Manager(employeeText);
                case "Analyst" -> new Analyst(employeeText);
                default -> new DummyEmployee();
            };
        } else{
            return  new DummyEmployee();
        }
    }

    public abstract int getSalary();

    public double getBonus() {
        return getSalary() * 1.10;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return String.format("%s, %s: %s",
                lastName,
                firstName,
                moneyFormat.format(getSalary())
        );
    }
    // Внутренний приватный класс-заглушка
    private static final class DummyEmployee extends Employee implements IEmployee {

        public DummyEmployee() {
            super(""); // Вызов конструктора родителя, если он есть
            this.lastName = "Unknown";
            this.firstName = "Unknown";
        }

        @Override
        public int getSalary() {
            return 0;
        }
    }

}
