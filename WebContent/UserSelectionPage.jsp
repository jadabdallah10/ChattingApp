<%@page import="servlet.AuthenticationServlet"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>IstiChat</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="user-selection.css">
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script>
        function startChat(receiverId, user) {
            var iframe = document.getElementById('chatFrame');
            iframe.src = 'ChatPage.jsp?receiverId=' + encodeURIComponent(receiverId) + '&receiverUsername=' + user;
            
            // Remove 'selected' class from all user items
            var userItems = document.querySelectorAll('.user-item');
            userItems.forEach(function(item) {
                item.classList.remove('selected');
            });

            // Add 'selected' class to the clicked user
            document.getElementById('user-' + user).classList.add('selected');
        }        
        function filterUsers() {
            var input = document.getElementById('searchBar').value.toLowerCase();
            var userItems = document.querySelectorAll('.user-item');

            userItems.forEach(function(item) {
                var username = item.textContent || item.innerText;
                if (username.toLowerCase().indexOf(input) > -1) {
                    item.style.display = "";
                } else {
                    item.style.display = "none";
                }
            });
        }
        
        function clearSearch() {
            document.getElementById('searchBar').value = '';
            filterUsers();
            document.getElementById('searchBar').focus();
        }
        
    </script>
</head>
<body>

    <div class="user-list">
        <div id="logo">
            <img src="istishat_logo.PNG" alt="Logo" id="logoImage">
        </div>
        <h2>Users</h2>
         <div class="search-container">
            <input type="text" id="searchBar" onkeyup="filterUsers()" placeholder="Search users..." />
            <button id="clearButton" onclick="clearSearch()">X</button>
        </div>
        <div id="userList">
            <% 
                Object userListObj = request.getAttribute("users");
                
                if (userListObj instanceof ArrayList<?>) {
                    @SuppressWarnings("unchecked")
                    ArrayList<String> users = (ArrayList<String>) userListObj;
                    
                    if (users.isEmpty()) {
                        out.println("<p>No users available</p>");
                    } else {
                        String currentUsername = (String) request.getSession().getAttribute("username");
                        if (currentUsername == null) {
                            currentUsername = "";
                        }

                        for (String user : users) {
                            if (!user.equals(currentUsername)) {
            %>
            <div id="user-<%=user%>" class="user-item" onclick="startChat(<%=AuthenticationServlet.retrieveId(user) %>, '<%=user%>')">
                <%= user %>
              <a href="ProfilePage.jsp?user=<%=user%>" class="profile-button">
                    <i class="fas fa-user-circle"></i> <!-- Font Awesome icon -->
                </a>
            </div>
            <% 
                            }
                        }
                    }
                } else {
                    out.println("<p>No users found or wrong attribute type</p>");
                }
            %>
        </div>
    </div>
    <div class="chat-area">
        <iframe id="chatFrame" src="" title="Chat Window"></iframe>
    </div>
</body>
</html>
