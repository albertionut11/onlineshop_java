package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;;
import java.sql.Statement;

public class DatabaseConnector {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/online_shop";
    private static final String USERNAME = "root";
    private static final String PASSWORD = readDatabasePassword();

    private static Connection connection;

    public static String readDatabasePassword() {
        String password = null;
        try (BufferedReader br = new BufferedReader(new FileReader("C://Users/alber/Desktop/mysql.txt"))) {
            password = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
    }
    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConnector.class) {
                if (connection == null) {
                    try {
                        // Register JDBC driver
                        Class.forName(JDBC_DRIVER);

                        // Open a connection
                        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                        System.out.println("Connected to the database!");
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeStatement(String sql) {
        Statement statement = null;

        try {
            // Create a statement object
            statement = connection.createStatement();

            // Execute the SQL statement
            statement.executeUpdate(sql);

            System.out.println("Statement executed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
