import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Manager {

    /*
    1. Book a room
    2. Check availability of room
    3. Cancel reservation
    4. Update reservation
    5. Search for guest
    6. Exit

    BOOK ROOM MENU
    Guest Name:
     */

    public void startMenu() {
        System.out.println("1. Book a room");
        System.out.println("2. Check availability of room");
        System.out.println("3. Cancel reservation");
        System.out.println("4. Update reservation");
        System.out.println("5. Search for guest");
        System.out.println("6. Exit");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int userInput = scanner.nextInt();

        switch (userInput) {
            case 1 -> bookRoomMenu();
            case 2 -> System.out.println("Case 2 selected");
            case 3 -> System.out.println("Case 3 selected");
            case 4 -> System.out.println("Case 4 selected");
            case 5 -> System.out.println("Case 5 selected");
            case 6 -> System.out.println("Case 6 selected");
            default -> System.out.println("Incorrect input");

        }
    }

    public void bookRoomMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Contact #: ");
        String contactNum = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Total # of guests: ");
        int totalNumOfGuests = Integer.parseInt(scanner.nextLine());

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
            String update = "INSERT INTO Guest(first_name,last_name,contact_number) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, contactNum);
            int resultSet = preparedStatement.executeUpdate();
            System.out.println("rows affected: "+resultSet);
        } catch (SQLException e) {
            System.out.println("Connection Failed!!");
            throw new RuntimeException(e);
        }
    }

}
