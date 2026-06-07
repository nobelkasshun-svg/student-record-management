import java.io.*;
import java.util.*;

public class FileManager {

    private static final String FILE_PATH = "data/students.txt";

    // Creates the data folder and file if they don't exist
    public static void initialize() {
        try {
            File dir = new File("data");
            if (!dir.exists()) dir.mkdirs();

            File file = new File(FILE_PATH);
            if (!file.exists()) file.createNewFile();

            System.out.println("Text file ready: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error initializing file: " + e.getMessage());
        }
    }

    // Reads all students from the text file and returns them as a list
    public static List<Student> loadAll() {
        List<Student> students = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    students.add(Student.fromFileString(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return students;
    }

    // Writes the entire list of students back to the file
    public static void saveAll(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Student s : students) {
                writer.println(s.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    // ADD — loads list, adds new student, saves back
    public static void addStudent(Student student) {
        List<Student> students = loadAll();
        students.add(student);
        saveAll(students);
        System.out.println("Student added successfully!");
    }

    // SEARCH — loops through list looking for matching ID
    public static Student searchById(String id) {
        List<Student> students = loadAll();
        for (Student s : students) {
            if (s.getStudentId().equals(id)) {
                return s;
            }
        }
        return null; // not found
    }

    // UPDATE — finds student by ID and replaces their info
    public static boolean updateStudent(String id, String newName, String newDept, double newGpa) {
        List<Student> students = loadAll();
        for (Student s : students) {
            if (s.getStudentId().equals(id)) {
                s.setName(newName);
                s.setDepartment(newDept);
                s.setGpa(newGpa);
                saveAll(students);
                System.out.println("Student updated successfully!");
                return true;
            }
        }
        System.out.println("Student not found!");
        return false;
    }

    // DELETE — removes student with matching ID from list
    public static boolean deleteStudent(String id) {
        List<Student> students = loadAll();
        boolean removed = students.removeIf(s -> s.getStudentId().equals(id));
        if (removed) {
            saveAll(students);
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
        return removed;
    }

    // DISPLAY ALL — prints every student
    public static void displayAll() {
        List<Student> students = loadAll();
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        System.out.println("\n===== ALL STUDENTS =====");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("========================\n");
    }

    // Shows file properties
    public static void showFileProperties() {
        File file = new File(FILE_PATH);
        System.out.println("\n--- File Properties ---");
        System.out.println("Name      : " + file.getName());
        System.out.println("Path      : " + file.getAbsolutePath());
        System.out.println("Size      : " + file.length() + " bytes");
        System.out.println("Last Modified: " + new Date(file.lastModified()));
        System.out.println("-----------------------\n");
    }
}