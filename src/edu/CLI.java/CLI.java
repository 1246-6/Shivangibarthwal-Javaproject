package ccrm.cli;

import ccrm.domain.Student;
import ccrm.domain.Course;
import ccrm.domain.Semester;
import ccrm.domain.Grade;
import ccrm.service.StudentService;
import ccrm.service.CourseService;
import ccrm.io.ImportExportService;
import ccrm.io.BackupService;
import ccrm.config.AppConfig;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final ImportExportService importExportService;
    private final BackupService backupService;
    private final AppConfig config;
    
    public CLI(StudentService studentService, CourseService courseService) {
        this.scanner = new Scanner(System.in);
        this.studentService = studentService;
        this.courseService = courseService;
        this.importExportService = new ImportExportService(studentService, courseService);
        this.backupService = new BackupService(importExportService);
        this.config = AppConfig.getInstance();
    }
    
    public void start() {
        boolean running = true;
        
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    manageStudents();
                    break;
                case 2:
                    manageCourses();
                    break;
                case 3:
                    manageEnrollments();
                    break;
                case 4:
                    manageGrades();
                    break;
                case 5:
                    importExportData();
                    break;
                case 6:
                    backupOperations();
                    break;
                case 7:
                    generateReports();
                    break;
                case 8:
                    printJavaPlatformInfo();
                    break;
                case 9:
                    running = false;
                    System.out.println("Thank you for using CCRM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private void printMainMenu() {
        System.out.println("\n=== Campus Course & Records Manager ===");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments");
        System.out.println("4. Manage Grades");
        System.out.println("5. Import/Export Data");
        System.out.println("6. Backup Operations");
        System.out.println("7. Generate Reports");
        System.out.println("8. Java Platform Info");
        System.out.println("9. Exit");
    }
    
    private void manageStudents() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== Student Management ===");
            System.out.println("1. Add Student");
            System.out.println("2. List All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Deactivate Student");
            System.out.println("5. View Student Transcript");
            System.out.println("6. Search Students");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    listAllStudents();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deactivateStudent();
                    break;
                case 5:
                    viewTranscript();
                    break;
                case 6:
                    searchStudents();
                    break;
                case 7:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void addStudent() {
        System.out.println("\n--- Add New Student ---");
        String id = getStringInput("Student ID: ");
        String regNo = getStringInput("Registration Number: ");
        String fullName = getStringInput("Full Name: ");
        String email = getStringInput("Email: ");
        
        try {
            Student student = new Student(id, regNo, fullName, email);
            studentService.addStudent(student);
            System.out.println("Student added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }
    
    private void listAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.findAll();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }
    
    private void updateStudent() {
        System.out.println("\n--- Update Student ---");
        String id = getStringInput("Student ID to update: ");
        
        try {
            String fullName = getStringInput("New Full Name (press Enter to skip): ");
            String email = getStringInput("New Email (press Enter to skip): ");
            
            studentService.updateStudent(id, 
                fullName.isEmpty() ? null : fullName, 
                email.isEmpty() ? null : email);
            
            System.out.println("Student updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }
    
    private void deactivateStudent() {
        System.out.println("\n--- Deactivate Student ---");
        String id = getStringInput("Student ID to deactivate: ");
        
        try {
            studentService.deactivateStudent(id);
            System.out.println("Student deactivated successfully!");
        } catch (Exception e) {
            System.out.println("Error deactivating student: " + e.getMessage());
        }
    }
    
    private void viewTranscript() {
        System.out.println("\n--- Student Transcript ---");
        String id = getStringInput("Student ID: ");
        
        try {
            String transcript = studentService.generateTranscript(id);
            System.out.println(transcript);
        } catch (Exception e) {
            System.out.println("Error generating transcript: " + e.getMessage());
        }
    }
    
    private void searchStudents() {
        System.out.println("\n--- Search Students ---");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Email");
        System.out.println("3. Search Active Students");
        
        int choice = getIntInput("Enter your choice: ");
        
        List<Student> results = null;
        
        switch (choice) {
            case 1:
                String name = getStringInput("Enter name to search: ");
                results = studentService.search(s -> s.getFullName().toLowerCase().contains(name.toLowerCase()));
                break;
            case 2:
                String email = getStringInput("Enter email to search: ");
                results = studentService.search(s -> s.getEmail().toLowerCase().contains(email.toLowerCase()));
                break;
            case 3:
                results = studentService.search(Student::isActive);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        if (results.isEmpty()) {
            System.out.println("No students found.");
        } else {
            results.forEach(System.out::println);
        }
    }
    
    private void manageCourses() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== Course Management ===");
            System.out.println("1. Add Course");
            System.out.println("2. List All Courses");
            System.out.println("3. Update Course");
            System.out.println("4. Deactivate Course");
            System.out.println("5. Search Courses");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    listAllCourses();
                    break;
                case 3:
                    updateCourse();
                    break;
                case 4:
                    deactivateCourse();
                    break;
                case 5:
                    searchCourses();
                    break;
                case 6:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void addCourse() {
        System.out.println("\n--- Add New Course ---");
        String code = getStringInput("Course Code: ");
        String title = getStringInput("Course Title: ");
        int credits = getIntInput("Credits: ");
        String instructor = getStringInput("Instructor: ");
        
        System.out.println("Available Semesters:");
        for (Semester semester : Semester.values()) {
            System.out.println("- " + semester);
        }
        String semesterInput = getStringInput("Semester: ");
        
        String department = getStringInput("Department: ");
        
        try {
            Semester semester = Semester.valueOf(semesterInput.toUpperCase());
            Course course = new Course.Builder(code, title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(department)
                .build();
            
            courseService.addCourse(course);
            System.out.println("Course added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }
    
    private void listAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.findAll();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            courses.forEach(System.out::println);
        }
    }
    
    private void updateCourse() {
        System.out.println("\n--- Update Course ---");
        String code = getStringInput("Course Code to update: ");
        
        try {
            String title = getStringInput("New Title (press Enter to skip): ");
            Integer credits = null;
            String creditsInput = getStringInput("New Credits (press Enter to skip): ");
            if (!creditsInput.isEmpty()) {
                credits = Integer.parseInt(creditsInput);
            }
            
            String instructor = getStringInput("New Instructor (press Enter to skip): ");
            
            Semester semester = null;
            String semesterInput = getStringInput("New Semester (press Enter to skip): ");
            if (!semesterInput.isEmpty()) {
                semester = Semester.valueOf(semesterInput.toUpperCase());
            }
            
            String department = getStringInput("New Department (press Enter to skip): ");
            
            courseService.updateCourse(code, 
                title.isEmpty() ? null : title,
                credits,
                instructor.isEmpty() ? null : instructor,
                semester,
                department.isEmpty() ? null : department);
            
            System.out.println("Course updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating course: " + e.getMessage());
        }
    }
    
    private void deactivateCourse() {
        System.out.println("\n--- Deactivate Course ---");
        String code = getStringInput("Course Code to deactivate: ");
        
        try {
            courseService.deactivateCourse(code);
            System.out.println("Course deactivated successfully!");
        } catch (Exception e) {
            System.out.println("Error deactivating course: " + e.getMessage());
        }
    }
    
    private void searchCourses() {
        System.out.println("\n--- Search Courses ---");
        System.out.println("1. Search by Instructor");
        System.out.println("2. Search by Department");
        System.out.println("3. Search by Semester");
        System.out.println("4. Search Active Courses");
        
        int choice = getIntInput("Enter your choice: ");
        
        List<Course> results = null;
        
        switch (choice) {
            case 1:
                String instructor = getStringInput("Enter instructor name: ");
                results = courseService.findByInstructor(instructor);
                break;
            case 2:
                String department = getStringInput("Enter department: ");
                results = courseService.findByDepartment(department);
                break;
            case 3:
                System.out.println("Available Semesters:");
                for (Semester semester : Semester.values()) {
                    System.out.println("- " + semester);
                }
                String semesterInput = getStringInput("Enter semester: ");
                Semester semester = Semester.valueOf(semesterInput.toUpperCase());
                results = courseService.findBySemester(semester);
                break;
            case 4:
                results = courseService.search(Course::isActive);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        if (results.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            results.forEach(System.out::println);
        }
    }
    
    private void manageEnrollments() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== Enrollment Management ===");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Unenroll Student from Course");
            System.out.println("3. List All Enrollments");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    enrollStudent();
                    break;
                case 2:
                    unenrollStudent();
                    break;
                case 3:
                    listAllEnrollments();
                    break;
                case 4:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void enrollStudent() {
        System.out.println("\n--- Enroll Student in Course ---");
        String studentId = getStringInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        try {
            Course course = courseService.findById(courseCode)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
            
            studentService.enrollInCourse(studentId, course);
            System.out.println("Student enrolled successfully!");
        } catch (Exception e) {
            System.out.println("Error enrolling student: " + e.getMessage());
        }
    }
    
    private void unenrollStudent() {
        System.out.println("\n--- Unenroll Student from Course ---");
        String studentId = getStringInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        try {
            Student student = studentService.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
            
            boolean removed = student.getEnrollments().removeIf(e -> 
                e.getCourse().getCode().equals(courseCode));
            
            if (removed) {
                System.out.println("Student unenrolled successfully!");
            } else {
                System.out.println("Student was not enrolled in this course.");
            }
        } catch (Exception e) {
                System.out.println("Error unenrolling student: " + e.getMessage());
            }
        }
   
    
    private void listAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        List<Student> students = studentService.findAll();
        
        if (students.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            students.forEach(student -> {
                if (!student.getEnrollments().isEmpty()) {
                    System.out.println("Enrollments for " + student.getFullName() + ":");
                    student.getEnrollments().forEach(System.out::println);
                    System.out.println();
                }
            });
        }
    }
    
    private void manageGrades() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== Grade Management ===");
            System.out.println("1. Record Grade");
            System.out.println("2. View Grade Distribution");
            System.out.println("3. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    recordGrade();
                    break;
                case 2:
                    viewGradeDistribution();
                    break;
                case 3:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void recordGrade() {
        System.out.println("\n--- Record Grade ---");
        String studentId = getStringInput("Student ID: ");
        String courseCode = getStringInput("Course Code: ");
        
        System.out.println("Available Grades:");
        for (Grade grade : Grade.values()) {
            System.out.println("- " + grade.name() + ": " + grade.getDescription());
        }
        
        String gradeInput = getStringInput("Grade: ");
        
        try {
            Grade grade = Grade.valueOf(gradeInput.toUpperCase());
            studentService.recordGrade(studentId, courseCode, grade);
            System.out.println("Grade recorded successfully!");
        } catch (Exception e) {
            System.out.println("Error recording grade: " + e.getMessage());
        }
    }
    
    private void viewGradeDistribution() {
        System.out.println("\n--- Grade Distribution ---");
        
        // Using Stream API to aggregate grade distribution
        long totalEnrollments = studentService.findAll().stream()
            .flatMap(student -> student.getEnrollments().stream())
            .filter(enrollment -> enrollment.getGrade() != null)
            .count();
        
        if (totalEnrollments == 0) {
            System.out.println("No grades recorded yet.");
            return;
        }
        
        System.out.println("Grade distribution:");
        for (Grade grade : Grade.values()) {
            long count = studentService.findAll().stream()
                .flatMap(student -> student.getEnrollments().stream())
                .filter(enrollment -> enrollment.getGrade() == grade)
                .count();
            
            double percentage = (double) count / totalEnrollments * 100;
            System.out.printf("%s: %d (%.1f%%)%n", grade, count, percentage);
        }
    }
    
    private void importExportData() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== Import/Export Data ===");
            System.out.println("1. Import Students from CSV");
            System.out.println("2. Import Courses from CSV");
            System.out.println("3. Export Students to CSV");
            System.out.println("4. Export Courses to CSV");
            System.out.println("5. Export Enrollments to CSV");
            System.out.println("6. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    importStudents();
                    break;
                case 2:
                    importCourses();
                    break;
                case 3:
                    exportStudents();
                    break;
                case 4:
                    exportCourses();
                    break;
                case 5:
                    exportEnrollments();
                    break;
                case 6:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void importStudents() {
        System.out.println("\n--- Import Students from CSV ---");
        String filename = getStringInput("CSV filename (in data directory): ");
        Path filePath = config.getDataDirectory().resolve(filename);
        
        try {
            importExportService.importStudentsFromCSV(filePath);
            System.out.println("Students imported successfully!");
        } catch (IOException e) {
            System.out.println("Error importing students: " + e.getMessage());
        }
    }
    
    private void importCourses() {
        System.out.println("\n--- Import Courses from CSV ---");
        String filename = getStringInput("CSV filename (in data directory): ");
        Path filePath = config.getDataDirectory().resolve(filename);
        
        try {
            importExportService.importCoursesFromCSV(filePath);
            System.out.println("Courses imported successfully!");
        } catch (IOException e) {
            System.out.println("Error importing courses: " + e.getMessage());
        }
    }
    
    private void exportStudents() {
        System.out.println("\n--- Export Students to CSV ---");
        String filename = getStringInput("CSV filename: ");
        Path filePath = config.getDataDirectory().resolve(filename);
        
        try {
            importExportService.exportStudentsToCSV(filePath);
            System.out.println("Students exported successfully to: " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting students: " + e.getMessage());
        }
    }
    
    private void exportCourses() {
        System.out.println("\n--- Export Courses to CSV ---");
        String filename = getStringInput("CSV filename: ");
        Path filePath = config.getDataDirectory().resolve(filename);
        
        try {
            importExportService.exportCoursesToCSV(filePath);
            System.out.println("Courses exported successfully to: " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting courses: " + e.getMessage());
        }
    }
    
    private void exportEnrollments() {
        System.out.println("\n--- Export Enrollments to CSV ---");
        String filename = getStringInput("CSV filename: ");
        Path filePath = config.getDataDirectory().resolve(filename);
        
        try {
            importExportService.exportEnrollmentsToCSV(filePath);
            System.out.println("Enrollments exported successfully to: " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting enrollments: " + e.getMessage());
        }
    }
    
    private void backupOperations() {
        boolean backToMain = false;
        
        while (!backToMain) {
            System.out.println("\n=== Backup Operations ===");
            System.out.println("1. Create Backup");
            System.out.println("2. Show Backup Size");
            System.out.println("3. List Backup Files");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    createBackup();
                    break;
                case 2:
                    showBackupSize();
                    break;
                case 3:
                    listBackupFiles();
                    break;
                case 4:
                    backToMain = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void createBackup() {
        System.out.println("\n--- Create Backup ---");
        
        try {
            Path backupDir = backupService.createBackup();
            long size = backupService.getBackupSize(backupDir);
            System.out.println("Backup created successfully at: " + backupDir);
            System.out.println("Backup size: " + size + " bytes");
        } catch (IOException e) {
            System.out.println("Error creating backup: " + e.getMessage());
        }
    }
    
    private void showBackupSize() {
        System.out.println("\n--- Show Backup Size ---");
        
        try {
            Path backupDir = config.getBackupDirectory();
            if (!java.nio.file.Files.exists(backupDir)) {
                System.out.println("No backup directory found.");
                return;
            }
            
            long totalSize = backupService.getBackupSize(backupDir);
            System.out.println("Total backup size: " + totalSize + " bytes");
            
            // Recursively list all backup directories and their sizes
            try (var paths = java.nio.file.Files.list(backupDir)) {
                paths.filter(java.nio.file.Files::isDirectory)
                    .forEach(dir -> {
                        try {
                            long dirSize = backupService.getBackupSize(dir);
                            System.out.println(dir.getFileName() + ": " + dirSize + " bytes");
                        } catch (IOException e) {
                            System.out.println("Error calculating size for: " + dir);
                        }
                    });
            }
        } catch (IOException e) {
            System.out.println("Error accessing backup directory: " + e.getMessage());
        }
    }
    
    private void listBackupFiles() {
        System.out.println("\n--- List Backup Files ---");
        
        try {
            Path backupDir = config.getBackupDirectory();
            if (!java.nio.file.Files.exists(backupDir)) {
                System.out.println("No backup directory found.");
                return;
            }
            
            int depth = getIntInput("Enter depth for recursive listing (0 for all): ");
            backupService.recursiveListFiles(backupDir, depth);
        } catch (IOException e) {
            System.out.println("Error listing backup files: " + e.getMessage());
        }
    }
    
    private void generateReports() {
        System.out.println("\n=== Generate Reports ===");
        
        // Using Stream API to generate various reports
        System.out.println("1. Top Students by GPA");
        System.out.println("2. Course Enrollment Statistics");
        System.out.println("3. Department Statistics");
        
        int choice = getIntInput("Enter your choice: ");
        
        switch (choice) {
            case 1:
                generateTopStudentsReport();
                break;
            case 2:
                generateEnrollmentStatistics();
                break;
            case 3:
                generateDepartmentStatistics();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void generateTopStudentsReport() {
        System.out.println("\n--- Top Students by GPA ---");
        
        List<Student> topStudents = studentService.findAll().stream()
            .filter(Student::isActive)
            .filter(s -> s.calculateGPA() > 0)
            .sorted((s1, s2) -> Double.compare(s2.calculateGPA(), s1.calculateGPA()))
            .limit(10)
            .collect(java.util.stream.Collectors.toList());
        
        if (topStudents.isEmpty()) {
            System.out.println("No students with GPA data found.");
        } else {
            System.out.println("Top 10 Students by GPA:");
            for (int i = 0; i < topStudents.size(); i++) {
                Student student = topStudents.get(i);
                System.out.printf("%d. %s - GPA: %.2f%n", 
                    i + 1, student.getFullName(), student.calculateGPA());
            }
        }
    }
    
    private void generateEnrollmentStatistics() {
        System.out.println("\n--- Course Enrollment Statistics ---");
        
        // Using Stream API to aggregate enrollment data
        var enrollmentStats = studentService.findAll().stream()
            .flatMap(student -> student.getEnrollments().stream())
            .collect(java.util.stream.Collectors.groupingBy(
                enrollment -> enrollment.getCourse().getTitle(),
                java.util.stream.Collectors.counting()
            ));
        
        if (enrollmentStats.isEmpty()) {
            System.out.println("No enrollment data found.");
        } else {
            System.out.println("Course enrollments:");
            enrollmentStats.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> 
                    System.out.printf("%s: %d students%n", entry.getKey(), entry.getValue()));
        }
    }
    
    private void generateDepartmentStatistics() {
        System.out.println("\n--- Department Statistics ---");
        
        // Using Stream API to aggregate department data
        var departmentStats = courseService.findAll().stream()
            .filter(Course::isActive)
            .collect(java.util.stream.Collectors.groupingBy(
                Course::getDepartment,
                java.util.stream.Collectors.counting()
            ));
        
        if (departmentStats.isEmpty()) {
            System.out.println("No department data found.");
        } else {
            System.out.println("Courses by department:");
            departmentStats.forEach((department, count) -> 
                System.out.printf("%s: %d courses%n", department, count));
        }
    }
    
    private void printJavaPlatformInfo() {
        System.out.println("\n=== Java Platform Information ===");
        System.out.println("Java SE vs ME vs EE Comparison:");
        System.out.println("- Java SE (Standard Edition): Core Java platform for desktop and server applications");
        System.out.println("- Java ME (Micro Edition): For embedded and mobile devices with limited resources");
        System.out.println("- Java EE (Enterprise Edition): Extension of SE for enterprise-scale applications");
        System.out.println();
        System.out.println("Java Architecture:");
        System.out.println("- JDK (Java Development Kit): Tools for developing Java applications");
        System.out.println("- JRE (Java Runtime Environment): Environment for running Java applications");
        System.out.println("- JVM (Java Virtual Machine): Executes Java bytecode");
        System.out.println();
        System.out.println("Current Java Version: " + System.getProperty("java.version"));
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
