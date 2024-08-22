package chat;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import conn.RabbitMQConnectionUtil;

public class ChatMessageProducer {
    private static final String EXCHANGE_NAME = "EXCHANGE_TWO";

    public void sendMessage(int user1Id, int user2Id, String message) {
        String queueName = getQueueName(user1Id, user2Id);

        try (Channel channel = RabbitMQConnectionUtil.getConnection().createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            channel.queueDeclare(queueName, true, false, false, null); // Durable queue
            channel.queueBind(queueName, EXCHANGE_NAME, queueName); // Routing key same as queue name

            channel.basicPublish(EXCHANGE_NAME, queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getQueueName(int user1Id, int user2Id) {
        // Ensure consistent ordering to avoid having two queues for the same pair in reverse order
        if (user1Id > user2Id) {
            return "chatQueue_" + user1Id + "_" + user2Id;
        } else {
            return "chatQueue_" + user2Id + "_" + user1Id;
        }
    }
}
