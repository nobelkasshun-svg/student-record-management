import java.io.*;
import java.util.*;

public class BinaryFileManager {

    private static final String FILE_PATH = "data/students.dat";

    // Creates the binary file if it doesn't exist
    public static void initialize() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) file.createNewFile();
            System.out.println("Binary file ready: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error initializing binary file: " + e.getMessage());
        }
    }

    // Saves all students to binary file
    // Each student is written field by field in exact order
    public static void saveAll(List<Student> students) {
        try (DataOutputStream dos = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(FILE_PATH)))) {
            
            dos.writeInt(students.size()); // write count first
            for (Student s : students) {
                dos.writeUTF(s.getStudentId());
                dos.writeUTF(s.getName());
                dos.writeUTF(s.getDepartment());
                dos.writeDouble(s.getGpa());
            }
            System.out.println("Saved to binary file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving binary file: " + e.getMessage());
        }
    }

    // Reads all students from binary file
    // Must read in EXACT same order as saveAll wrote them
    public static List<Student> loadAll() {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists() || file.length() == 0) return students;
        
        try (DataInputStream dis = new DataInputStream(
                new BufferedInputStream(new FileInputStream(FILE_PATH)))) {
            
            int count = dis.readInt(); // read count first
            for (int i = 0; i < count; i++) {
                String id   = dis.readUTF();
                String name = dis.readUTF();
                String dept = dis.readUTF();
                double gpa  = dis.readDouble();
                students.add(new Student(id, name, dept, gpa));
            }
        } catch (IOException e) {
            System.out.println("Error reading binary file: " + e.getMessage());
        }
        return students;
    }

    // Syncs binary file with the text file data
    public static void syncFromTextFile() {
        List<Student> students = FileManager.loadAll();
        saveAll(students);
        System.out.println("Binary file synced with text file!");
    }

    // Display all students from binary file
    public static void displayAll() {
        List<Student> students = loadAll();
        if (students.isEmpty()) {
            System.out.println("No students in binary file!");
            return;
        }
        System.out.println("\n===== STUDENTS (Binary File) =====");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("==================================\n");
    }
}