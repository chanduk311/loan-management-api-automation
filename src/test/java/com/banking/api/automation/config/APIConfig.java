package com.banking.api.automation.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class APIConfig {

    private static Properties properties;
    public static RequestSpecification requestSpec;

    static {
        loadProperties();
        initializeRequestSpec();
    }

    /**
     * Load API properties from configuration file
     */
    private static void loadProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/confiq.properties"));
            System.out.println("✓ API properties loaded successfully");
        } catch (IOException e) {
            System.err.println("Error loading API properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize REST Assured request specification
     */
    private static void initializeRequestSpec() {
        try {
            String baseUrl = properties.getProperty("base.url");

            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(baseUrl)
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .build();

            System.out.println("✓ API configuration initialized with base URL: " + baseUrl);

        } catch (Exception e) {
            System.err.println("Error initializing API configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get REST Assured Request Specification
     */
    public static RequestSpecification getRequestSpecification() {
        return requestSpec;
    }

}
