package ccrm.domain;

import java.util.Objects;

public class Course {
    private String code;
    private String title;
    private int credits;
    private String instructor;
    private Semester semester;
    private String department;
    private boolean active;
    
    // Private constructor for Builder
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        this.active = true;
    }
    
    // Builder class
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;
        
        public Builder(String code, String title) {
            this.code = Objects.requireNonNull(code);
            this.title = Objects.requireNonNull(title);
        }
        
        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }
        
        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }
        
        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder department(String department) {
            this.department = department;
            return this;
        }
        
        public Course build() {
            return new Course(this);
        }
    }
    
    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }
    
    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public void setDepartment(String department) { this.department = department; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return String.format("Course[Code: %s, Title: %s, Credits: %d, Instructor: %s, Semester: %s, Department: %s]", 
                           code, title, credits, instructor, semester, department);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return code.equals(course.code);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
