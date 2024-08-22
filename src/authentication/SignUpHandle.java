package authentication;

import conn.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpHandle {

    // Method to register a new user
    public boolean registerUser(String username, String password) {
        // Queries
        String checkQuery = "SELECT MAX(id) AS maxId FROM jad_users";
        String insertQuery = "INSERT INTO jad_users (id, username, password) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {

            // Get the current maximum ID
            int newId;
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    int maxId = resultSet.getInt("maxId");
                    newId = maxId + 1; // New ID is maxId + 1
                } else {
                    newId = 1; // Default to 1 if table is empty
                }
            }

            // Insert the new user with the generated ID
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, newId);
                insertStatement.setString(2, username);
                insertStatement.setString(3, password); // Consider hashing the password before storing it

                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0; // Return true if insertion was successful
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection errors
        }

        return false; // Default to registration failure
    }
}

