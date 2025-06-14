package com.micronautlearning.user;

import java.sql.*;

public class PostgresConnect {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/test";
        String user = "postgres";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // Create a table
            String sqlCreate = "CREATE TABLE IF NOT EXISTS employees (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "position VARCHAR(100), " +
                    "salary NUMERIC(10,2))";
            stmt.execute(sqlCreate);

            // Insert data
            String sqlInsert = "INSERT INTO employees (name, position, salary) VALUES " +
                    "('Alice', 'Developer', 60000), " +
                    "('Bob', 'Designer', 55000)";
            int count = stmt.executeUpdate(sqlInsert);
            System.out.println(count + " rows inserted.");

            // Query data
            String sqlSelect = "SELECT * FROM employees";
            try (ResultSet rs = stmt.executeQuery(sqlSelect)) {
                while (rs.next()) {
                    System.out.println("Employee: " + rs.getString("name") +
                            ", Position: " + rs.getString("position") +
                            ", Salary: " + rs.getDouble("salary"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
