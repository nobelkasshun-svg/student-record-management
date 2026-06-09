# Student Record Management System

## Overview

The Student Record Management System is a Java-based console application developed
as part of a Home Test assignment on Object-Oriented Programming and Java File I/O.
The system allows users to manage student records efficiently through a menu-driven
interface. It demonstrates the use of multiple file storage techniques, buffered
streams, exception handling, and object serialization in Java.

---

## Objectives

- Apply Object-Oriented Programming principles in a real-world scenario
- Demonstrate the use of Java File I/O with different stream types
- Implement CRUD operations (Create, Read, Update, Delete) on persistent data
- Generate statistical reports from stored student records
- Use the Java File class to manage directories and display file metadata
- Implement data backup using Buffered Streams

---

## Project Structure

```
student-record-management/
│
├── src/
│   ├── Student.java
│   ├── FileManager.java
│   ├── BinaryFileManager.java
│   ├── SerializationManager.java
│   ├── ReportGenerator.java
│   ├── BackupManager.java
│   └── Main.java
│
├── data/
│   ├── students.txt
│   ├── students.dat
│   ├── students.ser
│   └── backup/
│       └── students_backup_[timestamp].txt
│
├── README.md
└── .gitignore
```

---

## Class Descriptions

### 1. Student.java
The core data model of the entire system. Represents a single student entity
with four attributes: Student ID, Name, Department, and GPA.

**Key Design Decisions:**
- All fields are declared `private` to enforce **Encapsulation**
- Public getters and setters provide controlled access to private fields
- Implements `Serializable` interface to allow Java to convert Student objects
  into a byte stream for file storage
- `serialVersionUID` is defined to maintain version consistency during
  serialization and deserialization
- `toFileString()` converts a Student object into a comma-separated string
  for text file storage
- `fromFileString()` is a static factory method that parses a comma-separated
  string and reconstructs a Student object

```java
// Example of how a student is stored in the text file
S001,Abebe Kebede,Computer Science,3.8
S002,Tigist Haile,Engineering,3.5
```

---

### 2. FileManager.java
Handles all text file operations using `Scanner` for reading and `PrintWriter`
for writing. This is the primary storage manager of the system.

**Key Responsibilities:**
- Automatically creates the `data/` directory and `students.txt` file on first run
- Loads all student records from the text file into a `List<Student>`
- Saves the entire list back to the text file after every operation
- Implements Add, Search, Update, Delete, and Display All operations
- Displays file metadata using the `File` class

**Streams Used:**
- `Scanner` — reads text file line by line
- `PrintWriter` wrapped in `FileWriter` — writes formatted text to file
- `File` class — creates directories, checks existence, reads properties

---

### 3. BinaryFileManager.java
Handles binary file operations using `DataOutputStream` for writing and
`DataInputStream` for reading. Data is stored in binary format which is
not human-readable but is more compact and efficient.

**Key Design Decisions:**
- Each student's fields are written in a strict order: ID, Name, Department, GPA
- The total number of students is written first as an integer so the reader
  knows exactly how many records to expect
- Fields must be read back in the **exact same order** they were written
- Uses `BufferedOutputStream` and `BufferedInputStream` to improve performance

**Streams Used:**
- `DataOutputStream` → `BufferedOutputStream` → `FileOutputStream`
- `DataInputStream` → `BufferedInputStream` → `FileInputStream`

---

### 4. SerializationManager.java
Handles object serialization using `ObjectOutputStream` for writing and
`ObjectInputStream` for reading. This is the simplest storage method because
Java handles the entire conversion automatically.

**Key Design Decisions:**
- The entire `List<Student>` is written as a single object in one operation
- The entire list is read back in one operation
- Requires `Student` to implement `Serializable`
- Uses `@SuppressWarnings("unchecked")` to suppress the unavoidable cast warning
  when reading the object back

**Streams Used:**
- `ObjectOutputStream` → `BufferedOutputStream` → `FileOutputStream`
- `ObjectInputStream` → `BufferedInputStream` → `FileInputStream`

---

### 5. ReportGenerator.java
A pure logic class that generates statistical reports from student data.
It has no file I/O of its own — it reads data through `FileManager` and
performs calculations.

**Statistics Generated:**
- Total number of students
- Student with the highest GPA and their name
- Student with the lowest GPA and their name
- Average GPA of all students
- Per-department breakdown showing student count and average GPA

