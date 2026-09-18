# 🏫 Concurrent Campus Resource Booking System (CCRBS)

![Java](https://img.shields.io/badge/Java-11+-ED8B00?style=flat&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat&logo=sqlite&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-API-blue?style=flat)
![License](https://img.shields.io/badge/License-MIT-blue.svg)

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [Problem Statement](#-problem-statement)
- [Key Features](#-key-features)
- [Detailed Description](#-detailed-description)
- [Project Structure](#-project-structure)
- [How It Works](#-how-it-works)
- [Core Java Concepts Demonstrated](#-core-java-concepts-demonstrated)
- [Technologies Used](#-technologies-used)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Usage Guide](#-usage-guide)
- [Testing Concurrent Bookings](#-testing-concurrent-bookings)
- [Screenshots](#-screenshots)
- [Credits](#-credits)
- [License](#-license)

---

## 🎯 Project Overview

The **Concurrent Campus Resource Booking System (CCRBS)** is a localized, console-based Java application designed to streamline the process of reserving shared campus resources at a university. It allows students, faculty, and administrators to view, filter, and book campus facilities such as lecture halls, laboratories, and sports facilities — all while safely handling multiple simultaneous booking requests through Java multithreading and synchronization.

This project is built entirely using **core Java** (no external web frameworks like Spring or Jakarta), making it an ideal academic demonstration of foundational Java concepts including Object-Oriented Programming, Collections, Multithreading, Exception Handling, JDBC database connectivity, and File I/O Streams.

### Who Is This For?

| User Role | Description |
|---|---|
| **Students** | Browse available campus resources, make reservations for study rooms, labs, or sports facilities, view and cancel their bookings |
| **Faculty** | Reserve lecture halls, laboratories, and meeting spaces for academic purposes |
| **Administrators** | Manage all users and bookings system-wide, simulate concurrent booking scenarios for testing |

---

## 🧩 Problem Statement

In a typical university environment, multiple departments and individuals compete for a limited set of shared resources — lecture halls, computer labs, physics labs, basketball courts, and more. Without a centralized system, conflicts arise:

- **Double Bookings**: Two professors may unknowingly reserve the same lecture hall for the same time slot.
- **No Real-Time Visibility**: Users cannot see which resources are currently available vs. already reserved.
- **Manual Processes**: Paper-based or spreadsheet-based booking systems are error-prone and lack concurrency control.

CCRBS solves these problems by providing a thread-safe, database-backed reservation system where concurrent booking attempts are serialized through Java's `synchronized` mechanism, ensuring **only one user can reserve a specific resource at any given time**.

---

## ✨ Key Features

### 1. User Management Module
- Role-based registration system with three user types: Student, Faculty, and Admin
- Secure login and authentication via JDBC database queries
- Full CRUD operations on user profiles (Create, Read, Update, Delete)
- Java Enums for type-safe role management

### 2. Resource Catalog Module
- View all available campus resources in a formatted table
- Filter resources by type (Lecture Hall, Laboratory, Sports Facility)
- Real-time availability checking for specific dates and time slots
- In-memory caching using `ArrayList` for fast repeated lookups

### 3. Concurrent Booking Module
- Process reservation requests with thread-safe synchronization
- Prevent double-booking via `synchronized` methods on the `BookingService`
- Concurrent booking simulation (5 threads racing for the same slot)
- Booking receipt generation using both byte and character I/O streams
- Booking cancellation with automatic status updates

---

## 📖 Detailed Description

### Architecture Overview

CCRBS follows a **layered architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────┐
│            PRESENTATION LAYER               │
│         Main.java (Console CLI)             │
│    Scanner input + formatted output         │
├─────────────────────────────────────────────┤
│             SERVICE LAYER                   │
│  UserService │ ResourceService │ BookingService │
│  Business logic, validation, concurrency    │
├─────────────────────────────────────────────┤
│           DATA ACCESS LAYER                 │
│    UserDAO   │  ResourceDAO  │  BookingDAO  │
│  JDBC PreparedStatements, SQL queries       │
├─────────────────────────────────────────────┤
│              MODEL LAYER                    │
│  User (Student/Faculty/Admin) │ Booking     │
│  CampusResource (LectureHall/Lab/Sports)    │
├─────────────────────────────────────────────┤
│          INFRASTRUCTURE LAYER               │
│  DatabaseConnection (Singleton) │ SQLite DB │
│  ReceiptWriter/Reader (I/O Streams)         │
└─────────────────────────────────────────────┘
```

### Object-Oriented Domain Model

The system uses a rich inheritance hierarchy to model real-world entities:

**User Hierarchy:**
```
         User (Base Class)
        /       |       \
   Student   Faculty    Admin
```
- `User` is the base class with common fields (id, name, email, password, role)
- Each subclass uses the `super()` keyword to invoke the parent constructor
- `getDisplayRole()` and `getDashboardHeader()` are overridden in each subclass (polymorphism)

**Resource Hierarchy:**
```
    «interface» Bookable
           |
    CampusResource (Abstract Class)
      /         |          \
LectureHall  Laboratory  SportsFacility
```
- `Bookable` is a Java interface defining the contract: `getResourceId()`, `getResourceName()`, `isAvailable()`, `getResourceType()`, `getResourceDetails()`
- `CampusResource` is an abstract class implementing `Bookable`, with `getResourceDetails()` left abstract for subclasses to implement
- Each concrete subclass adds type-specific properties (e.g., `hasProjector` for LectureHall, `labType` for Laboratory)

### Concurrency Mechanism

The most critical feature is preventing double-bookings when multiple users try to reserve the same resource simultaneously:

```
Thread-1 ─┐
Thread-2 ─┤
Thread-3 ─┼──► synchronized processBooking() ──► Only ONE gets through
Thread-4 ─┤        (mutual exclusion)
Thread-5 ─┘
```

1. Each booking request is wrapped in a `BookingThread` (extends `Thread`)
2. All threads call `BookingService.processBooking()`, which is a `synchronized` method
3. The `synchronized` keyword ensures only one thread executes the method at a time
4. The first thread checks availability, creates the booking, and returns success
5. Subsequent threads find the resource already booked and receive a `ResourceNotAvailableException`

### Data Persistence

All data is persisted in a SQLite database (`ccrbs.db`) using JDBC:
- **Users table**: Stores user credentials and roles
- **Resources table**: Stores campus resource details and availability
- **Bookings table**: Stores reservation records with foreign keys to users and resources
- The `DatabaseConnection` class implements the **Singleton pattern** to ensure a single shared database connection

### File I/O (Receipt Generation)

Upon successful booking, the system generates receipts demonstrating both I/O stream types:
- **Character-oriented streams** (`BufferedWriter` + `FileWriter`): Creates a human-readable `.txt` receipt file
- **Byte-oriented streams** (`DataOutputStream` + `FileOutputStream`): Creates a structured binary `.dat` receipt file

---

## 📁 Project Structure

```
CCRBS/
│
├── lib/                                        # External libraries
│   └── sqlite-jdbc-3.42.0.0.jar               # SQLite JDBC driver
│
├── sql/                                        # Database scripts
│   └── schema.sql                             # DDL tables + seed data (6 resources, 3 users)
│
├── src/                                        # Java source code
│   └── com/
│       └── ccrbs/
│           │
│           ├── Main.java                       # 🚀 Application entry point (CLI menus)
│           │
│           ├── model/                          # 📦 Domain entities (11 files)
│           │   ├── UserRole.java               #   Enum: STUDENT, FACULTY, ADMIN
│           │   ├── User.java                   #   Base class with getDisplayRole()
│           │   ├── Student.java                #   extends User (department, studentId)
│           │   ├── Faculty.java                #   extends User (designation, department)
│           │   ├── Admin.java                  #   extends User (adminLevel)
│           │   ├── Bookable.java               #   Interface for bookable resources
│           │   ├── CampusResource.java         #   Abstract class implementing Bookable
│           │   ├── LectureHall.java            #   Concrete: projector, whiteboard
│           │   ├── Laboratory.java             #   Concrete: labType, safetyEquipment
│           │   ├── SportsFacility.java         #   Concrete: sportType, indoor/outdoor
│           │   └── Booking.java                #   Booking entity with status tracking
│           │
│           ├── dao/                            # 🗄️ Data Access Objects (3 files)
│           │   ├── UserDAO.java                #   User CRUD via JDBC PreparedStatements
│           │   ├── ResourceDAO.java            #   Resource CRUD + ArrayList caching
│           │   └── BookingDAO.java             #   Booking CRUD + JOIN queries
│           │
│           ├── service/                        # ⚙️ Business Logic & Concurrency (4 files)
│           │   ├── UserService.java            #   Registration, login, validation
│           │   ├── ResourceService.java        #   Filtering, availability, cache mgmt
│           │   ├── BookingService.java         #   ★ synchronized processBooking()
│           │   └── BookingThread.java          #   ★ extends Thread (concurrency demo)
│           │
│           ├── exception/                      # ❌ Custom Exceptions (4 files)
│           │   ├── CCRBSException.java         #   Base exception (extends Exception)
│           │   ├── ResourceNotAvailableException.java  # Double-booking errors
│           │   ├── InvalidBookingException.java        # Input validation errors
│           │   └── UserNotFoundException.java          # Authentication failures
│           │
│           └── util/                           # 🔧 Utilities (3 files)
│               ├── DatabaseConnection.java     #   Singleton JDBC connection manager
│               ├── ReceiptWriter.java          #   Byte + character stream output
│               └── ReceiptReader.java          #   Byte + character stream input
│
├── out/                                        # Compiled .class files
├── receipts/                                   # Generated booking receipts
├── ccrbs.db                                    # SQLite database file (auto-created)
├── README.md                                   # This file
└── statement.md                                # Problem statement document
```

**Total: 25 Java source files across 5 packages + 1 SQL script + 2 documentation files**

---

## ⚙️ How It Works

### Application Flow

```
Start Application
       │
       ▼
 Initialize Database
 (Singleton connection + schema.sql)
       │
       ▼
 ┌─────────────────┐
 │   MAIN MENU     │
 │ [1] Register    │
 │ [2] Login       │
 │ [3] Exit        │
 └────────┬────────┘
          │
    [1] Register ──► Enter name, email, password, role ──► Save to DB
          │
    [2] Login ──► Enter email + password ──► Authenticate via JDBC
          │
          ▼
 ┌──────────────────────────┐
 │   ROLE-BASED DASHBOARD   │
 │                          │
 │ [1]  View All Resources  │──► SELECT * FROM resources (cached in ArrayList)
 │ [2]  Filter by Type      │──► Filter ArrayList by type string
 │ [3]  Check Availability  │──► Query bookings table for conflicts
 │ [4]  Book a Resource     │──► synchronized processBooking()
 │ [5]  View My Bookings    │──► SELECT with JOIN (user + resource names)
 │ [6]  Cancel Booking      │──► UPDATE status = 'CANCELLED'
 │ [7]  View Receipt        │──► Read .txt file via BufferedReader
 │                          │
 │ --- Admin Only ---       │
 │ [8]  View All Users      │──► SELECT * FROM users
 │ [9]  View All Bookings   │──► SELECT with JOINs
 │ [10] Simulate Concurrent │──► Spawn 5 BookingThreads
 │ [0]  Logout              │
 └──────────────────────────┘
```

### Booking Workflow (Step by Step)

1. **User selects "Book a Resource"** from the dashboard
2. **System prompts** for Resource ID, Date (YYYY-MM-DD), and Time Slot (e.g., "09:00-10:00")
3. **BookingService.processBooking()** is called (this method is `synchronized`):
   - Validates that date and time slot are not empty
   - Queries the `bookings` table to check if a `CONFIRMED` booking already exists for that resource + date + slot
   - If already booked → throws `ResourceNotAvailableException`
   - If available → creates a `Booking` object, inserts into database, returns the booking
4. **Receipt generation**: A `.txt` receipt file is saved in the `receipts/` folder using character-oriented I/O streams
5. **Confirmation** is displayed to the user with the booking details

### Thread Lifecycle (Concurrent Booking Simulation)

```
main thread
    │
    ├── new BookingThread("Thread-1", ...) ──► CREATED (NEW state)
    ├── thread1.start()                    ──► RUNNABLE state
    │       └── run() { processBooking() } ──► BLOCKED (waiting for synchronized lock)
    │                                          or RUNNING (acquired lock)
    ├── new BookingThread("Thread-2", ...) ──► CREATED
    ├── thread2.start()                    ──► RUNNABLE / BLOCKED
    │   ...
    ├── thread1.join()                     ──► main waits for thread1 to finish
    ├── thread2.join()                     ──► main waits for thread2 to finish
    │   ...
    └── Print results                      ──► Show which thread succeeded
```

---

## 🧠 Core Java Concepts Demonstrated

| # | Concept | Implementation | File(s) |
|---|---|---|---|
| 1 | **Classes & Objects** | Domain entities with fields, constructors, getters/setters | All model classes |
| 2 | **Inheritance (`extends`)** | `Student`, `Faculty`, `Admin` extend `User`; resource subclasses extend `CampusResource` | `Student.java`, `Faculty.java`, `Admin.java`, `LectureHall.java`, etc. |
| 3 | **`super` keyword** | Subclass constructors explicitly call `super(...)` to initialize parent fields | All subclasses |
| 4 | **Method Overriding** | `getDisplayRole()`, `getDashboardHeader()`, `getResourceDetails()`, `getResourceType()` | All subclasses |
| 5 | **Polymorphism** | `CampusResource` reference holds `LectureHall`/`Laboratory`/`SportsFacility` objects; `Bookable` interface used generically | `ResourceDAO.mapResultSetToResource()` |
| 6 | **Interfaces** | `Bookable` defines contract for bookable resources | `Bookable.java` |
| 7 | **Abstract Classes** | `CampusResource` with abstract `getResourceDetails()` | `CampusResource.java` |
| 8 | **Enums** | `UserRole` with `STUDENT`, `FACULTY`, `ADMIN` values and `getDisplayName()` | `UserRole.java` |
| 9 | **`ArrayList` (Collections)** | Resource caching in `ResourceDAO`, filtering by type | `ResourceDAO.java` |
| 10 | **`Thread` class (`extends Thread`)** | `BookingThread` represents a concurrent booking request | `BookingThread.java` |
| 11 | **Thread Lifecycle** | `start()`, `run()`, `join()` demonstrated in simulation | `Main.java` (option 10) |
| 12 | **`synchronized` methods** | `processBooking()` ensures mutual exclusion | `BookingService.java` |
| 13 | **Custom Exceptions** | 4-class hierarchy extending `Exception` | `exception/` package |
| 14 | **`try-catch-finally`** | All DAO methods wrap SQL operations in try-catch blocks | All DAO classes |
| 15 | **`throw` / `throws`** | Service methods declare and throw custom exceptions | All service classes |
| 16 | **Character I/O Streams** | `BufferedWriter` + `FileWriter` for text receipts | `ReceiptWriter.java` |
| 17 | **Byte I/O Streams** | `DataOutputStream` + `FileOutputStream` for binary receipts | `ReceiptWriter.java` |
| 18 | **JDBC API** | `DriverManager`, `Connection`, `PreparedStatement`, `ResultSet` | All DAO classes |
| 19 | **Singleton Pattern** | `DatabaseConnection` with `private` constructor and `synchronized getInstance()` | `DatabaseConnection.java` |

---

## 🛠️ Technologies Used

| Technology | Purpose | Version |
|---|---|---|
| **Java** | Core programming language | JDK 11+ (tested on JDK 21) |
| **SQLite** | Lightweight relational database (file-based, zero-config) | Via JDBC driver |
| **JDBC API** | Database connectivity and CRUD operations | `java.sql.*` |
| **SQLite JDBC Driver** | JDBC driver for SQLite | `sqlite-jdbc-3.42.0.0.jar` |

---

## 📋 Prerequisites

- **Java Development Kit (JDK) 11** or higher installed
  - Verify: `javac -version` should show 11+
- **SQLite JDBC Driver** JAR file (downloaded during setup)

> **Note**: No build tools (Maven, Gradle) are required. The project compiles with `javac` directly.

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/ccrbs.git
cd ccrbs
```

### Step 2: Download the SQLite JDBC Driver
Download `sqlite-jdbc-3.42.0.0.jar` from [Maven Central](https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar) and place it in the `lib/` directory:
```bash
mkdir lib
# Download the JAR into lib/ (or use browser)
curl -o lib/sqlite-jdbc-3.42.0.0.jar https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar
```

### Step 3: Compile the Source Code
```bash
mkdir out
javac -cp "lib/sqlite-jdbc-3.42.0.0.jar" -d out -sourcepath src src/com/ccrbs/Main.java src/com/ccrbs/model/*.java src/com/ccrbs/dao/*.java src/com/ccrbs/service/*.java src/com/ccrbs/exception/*.java src/com/ccrbs/util/*.java
```

> **Windows users**: Use semicolons (`;`) instead of colons (`:`) in the classpath:
> ```cmd
> javac -cp "lib\sqlite-jdbc-3.42.0.0.jar" -d out -sourcepath src src\com\ccrbs\Main.java src\com\ccrbs\model\*.java src\com\ccrbs\dao\*.java src\com\ccrbs\service\*.java src\com\ccrbs\exception\*.java src\com\ccrbs\util\*.java
> ```

### Step 4: Run the Application
```bash
# Linux / macOS
java -cp "out:lib/sqlite-jdbc-3.42.0.0.jar" com.ccrbs.Main

# Windows
java -cp "out;lib\sqlite-jdbc-3.42.0.0.jar" com.ccrbs.Main
```

On first run, the application will:
1. Create the `ccrbs.db` SQLite database file
2. Execute `sql/schema.sql` to create tables and insert seed data
3. Display the main menu

---

## 📘 Usage Guide

### 1. Register a New User
```
--- Main Menu ---
[1] Register
[2] Login
[3] Exit
Select an option: 1

Enter your name: John Doe
Enter your email: john@university.edu
Enter your password: mypassword
Select role - [1] Student [2] Faculty [3] Admin: 1

Registration successful! Your User ID is: 4
```

### 2. Login
```
Select an option: 2
Enter email: john@university.edu
Enter password: mypassword

Welcome, John Doe!
Role: Student
```

### 3. View and Book Resources
```
--- Dashboard ---
[1] View All Resources
Select an option: 1

ID  | Name                | Type           | Location        | Available
----|---------------------|----------------|-----------------|----------
1   | Main Auditorium     | LECTURE_HALL    | Building A      | Yes
2   | Room 201            | LECTURE_HALL    | Building B      | Yes
3   | Computer Lab 1      | LABORATORY     | Building C      | Yes
...

[4] Book a Resource
Select an option: 4
Enter Resource ID: 1
Enter Date (YYYY-MM-DD): 2026-09-18
Enter Time Slot (e.g., 09:00-10:00): 10:00-11:00

Booking confirmed! Booking ID: 1
Receipt saved to: receipts/receipt_1.txt
```

---

## 🧪 Testing Concurrent Bookings

This is the **key demonstration** of multithreading and synchronization:

1. Login as an **Admin** (seeded credentials: `admin@campus.edu` / `admin123`)
2. Select option **[10] Simulate Concurrent Bookings**
3. Enter a Resource ID, Date, and Time Slot
4. The system spawns **5 threads** simultaneously trying to book the **same** resource for the **same** time slot

### Expected Output:
```
Launching 5 concurrent booking threads...

BookingThread-1 attempting to book resource 1
BookingThread-2 attempting to book resource 1
BookingThread-3 attempting to book resource 1
BookingThread-1 successfully booked resource 1 for 2026-09-18 10:00-11:00
BookingThread-4 attempting to book resource 1
BookingThread-2 failed: [CCRBS Error] Resource 1 is not available for time slot 10:00-11:00
BookingThread-5 attempting to book resource 1
BookingThread-3 failed: [CCRBS Error] Resource 1 is not available for time slot 10:00-11:00
BookingThread-4 failed: [CCRBS Error] Resource 1 is not available for time slot 10:00-11:00
BookingThread-5 failed: [CCRBS Error] Resource 1 is not available for time slot 10:00-11:00

--- Simulation Results ---
BookingThread-1: ✓ SUCCESS (Booking ID: 5)
BookingThread-2: ✗ FAILED
BookingThread-3: ✗ FAILED
BookingThread-4: ✗ FAILED
BookingThread-5: ✗ FAILED

Only 1 out of 5 threads succeeded — synchronized booking works!
```

This proves that the `synchronized` keyword on `processBooking()` ensures **mutual exclusion** — only one thread can execute the booking logic at a time, preventing data corruption and double-bookings.

---

## 📸 Screenshots

<!-- Replace these placeholders with actual screenshots -->
| Screen | Preview |
|---|---|
| Main Menu | ![Main Menu](screenshots/main_menu.png) |
| Resource Listing | ![Resources](screenshots/resource_list.png) |
| Booking Confirmation | ![Booking](screenshots/booking_confirmation.png) |
| Concurrent Simulation | ![Concurrency](screenshots/concurrent_demo.png) |
| Receipt Output | ![Receipt](screenshots/receipt_output.png) |

---

## 👥 Credits

Developed as an academic demonstration of core Java concepts for university coursework.

**Core Java Concepts Covered:**
- Object-Oriented Programming (Classes, Interfaces, Inheritance, Polymorphism)
- Java Collections Framework (List, ArrayList)
- Multithreading & Synchronization (Thread, synchronized)
- Exception Handling (Custom exceptions, try-catch, throw/throws)
- I/O Streams (Byte-oriented & Character-oriented)
- JDBC Database Connectivity

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
