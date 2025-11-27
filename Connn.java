package banking.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connn{
    public Connection connection;
    public Statement statement;

    public Connn() {
        try {
            // Make sure MySQL JDBC driver (Connector/J) is on classpath.
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bankSystem?serverTimezone=UTC",
                    "root",
                    "Taqi@123"
            );
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}