**Key Concept Used:**
- `HashMap<String, List<Double>>` groups GPA values by department for
  the department breakdown report

---

### 6. BackupManager.java
Creates timestamped backup copies of the main text file using Buffered Streams.
Each backup has a unique filename based on the date and time it was created.

**Key Design Decisions:**
- `SimpleDateFormat` generates a timestamp in the format `yyyyMMdd_HHmmss`
- Each backup is saved as `students_backup_20260606_005501.txt` for example
- Old backups are never overwritten because every backup has a unique timestamp
- `BufferedReader` and `BufferedWriter` read and write data in chunks,
  making the backup operation faster than character-by-character copying

**Streams Used:**
- `BufferedReader` → `FileReader` — reads source file efficiently
- `BufferedWriter` → `FileWriter` — writes to backup file efficiently

---

### 7. Main.java
The entry point of the application. Contains the menu loop and connects all
other classes together.

**Key Responsibilities:**
- Calls `initialize()` on all managers when the program starts
- Displays a numbered menu and reads user input in a loop
- Uses a `switch` statement with arrow syntax to handle each menu option
- Contains helper methods for safe integer and double input parsing
- Validates GPA range (0.0 to 4.0) before saving
- Asks for confirmation before deleting a student

---

## File Storage Comparison

| Feature | Text File | Binary File | Serialization |
|---|---|---|---|
| Human readable | Yes | No | No |
| File size | Large | Small | Medium |
| Read/Write speed | Slow | Fast | Medium |
| Easy to implement | Medium | Hard | Easy |
| Handles objects directly | No | No | Yes |
| Stream used | Scanner / PrintWriter | DataInputStream / DataOutputStream | ObjectInputStream / ObjectOutputStream |

---

## OOP Concepts Demonstrated

| Concept | Where it is used |
|---|---|
| **Encapsulation** | Private fields in `Student.java` with public getters/setters |
| **Abstraction** | Each class hides its internal implementation |
| **Single Responsibility** | Each class does one job only |
| **Static Methods** | All manager methods are static for easy access |
| **Serialization** | `Student` implements `Serializable` |
| **Exception Handling** | All file operations use try-catch blocks |

---

## Exception Handling Strategy

Every file operation in this system is wrapped in a try-catch block to prevent
the program from crashing. The following exceptions are handled:

| Exception | Where | Cause |
|---|---|---|
| `IOException` | All file managers | File not found, permission denied, disk full |
| `FileNotFoundException` | FileManager | Text file deleted while program runs |
| `ClassNotFoundException` | SerializationManager | Student class version mismatch |
| `NumberFormatException` | Main.java | User enters text instead of a number |

---

## How to Run

**Requirements:**
- Java JDK 17 or higher
- Any terminal or command prompt

**Steps:**

```bash
# Clone the repository
git clone https://github.com/nobelkasshun-svg/student-record-management.git

# Navigate to the src folder
cd student-record-management/src

# Compile all Java files at once
javac *.java

# Run the program
java Main
```

---

## Sample Program Output

```
Initializing Student Record Management System...
Text file ready: C:\...\data\students.txt
Binary file ready: C:\...\data\students.dat
Serialization file ready: C:\...\data\students.ser
Backup folder ready: C:\...\data\backup
System Ready!

========================================
   STUDENT RECORD MANAGEMENT SYSTEM
========================================
1. Add Student
2. Search Student by ID
3. Update Student
4. Delete Student
5. Display All Students
6. Generate Report
7. Create Backup
8. Sync All Files
9. Show File Properties
0. Exit
========================================

========================================
         STUDENT RECORD REPORT
========================================
Total Students  : 4
Highest GPA     : 3.8 (Abebe Kebede)
Lowest GPA      : 2.9 (Dawit Bekele)
Average GPA     : 3.35
========================================

--- GPA by Department ---
Computer Science    : 2 students | Avg GPA: 3.35
Engineering         : 1 students | Avg GPA: 3.50
Business            : 1 students | Avg GPA: 3.20
-------------------------
```

---

## Author

**Name:** Bitaniya Kasshun
**Student ID:** 0236/25
**Department:** Software Engineering
**Institution:** BITS College
**Submission Date:** June 18, 2026
**Presentation Date:** June 19, 2026
