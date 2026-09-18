package ccrm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student extends Person {
    private String regNo;
    private List<Enrollment> enrollments;
    
    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = Objects.requireNonNull(regNo, "Registration number cannot be null");
        this.enrollments = new ArrayList<>();
    }
    
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    
    public List<Enrollment> getEnrollments() { return enrollments; }
    
    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }
    
    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }
    
    public double calculateGPA() {
        if (enrollments.isEmpty()) return 0.0;
        
        double totalPoints = 0.0;
        int totalCredits = 0;
        
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != null) {
                totalPoints += enrollment.getGrade().getPoints() * enrollment.getCourse().getCredits();
                totalCredits += enrollment.getCourse().getCredits();
            }
        }
        
        return totalCredits > 0 ? totalPoints / totalCredits : 0.0;
    }
    
    @Override
    public String getDetails() {
        return String.format("Student: %s (Reg: %s), GPA: %.2f", 
                           fullName, regNo, calculateGPA());
    }
    
    @Override
    public String toString() {
        return String.format("Student[ID: %s, Reg: %s, Name: %s, Email: %s, Active: %s]", 
                           id, regNo, fullName, email, active ? "Yes" : "No");
    }
}
