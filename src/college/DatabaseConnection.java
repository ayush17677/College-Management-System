package college;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final String DEFAULT_URL = "jdbc:oracle:thin:@localhost:1521/FREE";
    private static final String DEFAULT_USER = "system";

    private DatabaseConnection() {
    }

    public static Connection open() throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.OracleDriver");
        String url = setting("COLLEGE_DB_URL", DEFAULT_URL);
        String user = setting("COLLEGE_DB_USER", DEFAULT_USER);
        String password = System.getenv("COLLEGE_DB_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new SQLException("Set the COLLEGE_DB_PASSWORD environment variable before starting the application.");
        }
        return DriverManager.getConnection(url, user, password);
    }

    private static String setting(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
