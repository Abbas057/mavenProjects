package com.example.studentmanagement;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DBUtil {
    private static final Logger logger = Logger.getLogger(DBUtil.class.getName());

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream logConfig = DBUtil.class.getClassLoader()
                .getResourceAsStream("logging.properties")) {
            if (logConfig != null) {
                LogManager.getLogManager().readConfiguration(logConfig);
                logger.info("✅ Logging initialized from logging.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load logging configuration", e);
        }

        try (InputStream input = DBUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                throw new RuntimeException("⚠️ application.properties not found");
            }
            prop.load(input);

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");

            logger.info("✅ Database properties loaded successfully");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Failed to load DB properties", ex);
            throw new RuntimeException(ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("🔹 Connected as DB user: " + conn.getMetaData().getUserName());
            return conn;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "❌ Failed to connect to database", e);
            throw e;
        }
    }
}
