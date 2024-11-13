import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public DatabaseConnection() {
    }

    private static final String url = System.getenv("URL");
    private static final String user = System.getenv("USER");
    private static final String password = System.getenv("PASSWORD");

    private static Connection connection = null;

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("La conexión fue exitosa!");

        } else {
            connection = null;
            System.out.println("Algo ha salido mal");
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}