<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="login.css">
</head>
<body>
    <div class="login-container">
        <div class="login-card">
            <h2>Login</h2>
            <form action="authentication" method="post">
            
            <input type="hidden" name="action" value="login">
            
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required><br><br>
                
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required><br><br>
                
                <input type="submit" value="Login">
                                
        <%-- Display error message if present --%>
           <% String error = request.getParameter("error");
   if ("true".equals(error)) { %>
       <p style="color: red;">Invalid username or password. Please try again.</p>
<% } %>
            </form>
 <!-- Link to redirect to SignUp Page -->
            <a href="SignUpPage.jsp" class="redirect-link">
                Don't have an account? Sign Up
            </a>
        </div>
    </div>
</body>
</html>
