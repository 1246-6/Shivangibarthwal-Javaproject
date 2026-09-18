package ccrm;

import ccrm.cli.CLI;
import ccrm.config.AppConfig;
import ccrm.service.StudentService;
import ccrm.service.CourseService;
import ccrm.util.TestDataGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Campus Course & Records Manager (CCRM)");
        System.out.println("======================================");
        
        // Load configuration (Singleton)
        AppConfig config = AppConfig.getInstance();
        
        // Initialize services
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        
        // Generate test data
        TestDataGenerator.generateTestData(studentService, courseService);
        
        // Initialize and start CLI
        CLI cli = new CLI(studentService, courseService);
        cli.start();
    }
}
