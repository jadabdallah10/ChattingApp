package webservice;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import authentication.LoginHandle;
import chat.MessageStore;
import servlet.AuthenticationServlet;
@WebService
public class MessageService {
	@WebMethod
	  public String sendMessage(@WebParam(name = "username") String username, 
			  @WebParam(name = "password") String password,
              @WebParam(name = "usernameReceive") 	String usernameReceive,
              @WebParam(name = "messageContent") String messageContent) {

        if (messageContent == null || messageContent.trim().isEmpty() || usernameReceive.isEmpty() ||
        		username ==null || username.trim().isEmpty() ||password == null || password.trim().isEmpty()) {
                    return "error fill everything please";
        }
        LoginHandle login= new LoginHandle();
        if(login.authenticateUser(username, password)){
          int senderId=AuthenticationServlet.retrieveId(username);
          if (username.equals(usernameReceive)) {
              return "error cannot send message to yourself";}
   else{
           try{
        	   int receiverId = AuthenticationServlet.retrieveId(usernameReceive);
        	   
            MessageStore.addMessage(senderId, receiverId, messageContent); // Store message
           }
           catch (Exception e){
        	   e.printStackTrace();
           }
        
        return "success";
    }
          }
        else{
        	  return "username or password incorrect";
        	}	
		}
}

