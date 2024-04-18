
package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBContext {

    public Connection getConnection() {
        String db = "social";
        String url = "jdbc:mysql://localhost:3306/" + db;
        String user = "root";
        String password = "12345";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }  

    public boolean testConnection() {
        Connection connection = getConnection();
        if (connection != null) {
            try {
                System.out.println("Database connection successful.");
                connection.close(); 
                return true;
            } catch (SQLException e) {
                System.out.println("Error closing the connection: " + e.getMessage());
            }
        } else {
            System.out.println("Unable to establish database connection.");
        }
        return false;
    }

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        dbContext.testConnection();
    }

}
