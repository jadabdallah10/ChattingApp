package chat;

public class Message {
    private final int senderId;
    private final String content;

    public Message(int senderId, String content) {
        this.senderId = senderId;
        this.content = content;
        
    }

    public int getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

}
