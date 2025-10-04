import java.sql.*;
import java.util.*;

public class ProductCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/your_database";
    static final String USER = "your_username";
    static final String PASS = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            con.setAutoCommit(false);
            while (true) {
                System.out.println("\n1. Create Product\n2. Display Products\n3. Update Product\n4. Delete Product\n5. Exit");
                System.out.print("Enter choice: ");
                int ch = sc.nextInt();
                sc.nextLine();
                if (ch == 1) createProduct(con, sc);
                else if (ch == 2) displayProducts(con);
                else if (ch == 3) updateProduct(con, sc);
                else if (ch == 4) deleteProduct(con, sc);
                else if (ch == 5) break;
                else System.out.println("Invalid choice.");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void createProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter ProductID: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter ProductName: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();
            PreparedStatement ps = con.prepareStatement("INSERT INTO Product(ProductID, ProductName, Price, Quantity) VALUES(?,?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.setInt(4, qty);
            ps.executeUpdate();
            con.commit();
            ps.close();
            System.out.println("Product added successfully.");
        } catch (Exception e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    static void displayProducts(Connection con) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Product");
            System.out.println("Product Records:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("ProductID") + ", Name: " + rs.getString("ProductName") + ", Price: " + rs.getDouble("Price") + ", Qty: " + rs.getInt("Quantity"));
            }
            rs.close();
            st.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    static void updateProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter ProductID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new ProductName: ");
            String name = sc.nextLine();
            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();
            PreparedStatement ps = con.prepareStatement("UPDATE Product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?");
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if(rows>0) System.out.println("Product updated.");
            else System.out.println("ProductID not found.");
            con.commit();
            ps.close();
        } catch (Exception e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }

    static void deleteProduct(Connection con, Scanner sc) {
        try {
            System.out.print("Enter ProductID to delete: ");
            int id = sc.nextInt();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Product WHERE ProductID=?");
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if(rows>0) System.out.println("Product deleted.");
            else System.out.println("ProductID not found.");
            con.commit();
            ps.close();
        } catch (Exception e) {
            try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        }
    }
}
