package data.Connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/brgy_document_application"; // mysql is a default system database
        String username = "root";
        String password = ""; // try empty first, then your actual password

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL database successfully!");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
