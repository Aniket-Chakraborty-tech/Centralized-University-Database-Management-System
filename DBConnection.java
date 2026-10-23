package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // override these with env variables so we don't hardcode passwords
    private static final String URL  = getEnv("UNI_DB_URL", "jdbc:mysql://localhost:3306/university_db");
    private static final String USER = getEnv("UNI_DB_USER", "root");
    private static final String PASS = getEnv("UNI_DB_PASS", "");

    private static String getEnv(String key, String fallback) {
        String v = System.getenv(key);
        return (v == null || v.isEmpty()) ? fallback : v;
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
