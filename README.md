# 📖 Concurrent Campus Resource Booking System (CCRBS)
### 📘 COURSE: PROGRAMMING IN JAVA

A localized, highly concurrent, modular Java console application designed to manage campus resource rosters, assign room and facility reservations, and monitor availability in a real-world academic context. 🏫⚡

This project was developed for the **Programming in Java** course. It bridges core object-oriented principles with advanced runtime capabilities like multithreading and database persistence to solve complex campus scheduling challenges. 🚀

---

## 🧠 Core Java Concepts

This system is architected to explicitly demonstrate the foundational concepts of Java programming:

* 🧵 **Multithreading & Concurrency**: The system simulates real-time resource booking without freezing the console interface. Worker threads (`BookingThread`) utilize `Thread`/`Runnable` and `synchronized` methods in `BookingService` to concurrently process active reservation requests safely and prevent race conditions or double-bookings.
* 🧱 **Object-Oriented Programming (OOP)**: Strict adherence to abstraction and inheritance. An abstract `CampusResource` base class implementing the `Bookable` interface is extended by specialized `LectureHall`, `Laboratory`, and `SportsFacility` classes utilizing method overriding, encapsulation, and the `super` keyword.
* 💾 **Data Persistence & File I/O**: Bridges in-memory collections (`ArrayList`) with durable storage. Uses JDBC (SQLite) for SQL-based CRUD operations, Character-oriented streams (`BufferedWriter`/`FileWriter`) to generate human-readable booking receipts, and Byte-oriented streams (`DataOutputStream`) for structured binary records.
* 🛡️ **Robust Exception Handling**: Enforces business logic and prevents application crashes using custom, user-defined exceptions (e.g., `ResourceNotAvailableException`, `InvalidBookingException`, `UserNotFoundException`).

---

## 🏗️ System Architecture: How It Works

The project adheres to strict **Separation of Concerns (SoC)** across a layered architecture:

1. ⚙️ **The Core Engine (`service/`)**: Central controller (`BookingService`, `ResourceService`, `UserService`) that manages in-memory data structures, enforces thread synchronization locks, and validates business rules before confirming any reservation.
2. 💻 **The Interface (`Main.java`)**: An interactive Command-Line Interface (CLI) that routes user commands and collects input via `Scanner` without executing business logic directly.
3. 🗄️ **The Data Access Layer (`dao/`)**: Abstracted persistence layer (`UserDAO`, `ResourceDAO`, `BookingDAO`) executing SQL queries via JDBC `PreparedStatements`.
4. 🔧 **The Utility Layer (`util/`)**: Dedicated helper classes that abstract external interactions. `DatabaseConnection` manages SQLite connections via a thread-safe Singleton pattern, while `ReceiptWriter` and `ReceiptReader` handle disk I/O operations.

---

## 🚀 Setup & Installation

### 📋 Prerequisites:
* ☕ **JDK 11+** (Verify with `javac -version` and `java -version`)
* 🗄️ **SQLite JDBC Driver**: Download `sqlite-jdbc-3.42.0.0.jar`

> ⚠️ **CRITICAL**: Place the `sqlite-jdbc-3.42.0.0.jar` file directly inside the `lib/` folder alongside the `src/` folder for the classpath to resolve correctly.

---

### 1️⃣ Compile the Source Code

**🪟 Windows (Command Prompt / PowerShell):**
```cmd
javac -cp "lib\sqlite-jdbc-3.42.0.0.jar" -d out -sourcepath src src\com\ccrbs\Main.java src\com\ccrbs\model\*.java src\com\ccrbs\dao\*.java src\com\ccrbs\service\*.java src\com\ccrbs\exception\*.java src\com\ccrbs\util\*.java
```

**🐧 Linux / 🍎 macOS:**
```bash
javac -cp "lib/sqlite-jdbc-3.42.0.0.jar" -d out -sourcepath src src/com/ccrbs/Main.java src/com/ccrbs/model/*.java src/com/ccrbs/dao/*.java src/com/ccrbs/service/*.java src/com/ccrbs/exception/*.java src/com/ccrbs/util/*.java
```

---

### 2️⃣ Execute the Application

**🪟 Windows:**
```cmd
java -cp "out;lib\sqlite-jdbc-3.42.0.0.jar" com.ccrbs.Main
```

**🐧 Linux / 🍎 macOS:**
```bash
java -cp "out:lib/sqlite-jdbc-3.42.0.0.jar" com.ccrbs.Main
```

---

## 🖥️ CLI Execution Scenarios

