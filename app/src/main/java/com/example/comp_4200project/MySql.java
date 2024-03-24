package com.example.comp_4200project;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MySql {
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/comp-4200-project";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin123!";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Student ID:");
        int studentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Student Name:");
        String studentName = scanner.nextLine();

        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter Birthdate (YYYY-MM-DD):");
        String birthdate = scanner.nextLine();

        System.out.println("Enter Faculty:");
        String faculty = scanner.nextLine();

        System.out.println("Enter Height:");
        double height = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Weight:");
        double weight = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter Team:");
        String team = scanner.nextLine();

        System.out.println("Enter Jersey Number:");
        int jerseyNumber = scanner.nextInt();

        insertAthleteData(studentID, studentName, firstName, lastName, birthdate, faculty, height, weight, team, jerseyNumber);

        // Retrieve and display inserted data
        retrieveAthleteData();
    }

    // Method to insert data into the 'athlete' table
    public static void insertAthleteData(int studentID, String studentName, String firstName, String lastName, String birthdate,
                                          String faculty, double height, double weight, String team, int jerseyNumber) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO athlete (StudentID, StudentName, FirstName, LastName, Birthdate, Faculty, Height, Weight, Team, JerseyNumber) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentID);
                preparedStatement.setString(2, studentName);
                preparedStatement.setString(3, firstName);
                preparedStatement.setString(4, lastName);
                preparedStatement.setString(5, birthdate);
                preparedStatement.setString(6, faculty);
                preparedStatement.setDouble(7, height);
                preparedStatement.setDouble(8, weight);
                preparedStatement.setString(9, team);
                preparedStatement.setInt(10, jerseyNumber);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data from the 'athlete' table
    public static void retrieveAthleteData() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM athlete";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int studentID = resultSet.getInt("StudentID");
                    String studentName = resultSet.getString("StudentName");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String birthdate = resultSet.getString("Birthdate");
                    String faculty = resultSet.getString("Faculty");
                    double height = resultSet.getDouble("Height");
                    double weight = resultSet.getDouble("Weight");
                    String team = resultSet.getString("Team");
                    int jerseyNumber = resultSet.getInt("JerseyNumber");

                    // Do something with the retrieved data, e.g., display it or use it in your application logic
                    System.out.println("Student ID: " + studentID + ", Student Name: " + studentName + ", First Name: " + firstName
                            + ", Last Name: " + lastName + ", Birthdate: " + birthdate + ", Faculty: " + faculty
                            + ", Height: " + height + ", Weight: " + weight + ", Team: " + team + ", Jersey Number: " + jerseyNumber);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}