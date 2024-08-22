<!DOCTYPE html>
<html>
<head>
    <title>Sign Up Page</title>
    <link rel="stylesheet" type="text/css" href="signup.css">
</head>
<body>
    <div class="signup-container">
        <div class="signup-card">
            <h2>Sign Up</h2>
            <form action="authentication" method="post">
            
             <input type="hidden" name="action" value="signup">
            
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required><br><br>                              
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required><br><br>                
                <label for="confirm-password">Confirm Password:</label>
                <input type="password" id="confirm-password" name="confirm-password" required><br><br>                
                <input type="submit" value="Sign Up">
                
                <%-- Display error message if present --%>
                <% String error = request.getParameter("error");
                if ("true".equals(error)) { %>
                    <p style="color: red;">Error with your signup. Please try again.</p>
                <% } %>
            </form>
            
            <!-- Link to redirect to Login Page -->
            <a href="LoginPage.jsp" class="redirect-link">
                Already have an account? Log In
            </a>
        </div>
    </div>
</body>
</html>
