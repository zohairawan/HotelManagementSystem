import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        // Load file
        Properties properties = new Properties();
        try {
            properties.load(Files.newInputStream(Path.of("hotel.properties"),
                    StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set server name, port, and database name
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(properties.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(properties.getProperty("port")));
        dataSource.setDatabaseName(properties.getProperty("databaseName"));

        // Establish connection using username and password
        try (Connection connection = dataSource.getConnection(
                properties.getProperty("user"),
                System.getenv("MYSQL_PASS")
        )) {
            System.out.println("Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
