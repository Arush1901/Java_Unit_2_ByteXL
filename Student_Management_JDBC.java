import java.sql.*;
import java.util.*;

// Model
class Student {
    int studentID;
    String name;
    String department;
    double marks;
    Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }
}

// Controller
class StudentController {
    static final String URL = "jdbc:mysql://localhost:3306/mydb";
    static final String USER = "root";
    static final String PASS = "password";
    Connection con;

    StudentController() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            con.setAutoCommit(false);
        } catch (Exception e) { e.printStackTrace(); }
    }

    void addStudent(Student s) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO Student(StudentID, Name, Department, Marks) VALUES(?,?,?,?)");
            ps.setInt(1, s.studentID);
            ps.setString(2, s.name);
            ps.setString(3, s.department);
            ps.setDouble(4, s.marks);
            ps.executeUpdate();
            con.commit();
            ps.close();
            System.out.println("Student added.");
        } catch (Exception e) { try { con.rollback(); } catch(SQLException ex){ex.printStackTrace();} e.printStackTrace(); }
    }

    void displayStudents() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Student");
            System.out.println("Student Records:");
            while(rs.next()) {
                System.out.println("ID: "+rs.getInt("StudentID")+", Name: "+rs.getString("Name")+", Dept: "+rs.getString("Department")+", Marks: "+rs.getDouble("Marks"));
            }
            rs.close();
            st.close();
        } catch(Exception e) { e.printStackTrace(); }
    }

    void updateStudent(Student s) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Student SET Name=?, Department=?, Marks=? WHERE StudentID=?");
            ps.setString(1, s.name);
            ps.setString(2, s.department);
            ps.setDouble(3, s.marks);
            ps.setInt(4, s.studentID);
            int rows = ps.executeUpdate();
            if(rows>0) System.out.println("Student updated.");
            else System.out.println("StudentID not found.");
            con.commit();
            ps.close();
        } catch(Exception e) { try { con.rollback(); } catch(SQLException ex){ex.printStackTrace();} e.printStackTrace(); }
    }

    void deleteStudent(int id) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Student WHERE StudentID=?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if(rows>0) System.out.println("Student deleted.");
            else System.out.println("StudentID not found.");
            con.commit();
            ps.close();
        } catch(Exception e) { try { con.rollback(); } catch(SQLException ex){ex.printStackTrace();} e.printStackTrace(); }
    }
}

// View
public class StudentManagementMVC {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentController controller = new StudentController();
        while(true) {
            System.out.println("\n1. Add Student\n2. Display Students\n3. Update Student\n4. Delete Student\n5. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if(ch==1) {
                System.out.print("Enter StudentID: "); int id = sc.nextInt(); sc.nextLine();
                System.out.print("Enter Name: "); String name = sc.nextLine();
                System.out.print("Enter Department: "); String dept = sc.nextLine();
                System.out.print("Enter Marks: "); double marks = sc.nextDouble();
                controller.addStudent(new Student(id, name, dept, marks));
            } else if(ch==2) controller.displayStudents();
            else if(ch==3) {
                System.out.print("Enter StudentID to update: "); int id = sc.nextInt(); sc.nextLine();
                System.out.print("Enter new Name: "); String name = sc.nextLine();
                System.out.print("Enter new Department: "); String dept = sc.nextLine();
                System.out.print("Enter new Marks: "); double marks = sc.nextDouble();
                controller.updateStudent(new Student(id, name, dept, marks));
            } else if(ch==4) {
                System.out.print("Enter StudentID to delete: "); int id = sc.nextInt();
                controller.deleteStudent(id);
            } else if(ch==5) break;
            else System.out.println("Invalid choice.");
        }
    }
}
