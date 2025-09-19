package models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBase {
    private static ConnectionBase instance;
    private Connection connection;

    private final String url = "jdbc:mysql://localhost:3306/banque";
    private final String username = "root";
    private final String password = "lakroune";

     private ConnectionBase() {
        try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             this.connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion réussie à MySQL !");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Driver JDBC introuvable !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion MySQL !");
            e.printStackTrace();
        }
    }

    public static ConnectionBase getInstance() {
        if (instance == null) {
            instance = new ConnectionBase();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
