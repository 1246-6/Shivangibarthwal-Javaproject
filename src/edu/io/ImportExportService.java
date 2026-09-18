package ccrm.io;

import ccrm.domain.Student;
import ccrm.domain.Course;
import ccrm.domain.Semester;
import ccrm.service.StudentService;
import ccrm.service.CourseService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportExportService {
    private final StudentService studentService;
    private final CourseService courseService;
    
    public ImportExportService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    
    public void importStudentsFromCSV(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1) // Skip header
                .forEach(line -> {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String id = parts[0].trim();
                        String regNo = parts[1].trim();
                        String fullName = parts[2].trim();
                        String email = parts[3].trim();
                        
                        Student student = new Student(id, regNo, fullName, email);
                        studentService.addStudent(student);
                    }
                });
        }
    }
    
    public void importCoursesFromCSV(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            lines.skip(1) // Skip header
                .forEach(line -> {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        String code = parts[0].trim();
                        String title = parts[1].trim();
                        int credits = Integer.parseInt(parts[2].trim());
                        String instructor = parts[3].trim();
                        Semester semester = Semester.valueOf(parts[4].trim().toUpperCase());
                        String department = parts[5].trim();
                        
                        Course course = new Course.Builder(code, title)
                            .credits(credits)
                            .instructor(instructor)
                            .semester(semester)
                            .department(department)
                            .build();
                        
                        courseService.addCourse(course);
                    }
                });
        }
    }
    
    public void exportStudentsToCSV(Path filePath) throws IOException {
        List<String> lines = studentService.findAll().stream()
            .map(student -> String.format("%s,%s,%s,%s,%s",
                student.getId(),
                student.getRegNo(),
                student.getFullName(),
                student.getEmail(),
                student.isActive() ? "Active" : "Inactive"))
            .collect(Collectors.toList());
        
        // Add header
        lines.add(0, "ID,RegistrationNo,FullName,Email,Status");
        
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public void exportCoursesToCSV(Path filePath) throws IOException {
        List<String> lines = courseService.findAll().stream()
            .map(course -> String.format("%s,%s,%d,%s,%s,%s,%s",
                course.getCode(),
                course.getTitle(),
                course.getCredits(),
                course.getInstructor(),
                course.getSemester(),
                course.getDepartment(),
                course.isActive() ? "Active" : "Inactive"))
            .collect(Collectors.toList());
        
        // Add header
        lines.add(0, "Code,Title,Credits,Instructor,Semester,Department,Status");
        
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public void exportEnrollmentsToCSV(Path filePath) throws IOException {
        List<String> lines = studentService.findAll().stream()
            .flatMap(student -> student.getEnrollments().stream()
                .map(enrollment -> String.format("%s,%s,%s,%s,%s",
                    student.getId(),
                    enrollment.getCourse().getCode(),
                    enrollment.getEnrollmentDate(),
                    enrollment.getGrade() != null ? enrollment.getGrade() : "Not graded",
                    enrollment.getGrade() != null ? enrollment.getGrade().getPoints() : "0.0")))
            .collect(Collectors.toList());
        
        // Add header
        lines.add(0, "StudentID,CourseCode,EnrollmentDate,Grade,GradePoints");
        
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
