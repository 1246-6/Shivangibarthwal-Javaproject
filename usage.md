# Campus Course & Records Manager - Usage Guide

## Getting Started

1. Run the application by executing the `Main.java` class
2. Follow the menu prompts to navigate through the system

## Basic Operations

### Managing Students
- Add students with unique IDs and registration numbers
- Update student information (name, email)
- Deactivate students (soft delete)
- Search students by various criteria
- View student transcripts with GPA calculation

### Managing Courses
- Add courses with codes, titles, credits, instructors, semesters, and departments
- Update course information
- Deactivate courses
- Search courses by instructor, department, or semester

### Enrollment Management
- Enroll students in courses with credit limit validation (max 18 credits per semester)
- Unenroll students from courses
- View all enrollments

### Grade Management
- Record grades for students in courses
- View grade distribution statistics

## File Operations

### Importing Data
- Place CSV files in the `data` directory
- Use the import menu options to load students and courses from CSV files
- CSV format must match the sample files provided

### Exporting Data
- Export current data to CSV files for backup or external processing
- Exports include students, courses, and enrollment information

### Backup Operations
- Create timestamped backups of all data
- View backup sizes and file listings
- Backups are stored in the `data/backups` directory

## Reports

- Top students by GPA
- Course enrollment statistics
- Department statistics

## Sample Data

The application includes sample data for testing:
- 3 sample students
- 3 sample courses

You can add more data through the menu system or by importing CSV Files
