package ccrm.domain;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Person {
    protected String id;
    protected String fullName;
    protected String email;
    protected LocalDate dateCreated;
    protected boolean active;
    
    public Person(String id, String fullName, String email) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.fullName = Objects.requireNonNull(fullName, "Full name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.dateCreated = LocalDate.now();
        this.active = true;
    }
    
    // Abstract method to be implemented by subclasses
    public abstract String getDetails();
    
    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getDateCreated() { return dateCreated; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Email: %s, Active: %s", 
                           id, fullName, email, active ? "Yes" : "No");
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
