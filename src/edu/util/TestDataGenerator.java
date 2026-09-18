package ccrm.util;

import ccrm.domain.Student;
import ccrm.domain.Course;
import ccrm.domain.Semester;
import ccrm.service.StudentService;
import ccrm.service.CourseService;

public class TestDataGenerator {
    public static void generateTestData(StudentService studentService, CourseService courseService) {
        // Add some test students
        studentService.addStudent(new Student("S001", "2023001", "John Doe", "john.doe@university.edu"));
        studentService.addStudent(new Student("S002", "2023002", "Jane Smith", "jane.smith@university.edu"));
        studentService.addStudent(new Student("S003", "2023003", "Bob Johnson", "bob.johnson@university.edu"));
        
        // Add some test courses
        courseService.addCourse(new Course.Builder("CS101", "Introduction to Programming")
            .credits(3)
            .instructor("Dr. Alice Brown")
            .semester(Semester.FALL)
            .department("Computer Science")
            .build());
            
        courseService.addCourse(new Course.Builder("MATH201", "Calculus I")
            .credits(4)
            .instructor("Prof. Charlie Davis")
            .semester(Semester.FALL)
            .department("Mathematics")
            .build());
            
        courseService.addCourse(new Course.Builder("ENG101", "English Composition")
            .credits(3)
            .instructor("Dr. Eva Wilson")
            .semester(Semester.SPRING)
            .department("English")
            .build());
    }
}
