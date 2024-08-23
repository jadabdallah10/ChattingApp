package webservice;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import chat.MessageStore;
import servlet.AuthenticationServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
@WebServlet("/messagesoap")
public class MessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIRECTORY = "uploads"; // Directory where files will be stored

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not logged in");
            return;
        }

        int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        String message = request.getParameter("message");
        int senderId = AuthenticationServlet.retrieveId(username);

        System.out.println("Username: " + username);
        System.out.println("Sender ID: " + senderId);
        System.out.println("Receiver ID: " + receiverId);
        System.out.println("message : " + message);
       

        // Handle file upload
        Part filePart = request.getPart("file");
        String fileUrl = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = extractFileNamePart(filePart);
            File uploadDir = new File(getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File file = new File(uploadDir, fileName);
            try (InputStream input = filePart.getInputStream();
                 FileOutputStream output = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                 fileUrl = "http://192.168.0.115:8081/IstiChat/" + UPLOAD_DIRECTORY + "/" + fileName;
            }
        

            if (fileUrl != null) {
            	message = "<a href=\"" + fileUrl + "\" target=\"_blank\" style=\"color: lightblue;\">"+fileName+"</a>" + "<br>"+message;
                System.out.println("Message: " + message);
            }

        }
        MessageStore.addMessage(senderId, receiverId, message); // Store message with file URL
    }
   
    public String extractFileNamePart(Part filePart) {
        String contentDisposition = filePart.getHeader("Content-Disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "unknown";
    }
}
