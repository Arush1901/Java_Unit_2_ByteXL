import java.io.*;
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
        Student s = new Student(101, "Arush", 92.5);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("student.ser"));
            out.writeObject(s);
            out.close();
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("student.ser"));
            Student obj = (Student) in.readObject();
            in.close();
            System.out.println("ID: " + obj.studentID);
            System.out.println("Name: " + obj.name);
            System.out.println("Grade: " + obj.grade);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
