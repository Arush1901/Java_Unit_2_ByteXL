import java.util.*;
class Employee {
    String name;
    int age;
    double salary;
    Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}
public class EmployeeSorting {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("Arush", 22, 62000));
        list.add(new Employee("Gojo", 28, 48000));
        list.add(new Employee("Ayanokoji", 25, 55000));

        System.out.println("Sort by Name:");
        list.sort((a, b) -> a.name.compareTo(b.name));
        list.forEach(e -> System.out.println(e.name + " " + e.age + " " + e.salary));

        System.out.println("\nSort by Age:");
        list.sort((a, b) -> a.age - b.age);
        list.forEach(e -> System.out.println(e.name + " " + e.age + " " + e.salary));

        System.out.println("\nSort by Salary (Descending):");
        list.sort((a, b) -> Double.compare(b.salary, a.salary));
        list.forEach(e -> System.out.println(e.name + " " + e.age + " " + e.salary));
    }
}
