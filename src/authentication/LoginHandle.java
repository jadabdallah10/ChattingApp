package authentication;

import conn.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginHandle {


	   // Method to check user credentials
    public boolean authenticateUser(String username, String password) {
	        String query = "SELECT username FROM jad_users WHERE username = ? AND password = ?";
	        //users_jad

	        try (Connection connection = DBConnection.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {

	            statement.setString(1, username);
	            statement.setString(2, password);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                return resultSet.next(); // If a row is found, login is successful
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle database connection errors
	        }

	        return false; // Default to login failure
	    }
	

}
