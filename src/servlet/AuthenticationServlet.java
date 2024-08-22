package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authentication.LoginHandle;
import authentication.SignUpHandle;
import conn.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginHandle loginHandle = new LoginHandle();
    private SignUpHandle signupHandle = new SignUpHandle();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action"); // Determine the action (login or signup)
        System.out.println(action);

        if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("signup".equals(action)) {
            handleSignUp(request, response);
        } else {
            // Handle unknown action
            response.sendRedirect("error.jsp"); // Redirect to an error page or handle as needed
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int id= retrieveId(username);

        boolean isAuthenticated = loginHandle.authenticateUser(username, password);
        ArrayList<String> users = fetchAllUsers();

        if (isAuthenticated) {
            // Set session attribute for username
            request.getSession().setAttribute("username", username);

            // Set request attribute for users
            request.setAttribute("users", users);
            request.setAttribute("id", id);

            // Forward request to JSP
            request.getRequestDispatcher("UserSelectionPage.jsp").forward(request, response);
        } else {
            // Redirect back to login page with error message
            response.sendRedirect("LoginPage.jsp?error=true");
        }
    }


    private void handleSignUp(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");       
        String confirmPassword = request.getParameter("confirm-password");
        int id= retrieveId(username);
        ArrayList<String> users = fetchAllUsers();
        
        System.out.println(username);
        System.out.println(password);
        
        if (!password.equals(confirmPassword))  response.sendRedirect("SignUpPage.jsp?error=true");
        else if (users.contains(username))   response.sendRedirect("SignUpPage.jsp?error=true");
        else{
        boolean isCreated = signupHandle.registerUser(username, password);

        if (isCreated) {
        	  request.getSession().setAttribute("username", username);

              // Set request attribute for users
              request.setAttribute("users", users);
              request.setAttribute("id", id);

              // Forward request to JSP
              request.getRequestDispatcher("UserSelectionPage.jsp").forward(request, response);
        } else {
            // Redirect back to sign-up page with an error message
            response.sendRedirect("SignUpPage.jsp?error=true");
        }
       }
    }
    
    public static ArrayList<String> fetchAllUsers() {
        ArrayList<String> users = new ArrayList<>();
        String query = "SELECT username FROM jad_users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public static ArrayList<Integer> fetchAllUsersExceptSender() {
        ArrayList<Integer> users = new ArrayList<>();
        String query = "SELECT id FROM jad_users where username!='Roadster' ";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(rs.getInt("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public static int retrieveId(String username) {
        String query = "SELECT id FROM jad_users WHERE username=?";
        int id = -1; // Default value if user not found

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            // Set the username parameter
            ps.setString(1, username);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, log the exception or rethrow it if needed
        }

        return id;
    }
    
    public static String retrieveName(int id) {
        String query = "SELECT username FROM jad_users WHERE id=?";
        String username = ""; // Default value if user not found

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            // Set the username parameter
            ps.setInt(1, id);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, log the exception or rethrow it if needed
        }

        return username;
    }
    
}
