package chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageStore {
    private static final Map<String, List<Message>> userMessages = new ConcurrentHashMap<>();

    public static synchronized void addMessage(int senderId, int receiverId, String messageContent) {
        String key = getConversationKey(senderId, receiverId);
        Message message = new Message(senderId, messageContent);
        userMessages.computeIfAbsent(key, k -> new ArrayList<>()).add(message);
    }

    public static synchronized List<Message> getMessagesForUser(int user1Id, int user2Id) {
        String key = getConversationKey(user1Id, user2Id);
        return userMessages.getOrDefault(key, new ArrayList<>());
    }

    private static String getConversationKey(int user1Id, int user2Id) {
        if (user1Id > user2Id) {
            return user1Id + "_" + user2Id;
        } else {
            return user2Id + "_" + user1Id;
        }
    }
}
