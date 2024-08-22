package conn;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String PROPERTIES_FILE_PATH = "C:\\Users\\User2\\Desktop\\config.properties"; 

    public static Connection getConnection() {
        Connection con = null;
        Properties properties = new Properties();
        
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(fis);

            String uname = properties.getProperty("db.username");
            String pswrd = properties.getProperty("db.password");
            String url = properties.getProperty("db.url");

            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(url, uname, pswrd);

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return con;
    }
}
