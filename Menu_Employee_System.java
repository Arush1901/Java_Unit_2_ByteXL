import java.io.*;
import java.util.*;
class Employee implements Serializable {
    int id;
    String name, designation;
    double salary;
    Employee(int id, String name, String designation, double salary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.salary = salary;
    }
}
public class EmployeeManagement {
    static final String FILE = "employees.ser";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Employee\n2. Display All Employees\n3. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) addEmployee(sc);
            else if (ch == 2) displayEmployees();
            else if (ch == 3) break;
            else System.out.println("Invalid choice.");
        }
    }
    static void addEmployee(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Designation: ");
        String desig = sc.nextLine();
        System.out.print("Enter Salary: ");
        double sal = sc.nextDouble();
        Employee e = new Employee(id, name, desig, sal);
        try {
            ArrayList<Employee> list = new ArrayList<>();
            File f = new File(FILE);
            if (f.exists()) {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
                list = (ArrayList<Employee>) in.readObject();
                in.close();
            }
            list.add(e);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
            out.writeObject(list);
            out.close();
            System.out.println("Employee added.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    static void displayEmployees() {
        try {
            File f = new File(FILE);
            if (!f.exists()) {
                System.out.println("No records found.");
                return;
            }
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
            ArrayList<Employee> list = (ArrayList<Employee>) in.readObject();
            in.close();
            if (list.isEmpty()) System.out.println("No records found.");
            else {
                System.out.println("\nEmployee Records:");
                for (Employee e : list)
                    System.out.println("ID: " + e.id + ", Name: " + e.name + ", Designation: " + e.designation + ", Salary: " + e.salary);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
