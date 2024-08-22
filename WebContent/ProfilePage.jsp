<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="profile.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <a href="javascript:history.back()" class="back-button">
        <i class="fas fa-arrow-left"></i> Back
    </a>
    <div class="profile-container">
        <header class="profile-header">
            <h1>User Profile</h1>
        </header>
        <form class="profile-details-form">
            <div class="profile-details">
                <%
                    String username = request.getParameter("user");
                    if (username != null && !username.isEmpty()) {
                        out.println("<h2>" + username + "</h2>");
                    } else {
                        out.println("<h2>User Not Found</h2>");
                    }
                %>
            </div>
            <input type="hidden" name="username" value="<%=username%>">
            <button type="submit" class="block-button" disabled>Block User</button>
        </form>
    </div>
</body>
</html>
