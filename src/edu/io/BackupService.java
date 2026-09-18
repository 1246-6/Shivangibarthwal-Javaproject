package ccrm.io;

import ccrm.config.AppConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class BackupService {
    private final AppConfig config;
    private final ImportExportService importExportService;
    
    public BackupService(ImportExportService importExportService) {
        this.config = AppConfig.getInstance();
        this.importExportService = importExportService;
    }
    
    public Path createBackup() throws IOException {
        // Create backup directory with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = config.getBackupDirectory().resolve("backup_" + timestamp);
        
        Files.createDirectories(backupDir);
        
        // Export data to backup directory
        Path studentsFile = backupDir.resolve("students.csv");
        Path coursesFile = backupDir.resolve("courses.csv");
        Path enrollmentsFile = backupDir.resolve("enrollments.csv");
        
        importExportService.exportStudentsToCSV(studentsFile);
        importExportService.exportCoursesToCSV(coursesFile);
        importExportService.exportEnrollmentsToCSV(enrollmentsFile);
        
        return backupDir;
    }
    
    public long getBackupSize(Path directory) throws IOException {
        try (Stream<Path> paths = Files.walk(directory)) {
            return paths
                .filter(Files::isRegularFile)
                .mapToLong(this::getFileSize)
                .sum();
        }
    }
    
    private long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            System.err.println("Error getting file size for: " + path + " - " + e.getMessage());
            return 0;
        }
    }
    
    public void recursiveListFiles(Path directory, int depth) throws IOException {
        try (Stream<Path> paths = Files.walk(directory, depth)) {
            paths.forEach(path -> {
                if (Files.isRegularFile(path)) {
                    try {
                        long size = Files.size(path);
                        System.out.println(path + " - " + size + " bytes");
                    } catch (IOException e) {
                        System.err.println("Error accessing file: " + path);
                    }
                }
            });
        }
    }
}
