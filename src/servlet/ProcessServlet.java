package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/process")
public class ProcessServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String receiverUsername = request.getParameter("receiver");
        int receiverId = AuthenticationServlet.retrieveId(receiverUsername);
        

        // Set the receiverId as a request attribute
        request.setAttribute("receiverId", receiverId);
        request.setAttribute("receiverUsername", receiverUsername);
        System.out.println("the name is" + receiverUsername);

        // Forward request to ChatPage.jsp
        request.getRequestDispatcher("ChatPage.jsp").forward(request, response);
    }
}