The system operates via an interactive CLI menu. 📋

### 📌 Scenario 1: Initializing the System & Booking a Resource

```text
=== Concurrent Campus Resource Booking System ===
[1] Register
[2] Login
[3] Exit
Enter your choice: 2

Enter email: john@university.edu
Enter password: mypassword

Welcome, John Doe!
Role: Student

--- Dashboard ---
[1] View All Resources
[2] Filter by Type
[3] Check Availability
[4] Book a Resource
[5] View My Bookings
[6] Cancel Booking
[7] View Receipt
[0] Logout
Enter your choice: 1

ID  | Name                | Type           | Location        | Available
----|---------------------|----------------|-----------------|----------
1   | Main Auditorium     | LECTURE_HALL   | Building A      | Yes
2   | Room 201            | LECTURE_HALL   | Building B      | Yes
3   | Computer Lab 1      | LABORATORY     | Building C      | Yes

Enter your choice: 4
Enter Resource ID: 1
Enter Date (YYYY-MM-DD): 2026-09-18
Enter Time Slot (e.g., 09:00-10:00): 10:00-11:00

[+] SUCCESS: Booking confirmed! Booking ID: 1
[+] Receipt generated and saved to: receipts/receipt_1.txt
```

---

### ⚡ Scenario 2: Concurrent Booking Simulation & Double-Booking Prevention

Simulates multiple concurrent threads requesting the exact same resource and time slot simultaneously. Only the first thread acquiring the lock succeeds; subsequent threads receive a custom exception. 🛑

```text
Enter your choice: 10
Enter Resource ID to contest: 1
Enter Date (YYYY-MM-DD): 2026-09-18
Enter Time Slot: 10:00-11:00

[>] Launching 5 concurrent booking threads...

[Thread-1] Attempting to book Resource 1...
[Thread-2] Attempting to book Resource 1...
[Thread-3] Attempting to book Resource 1...
[+] Thread-1: SUCCESS - Booked Resource 1 (Booking ID: 5)
[!] Thread-2: ERROR (ResourceNotAvailableException) - Resource 1 is already booked for 10:00-11:00.
[!] Thread-3: ERROR (ResourceNotAvailableException) - Resource 1 is already booked for 10:00-11:00.
[!] Thread-4: ERROR (ResourceNotAvailableException) - Resource 1 is already booked for 10:00-11:00.
[!] Thread-5: ERROR (ResourceNotAvailableException) - Resource 1 is already booked for 10:00-11:00.

--- Simulation Results ---
Thread-1: ✓ SUCCESS
Thread-2: ✗ FAILED (Blocked by synchronization lock)
Thread-3: ✗ FAILED (Blocked by synchronization lock)
Thread-4: ✗ FAILED (Blocked by synchronization lock)
Thread-5: ✗ FAILED (Blocked by synchronization lock)
```

---

## 🗺️ Repository Map

* 📄 `src/com/ccrbs/Main.java` — Interactive CLI menu and execution entry point.
* 📦 `src/com/ccrbs/model/` — OOP data models (`User.java`, `Student.java`, `Faculty.java`, `Admin.java`, `Bookable.java`, `CampusResource.java`, `LectureHall.java`, `Laboratory.java`, `SportsFacility.java`, `Booking.java`).
* ⚙️ `src/com/ccrbs/service/` — Concurrency implementations (`BookingService.java`, `BookingThread.java`, `ResourceService.java`, `UserService.java`).
* 🗄️ `src/com/ccrbs/dao/` — JDBC CRUD operations (`UserDAO.java`, `ResourceDAO.java`, `BookingDAO.java`) handling SQLite connections.
* 🛠️ `src/com/ccrbs/util/` — Utility classes including thread-safe Singleton `DatabaseConnection.java` and I/O stream handlers `ReceiptWriter.java` / `ReceiptReader.java`.
* ⚠️ `src/com/ccrbs/exception/` — User-defined exception classes for validation and error handling (`ResourceNotAvailableException.java`, `InvalidBookingException.java`, `UserNotFoundException.java`, `CCRBSException.java`).
* 🗃️ `sql/schema.sql` — DDL tables and seed data for bulk initialization.
* 📦 `lib/sqlite-jdbc-3.42.0.0.jar` — Required SQLite JDBC driver.
* 🧾 `receipts/` — Directory where output receipt files (`.txt`, `.dat`) are exported.

---

## 👨‍💻 Author

**Made by:** Shivangi Barthwal 🎓  
**Platform:** VITyarthi ✨# Shivangibarthwal-Javaproject
