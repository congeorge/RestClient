package com.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final String CONFIG_FILE = "./config.properties";
    private static final Properties properties = new Properties();

    private AppConfig() {
    }

    static {
        try (InputStream inputStream = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config properties: " + e.getMessage());
        }
    }


    public static String getProperty(String propertyName, String defaultValue) {
        String val = properties.getProperty(propertyName);
        return (val == null) ? defaultValue : val;

    }

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);

    }
}