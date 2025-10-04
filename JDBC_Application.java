import java.sql.*;
import java.util.*;

// ---------------------- Part A: Fetching Employee Data ----------------------
class EmployeeData {
    public void fetchEmployees() {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String pass = "yourpassword";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Employee")) {
            System.out.println("\n--- Employee Table ---");
            while (rs.next()) {
                System.out.println("EmpID: " + rs.getInt("EmpID") + ", Name: " + rs.getString("Name") + ", Salary: " + rs.getDouble("Salary"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ---------------------- Part B: CRUD Operations on Product Table ----------------------
class ProductCRUD {
    String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "root";
    String pass = "yourpassword";
    Scanner sc = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\n--- Product CRUD Menu ---");
            System.out.println("1. Add Product\n2. View Products\n3. Update Product\n4. Delete Product\n5. Exit");
            int ch = sc.nextInt();
            switch (ch) {
                case 1 -> createProduct();
                case 2 -> readProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> { return; }
                default -> System.out.println("Invalid Choice");
            }
        }
    }

    void createProduct() {
        System.out.print("Enter Product Name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Quantity: ");
        int qty = sc.nextInt();
        String sql = "INSERT INTO Product(ProductName, Price, Quantity) VALUES(?,?,?)";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.executeUpdate();
            System.out.println("Product Added Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void readProducts() {
        String sql = "SELECT * FROM Product";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n--- Product Table ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ProductID") + ", Name: " + rs.getString("ProductName") +
                        ", Price: " + rs.getDouble("Price") + ", Quantity: " + rs.getInt("Quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateProduct() {
        System.out.print("Enter ProductID to Update: ");
        int id = sc.nextInt();
        System.out.print("Enter New Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter New Quantity: ");
        int qty = sc.nextInt();
        String sql = "UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?";
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDouble(1, price);
                ps.setInt(2, qty);
                ps.setInt(3, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    con.commit();
                    System.out.println("Product Updated Successfully");
                } else {
                    con.rollback();
                    System.out.println("Product Not Found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteProduct() {
        System.out.print("Enter ProductID to Delete: ");
        int id = sc.nextInt();
        String sql = "DELETE FROM Product WHERE ProductID=?";
        try (Connection con = DriverManager.getConnection(url, user, pass)) {
            con.setAutoCommit(false);
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    con.commit();
                    System.out.println("Product Deleted Successfully");
                } else {
                    con.rollback();
                    System.out.println("Product Not Found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ---------------------- Part C: Student Management (MVC) ----------------------
class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    public Student(int id, String name, String dept, double marks) {
        this.studentID = id;
        this.name = name;
        this.department = dept;
        this.marks = marks;
    }

    public int getStudentID() { return studentID; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }
}

class StudentController {
    String url = "jdbc:mysql://localhost:3306/testdb";
    String user = "root";
    String pass = "yourpassword";

    void addStudent(Student s) {
        String sql = "INSERT INTO Student(StudentID, Name, Department, Marks) VALUES(?,?,?,?)";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, s.getStudentID());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDepartment());
            ps.setDouble(4, s.getMarks());
            ps.executeUpdate();
            System.out.println("Student Added Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void viewStudents() {
        String sql = "SELECT * FROM Student";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n--- Student Table ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("StudentID") + ", Name: " + rs.getString("Name") +
                        ", Dept: " + rs.getString("Department") + ", Marks: " + rs.getDouble("Marks"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateStudent(int id, double marks) {
        String sql = "UPDATE Student SET Marks=? WHERE StudentID=?";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, marks);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Student Updated");
            else System.out.println("Student Not Found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteStudent(int id) {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        try (Connection con = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Student Deleted");
            else System.out.println("Student Not Found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class StudentView {
    Scanner sc = new Scanner(System.in);
    StudentController controller = new StudentController();

    void menu() {
        while (true) {
            System.out.println("\n--- Student Management Menu ---");
            System.out.println("1. Add Student\n2. View Students\n3. Update Marks\n4. Delete Student\n5. Exit");
            int ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    controller.addStudent(new Student(id, name, dept, marks));
                }
                case 2 -> controller.viewStudents();
                case 3 -> {
                    System.out.print("Enter StudentID to Update: ");
                    int id = sc.nextInt();
                    System.out.print("Enter New Marks: ");
                    double marks = sc.nextDouble();
                    controller.updateStudent(id, marks);
                }
                case 4 -> {
                    System.out.print("Enter StudentID to Delete: ");
                    int id = sc.nextInt();
                    controller.deleteStudent(id);
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}

// ---------------------- MAIN PROGRAM ----------------------
public class JDBC_Application {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== JDBC Application Main Menu ===");
            System.out.println("1. Fetch Employee Data");
            System.out.println("2. Product CRUD Operations");
            System.out.println("3. Student Management (MVC)");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            switch (ch) {
                case 1 -> new EmployeeData().fetchEmployees();
                case 2 -> new ProductCRUD().menu();
                case 3 -> new StudentView().menu();
                case 4 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid Choice");
            }
        }
    }
}
