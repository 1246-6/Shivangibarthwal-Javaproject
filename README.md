# Campus Course & Records Manager (CCRM)

## 1. Project Overview

Campus Course & Records Manager (CCRM) is a Java-based console application developed to make academic record management easier and more organized. It keeps student details, course information, enrollments, grades, and GPA records in one place.

The project is designed as a simple campus management system where users can add and update records, enroll students in courses, record grades, calculate GPA, and generate useful reports. It also includes file-based features such as CSV import/export and data backup.

The project helps demonstrate how Java and OOP concepts can be used together to build a practical application.

---

## 2. Features

- Student management
- Course management
- Student enrollment
- Credit-limit validation
- Grade recording and GPA calculation
- Search and update records
- Student/course activation and deactivation
- CSV data import and export
- Data backup and restore
- Reports and statistics
- Input validation and exception handling

---

## 3. Technologies and Tools Used

- **Programming Language:** Java
- **IDE:** Eclipse / IntelliJ IDEA / VS Code
- **Data Storage:** CSV files and local files
- **Version Control:** Git and GitHub
- **Java Concepts:** OOP, Collections, Interfaces, Abstract Classes, Enums
- **APIs:** Stream API, Date/Time API, File I/O, NIO.2
- **Other Concepts:** Lambda Expressions, Functional Interfaces, Exception Handling, Recursion
- **Design Patterns:** Singleton and Builder

---

## 4. Java Concepts Used

The project uses different Java concepts in practical situations:

- **Encapsulation:** Used to keep class data protected and controlled through methods.
- **Inheritance:** Used when classes share common properties or behavior.
- **Abstraction:** Used to hide unnecessary implementation details.
- **Polymorphism:** Used to allow different classes to provide their own implementation.
- **Collections:** Used for storing and managing student, course, and enrollment data.
- **Streams and Lambdas:** Used for filtering, searching, and processing records.
- **Exception Handling:** Used to handle invalid inputs and unexpected situations.
- **File Handling:** Used for saving, importing, exporting, backing up, and restoring data.

---

## 5. Installation and Setup

### Windows Installation

1. Download JDK from Oracle's website
2. Run the installer and follow the prompts
3. Set JAVA_HOME environment variable to JDK installation path
4. Add %JAVA_HOME%\bin to PATH environment variable

### Eclipse IDE Setup

1. Download Eclipse IDE for Java Developers
2. Extract and run eclipse.exe
3. Create a new Java project
4. Configure build path to use the installed JDK
5. Create packages and classes as per the project structure

## How to Run

1. Clone the repository
2. Open the project in Eclipse or any Java IDE
3. Ensure Java 8 or later is configured
4. Run the `Main.java` class

## 6. How to Use the Application

Once the application is started, a menu will appear in the console. Select the required option and follow the instructions shown on the screen.

The main options include:

1. Add and manage student details
2. Add and manage course details
3. Enroll students in courses
4. Add or update student grades
5. Calculate and view GPA
6. Search and update records
7. Import or export data using CSV files
8. Create or restore backups
9. Generate reports
10. Exit the application

Each option guides the user through the required inputs and displays the result after completing the operation.

---

## 7. Testing Instructions

The project can be tested by running each feature and checking whether the expected result is obtained.

### Student Testing

- Add a student and check whether the details are stored correctly.
- Search for the student using the available search option.
- Update the student's details and verify the changes.
- Enter invalid details to check input validation.

### Course Testing

- Add a new course and verify its information.
- Search for an existing course.
- Update course details and check the changes.
- Test activating and deactivating a course.

### Enrollment Testing

- Enroll a student in a valid course.
- Try enrolling the same student in the same course again.
- Try enrolling beyond the allowed credit limit.
- Check that invalid enrollment requests are rejected.

### Grade and GPA Testing

- Record grades for enrolled courses.
- Calculate the student's GPA.
- Change a grade and check whether the GPA is updated correctly.

### File Testing

- Export existing records to a CSV file.
- Import valid CSV data and verify the records.
- Create a backup of the data.
- Restore the backup and check whether the records are recovered correctly.

### Error Testing

- Enter an invalid menu option.
- Enter incorrect data formats.
- Leave required fields empty.
- Check that proper error messages are displayed and the application continues running.

---

## 8. Expected Result

After successful execution, the application should allow users to manage students, courses, enrollments, grades, and academic records through the console.

Features such as GPA calculation, credit-limit checking, searching, updating, CSV handling, backup, restore, and report generation should work correctly. Invalid inputs should be handled properly without stopping the application unexpectedly.

---

## 9. Screenshots

**Add Students**
<img width="1720" height="914" alt="Add" src="https://github.com/user-attachments/assets/88d4c52a-57af-4f83-a656-b9a2e6f6e7ea" />

**Display**
<img width="1745" height="901" alt="Display" src="https://github.com/user-attachments/assets/b2ff009b-3324-49f7-a4ec-b32204ac139e" />


**Student Transcripts**
<img width="1280" height="513" alt="Transcripts" src="https://github.com/user-attachments/assets/52ad4ff8-4d19-4d19-8ef9-2229ff5b6f74" />



---

## 10. Project Purpose

The purpose of CCRM is to build a simple academic record management system while applying the Java concepts learned during the course.

The project combines OOP, collections, exception handling, interfaces, streams, file handling, and other Java features in one practical application. It provides a good example of how Java can be used to solve a common problem in a campus environment.
