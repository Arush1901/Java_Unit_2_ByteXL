import java.util.*;
import java.util.stream.*;
class Student {
    String name;
    double marks;
    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}
public class StudentFilterSort {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Arush", 92));
        list.add(new Student("Gojo", 68));
        list.add(new Student("Mohito", 81));
        list.add(new Student("Ayano", 76));

        System.out.println("Students scoring above 75%, sorted by marks:");
        list.stream()
            .filter(s -> s.marks > 75)
            .sorted((a, b) -> Double.compare(a.marks, b.marks))
            .map(s -> s.name)
            .forEach(System.out::println);
    }
}
