package ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Enrollment {
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private Grade grade;
    
    public Enrollment(Student student, Course course) {
        this.student = Objects.requireNonNull(student, "Student cannot be null");
        this.course = Objects.requireNonNull(course, "Course cannot be null");
        this.enrollmentDate = LocalDate.now();
        this.grade = null; // No grade initially
    }
    
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public Grade getGrade() { return grade; }
    
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return String.format("Enrollment[Student: %s, Course: %s, Date: %s, Grade: %s]", 
                           student.getFullName(), course.getTitle(), 
                           enrollmentDate, grade != null ? grade : "Not graded");
    }
}
