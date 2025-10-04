import java.io.*;
import java.util.*;
class Student implements Serializable {
    int studentID;
    String name;
    double grade;
    Student(int studentID, String name, double grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }
}
public class StudentSerialization {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Grade: ");
        double grade = sc.nextDouble();
        Student s = new Student(id, name, grade);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.ser"));
            out.writeObject(s);
            out.close();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.ser"));
            Student obj = (Student) in.readObject();
            in.close();
            System.out.println("Deserialized Student Data:");
            System.out.println("ID: " + obj.studentID);
            System.out.println("Name: " + obj.name);
            System.out.println("Grade: " + obj.grade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
