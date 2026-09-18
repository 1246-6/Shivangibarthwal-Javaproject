# Problem Statement: Campus Resource Booking System (CCRBS)

## The Problem
Managing resources across a university campus—such as lecture halls, computer labs, projectors, and study spaces—often involves disjointed systems or manual tracking. Students struggle to find available study rooms, faculty face double-booked lecture halls, and administrators lack a centralized view of resource utilization.

As campus activities scale, the risk of conflicting reservations increases. Without a centralized, thread-safe booking mechanism, concurrent requests for the same room at the same time can result in erroneous double-bookings, causing logistical nightmares and operational inefficiency.

## Scope of the Project
The Campus Resource Booking System (CCRBS) provides a centralized, console-based Java application to streamline the reservation of campus assets. The system ensures robust data persistence via SQLite, thread-safe booking mechanisms to prevent race conditions, and role-based access control to distinguish between varying levels of user authorization.

## Target Users

*   **Students:** Can view available resources, make bookings for study spaces or equipment, and view/cancel their own reservations.
*   **Faculty:** Can book lecture halls and specialized labs for classes, with priority access considerations where applicable.
*   **Administrators:** Have full oversight. They can view all users, monitor all bookings across the campus, manage system data, and run concurrency simulations for testing.

## High-Level Features

| Feature Module | Description |
| :--- | :--- |
| **Authentication & Authorization** | User registration, login, and role-based dashboards (Student, Faculty, Admin). |
| **Resource Catalog** | View all campus resources, filter by type, and check real-time availability for specific dates/slots. |
| **Concurrency-Safe Booking** | Synchronized processing of reservations ensuring that simultaneous requests for the same resource do not result in double-booking. |
| **Receipt Generation** | Automated generation of booking receipts demonstrating both character-oriented (text) and byte-oriented (binary) I/O file streams. |

## Technical Constraints / Academic Requirements
This project serves as a comprehensive demonstration of Core Java concepts:
*   Must utilize **Object-Oriented Programming** principles (inheritance, abstract classes, encapsulation).
*   Must implement custom **Exception Handling** for domain-specific errors (e.g., `ResourceNotAvailableException`).
*   Must use **Multithreading** with `Thread` or `Runnable` and `synchronized` blocks to handle concurrent bookings.
*   Must perform **File I/O** using both Byte Streams (`FileInputStream`/`FileOutputStream`) and Character Streams (`FileReader`/`FileWriter`).
*   Must interact with an **SQLite** database using JDBC for data persistence.

## Expected Outcomes
A fully functional, console-driven Java application that successfully mitigates double-booking scenarios under concurrent load, generates file-based receipts, and provides a structured, role-based user experience for campus resource management.
