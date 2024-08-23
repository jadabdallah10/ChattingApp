<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <link rel="stylesheet" type="text/css" href="chatpage.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var urlParams = new URLSearchParams(window.location.search);
            var receiverId = urlParams.get('receiverId');
            var textarea = document.querySelector('textarea[name="message"]');
            var fileInput = document.getElementById('fileInput');
            var fileNameDisplay = document.getElementById('fileName');
            scrollToBottom();

            function updateFileName() {
                if (fileInput.files.length > 0) {
                    fileNameDisplay.textContent = fileInput.files[0].name;
                } else {
                    fileNameDisplay.textContent = "";
                }
            }
            
            function adjustHeight() {
                textarea.style.height = 'auto'; // Reset height to auto to calculate scrollHeight
                textarea.style.height = textarea.scrollHeight + 'px'; // Set height based on scrollHeight
            }
            textarea.addEventListener('input', adjustHeight);
            adjustHeight();
            
            function scrollToBottom() {
                var messagesContainer = document.getElementById('messages');
                messagesContainer.scrollTop = messagesContainer.scrollHeight; // Scroll to the bottom
            }           
            
            function handleKeyPress(event) {
                if (event.key === 'Enter' && !event.shiftKey) {   
                    event.preventDefault(); 
                    sendMessage(event); // Trigger the send message function
                    setTimeout(scrollToBottom, 2000);
                }
            }
            
            textarea.addEventListener('keydown', handleKeyPress); // Add event listener to handle key press
                                    
            function fetchMessages() {
                var xhr = new XMLHttpRequest();
                xhr.open('GET', 'FetchMessagesServlet?receiverId=' + encodeURIComponent(receiverId), true);
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        var messagesContainer = document.getElementById('messages');
                        var newContent = xhr.responseText;                        
                        // Check if the content has changed
                        if (messagesContainer.innerHTML !== newContent) {
                            messagesContainer.innerHTML = newContent;                          
                        }
                    } else {
                        console.error('Error fetching messages: ' + xhr.status);
                    }
                };
                xhr.send();
            }

            function sendMessage(event) {
                event.preventDefault();
                
                if(document.querySelector('textarea[name="message"]').value.trim() === ""){
                	return;//do nothing if message is empty
                	
                }
                
                var form = document.getElementById('sendMessageForm');
                var formData = new FormData(form); // Use FormData to handle file uploads
                
                var xhr = new XMLHttpRequest();
                xhr.open('POST', 'messagesoap', true);
                xhr.onload = function() {
                    if (xhr.status === 200) { 
                        setTimeout(scrollToBottom, 1000);
                    } else {
                        console.error('Error sending message: ' + xhr.status);
                    }
                };
                document.getElementById('fileInput').value = "";
                document.querySelector('textarea[name="message"]').value="";
                fileNameDisplay.textContent = "";
                xhr.send(formData);
            }

            fileInput.addEventListener('change', updateFileName);
            document.getElementById('sendMessageForm').addEventListener('submit', sendMessage);

            // Fetch messages initially and then periodically
            fetchMessages();
            setInterval(fetchMessages, 1000); // Fetch every second
            setTimeout(scrollToBottom, 1000);
        });
    </script>
</head>
<body>
    <h2>Chat with <%= request.getParameter("receiverUsername") %></h2>
    <div id="messages">
        <!-- Messages will be loaded here -->
    </div>
    <form id="sendMessageForm" action="messagesoap" method="post" enctype="multipart/form-data">
        <textarea id="message" name="message" rows="4" cols="50" placeholder="Type a message..."></textarea>
      <label for="fileInput" class="file-icon"><i class="fas fa-paperclip"></i></label>
        <input type="file" id="fileInput" name="file" style="display: none;">
         <span id="fileName" class="file-name"></span><br>
        <input type="hidden" name="receiverId" value="<%= request.getParameter("receiverId") %>">
        <input type="submit" id="sendButton" value="Send">
    </form>
</body>
</html>
