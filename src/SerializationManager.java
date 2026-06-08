import java.io.*;
import java.util.*;

public class SerializationManager {

    private static final String FILE_PATH = "data/students.ser";

    // Creates the serialization file if it doesn't exist
    public static void initialize() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) file.createNewFile();
            System.out.println("Serialization file ready: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error initializing serialization file: " + e.getMessage());
        }
    }

    // Saves entire list of Student objects directly to file
    // Java converts the whole object automatically
    public static void saveAll(List<Student> students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(FILE_PATH)))) {
            
            oos.writeObject(students); // writes the whole list in one shot
            System.out.println("Saved to serialization file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving serialization file: " + e.getMessage());
        }
    }

    // Loads entire list of Student objects directly from file
    @SuppressWarnings("unchecked")
    public static List<Student> loadAll() {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists() || file.length() == 0) return students;

        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(FILE_PATH)))) {
            
            students = (List<Student>) ois.readObject(); // reads whole list in one shot
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading serialization file: " + e.getMessage());
        }
        return students;
    }

    // Syncs serialization file with the text file data
    public static void syncFromTextFile() {
        List<Student> students = FileManager.loadAll();
        saveAll(students);
        System.out.println("Serialization file synced with text file!");
    }

    // Display all students from serialization file
    public static void displayAll() {
        List<Student> students = loadAll();
        if (students.isEmpty()) {
            System.out.println("No students in serialization file!");
            return;
        }
        System.out.println("\n===== STUDENTS (Serialization File) =====");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("=========================================\n");
    }
}