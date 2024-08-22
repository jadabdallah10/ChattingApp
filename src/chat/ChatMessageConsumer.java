package chat;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import conn.RabbitMQConnectionUtil;

public class ChatMessageConsumer {
    private static final String EXCHANGE_NAME = "EXCHANGE_TWO";

    public void startConsuming(int user1Id, int user2Id) {
        String queueName = getQueueName(user1Id, user2Id);

        try (Channel channel = RabbitMQConnectionUtil.getConnection().createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, queueName);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received message: " + message);
                // Store the message in MessageStore
                MessageStore.addMessage(user1Id, user2Id, message);
            };

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
                System.out.println("Consumer cancelled: " + consumerTag);
            });
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
