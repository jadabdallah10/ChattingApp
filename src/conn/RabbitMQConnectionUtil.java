package conn;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RabbitMQConnectionUtil {
    private static final String PROPERTIES_FILE_PATH = "C:\\Users\\User2\\Desktop\\config.properties"; // Default path

    public static Connection getConnection() throws Exception {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(fis);

            String host = properties.getProperty("rabbitmq.host");
            String username = properties.getProperty("rabbitmq.username");
            String password = properties.getProperty("rabbitmq.password");

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setUsername(username);
            factory.setPassword(password);
            return factory.newConnection();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load RabbitMQ properties file", e);
        }
    }
}
