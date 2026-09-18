package ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class AppConfig {
    private static AppConfig instance;
    private Path dataDirectory;
    
    private AppConfig() {
        // Private constructor to prevent instantiation
        dataDirectory = Paths.get("data").toAbsolutePath();
        initializeDataDirectory();
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    
    private void initializeDataDirectory() {
        try {
            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }
        } catch (Exception e) {
            System.err.println("Error initializing data directory: " + e.getMessage());
        }
    }
    
    public Path getDataDirectory() {
        return dataDirectory;
    }
    
    public Path getBackupDirectory() {
        return dataDirectory.resolve("backups");
    }
}
