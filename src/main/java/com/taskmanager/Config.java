package com.taskmanager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getResourceAsStream("/config.properties")) {
            if (input != null) {
                properties.load(input);
            }
            else {
                System.out.println("Config file not found, using defaults");
            }
        } catch (IOException e) {
            System.out.println("Error loading config: " + e.getMessage());
        }
    }

    public static String getDataFilePath() {
        return properties.getProperty("data.file.path", "data/tasks.txt");
    }

    public static String getDateFormat() {
        return properties.getProperty("date.format", "yyy-MM-dd HH:mm:ss");
    }

    public static String getAppVersion() {
        return properties.getProperty("app.version", "1.0");
    }

    public static String getAppName() {
        return properties.getProperty("app.name", "Personal TaskManager");
    }

    public static String getMaxTasks() {
        return properties.getProperty("max.tasks", "1000");
    }

    public static String getDebugMode() {
        return properties.getProperty("debug.mode", "false");
    }
}
