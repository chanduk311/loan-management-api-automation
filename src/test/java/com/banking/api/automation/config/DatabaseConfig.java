package com.banking.api.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {

    private static Properties properties;
    private static Connection connection;

    static {
        loadProperties();
    }

    /**
     * Load database properties from config.properties file
     */
    private static void loadProperties() {
        properties = new Properties();
        try {
            // Load from config.properties (same file as API config)
            properties.load(new FileInputStream("src/test/resources/confiq.properties"));
            System.out.println("✓ Database properties loaded successfully from config.properties");
        } catch (IOException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get connection to PostgreSQL database
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            // Load PostgreSQL JDBC driver
            Class.forName(properties.getProperty("db.driver"));

            // Get connection details
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Validate that properties are loaded
            if (url == null || username == null) {
                throw new IllegalArgumentException("Database configuration is missing. Check config.properties file.");
            }

            // Create connection
            connection = DriverManager.getConnection(url, username, password);

            // Set schema search path for PostgreSQL
            String schema = properties.getProperty("db.schema", "banking");
            connection.createStatement().execute("SET search_path TO " + schema + ", public");

            System.out.println("✓ Connected to PostgreSQL database successfully");
            return connection;

        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            System.err.println("Error connecting to PostgreSQL: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Close database connection
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("✓ PostgreSQL connection closed");
        }
    }

    /**
     * Test database connection
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            boolean isValid = conn.isValid(5);
            closeConnection();
            System.out.println("✓ Database connection test passed");
            return isValid;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get property value from configuration
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}