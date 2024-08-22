

package webservice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.MessageStore;
import servlet.AuthenticationServlet;

import java.io.IOException;

@WebServlet("/messagesoap")
public class MessageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		int receiverId = Integer.parseInt(request.getParameter("receiverId"));
        String message= request.getParameter("message");
        int senderId = AuthenticationServlet.retrieveId(username);
        
        System.out.println(username);
        System.out.println(senderId);
        System.out.println(receiverId);
        System.out.println(message);
        
        try{
            MessageStore.addMessage(senderId, receiverId, message); // Store message
           }
           catch (Exception e){
        	   e.printStackTrace();
           }


    }
}

