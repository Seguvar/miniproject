package com.EmployeeManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class EmployeeManagement {

    // JDBC URL, username and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/EmployeeDB";
    private static final String USER = "root";
    private static final String PASSWORD = "seguvar@1234"; // Change it to your password

    // JDBC variables for opening and managing connection
    private static Connection connection;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        try {
            // Opening database connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established");

            while (true) {
                System.out.println("\nEmployee Management System");
                System.out.println("1. Insert Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Display Employees");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (option) {
                    case 1:
                        insertEmployee();
                        break;
                    case 2:
                        updateEmployee();
                        break;
                    case 3:
                        deleteEmployee();
                        break;
                    case 4:
                        displayEmployees();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closing the connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Designation: ");
            String designation = scanner.nextLine();

            System.out.print("Enter Location: ");
            String location = scanner.nextLine();

            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();

            String query = "INSERT INTO Employees (employee_id, nameofemployee, designation, location, salary) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, designation);
            preparedStatement.setString(4, location);
            preparedStatement.setDouble(5, salary);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee inserted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.print("Enter new Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter new Designation: ");
            String designation = scanner.nextLine();

            System.out.print("Enter new Location: ");
            String location = scanner.nextLine();

            System.out.print("Enter new Salary: ");
            double salary = scanner.nextDouble();

            String query = "UPDATE Employees SET nameofemployee=?, designation=?, location=?, salary=? WHERE employee_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, designation);
            preparedStatement.setString(3, location);
            preparedStatement.setDouble(4, salary);
            preparedStatement.setInt(5, id);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteEmployee() {
        try {
            System.out.print("Enter Employee ID to delete: ");
            int id = scanner.nextInt();

            String query = "DELETE FROM Employees WHERE employee_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Employee deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayEmployees() {
        try {
            String query = "SELECT * FROM Employees";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("\nEmployee Details:");
            while (resultSet.next()) {
                int id = resultSet.getInt("employee_id");
                String name = resultSet.getString("nameofemployee");
                String designation = resultSet.getString("designation");
                String location = resultSet.getString("location");
                double salary = resultSet.getDouble("salary");

                System.out.println("ID: " + id + ", Name: " + name + ", Designation: " + designation + ", Location: " + location + ", Salary: " + salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}