
package servlet;

import chat.Message;
import chat.MessageStore;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/FetchMessagesServlet")
public class FetchMessagesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        String receiverId = request.getParameter("receiverId");
        int senderId = AuthenticationServlet.retrieveId(username);
        if (receiverId == null || receiverId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Receiver ID is required");
            return;
        }

        List<Message> messages = MessageStore.getMessagesForUser(senderId, Integer.parseInt(receiverId));
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        for (Message message : messages) {
            String messageClass = (message.getSenderId() == senderId) ? "sender" : "receiver";
            response.getWriter().println("<div class=\"message " + messageClass + "\">" + 
                                         formatMessage(message.getContent()).trim() + 
                                         "</div>");
        }
    }

    private String formatMessage(String text) {
        if (text == null) {
            return "";
        }
        // Escape HTML special characters
        String escapedText = escapeHtml(text);
        
        // Replace newlines with <br> tags
        String formattedText = escapedText.replace("\n", "<br>");
        
        // Convert URLs to clickable links
        return convertUrlsToLinks(formattedText);
    }

    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("\'", "&#039;");
    }

    private String convertUrlsToLinks(String text) {
        if (text == null) {
            return "";
        }
        // Regular expression to detect URLs
        String urlRegex = "(https?://\\S+|www\\.\\S+)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        
        // Use a StringBuffer to build the final result
        StringBuffer result = new StringBuffer();
        int lastEnd = 0;
        
        while (matcher.find()) {
            result.append(text, lastEnd, matcher.start());
            String url = matcher.group();
            
            // Check if the URL starts with 'http://' or 'https://'
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            
            // Append the URL as a clickable link
            result.append("<a href=\"").append(url).append("\" target=\"_blank\" style=\"color: lightblue;\">").append(url).append("</a>");

            lastEnd = matcher.end();
        }
        
        // Append any remaining text
        result.append(text.substring(lastEnd));
        
        return result.toString();
    }
}
