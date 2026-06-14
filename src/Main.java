import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Initialize all files and folders when program starts
        System.out.println("Initializing Student Record Management System...");
        FileManager.initialize();
        BinaryFileManager.initialize();
        SerializationManager.initialize();
        BackupManager.initialize();
        System.out.println("System Ready!\n");

        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1  -> addStudent();
                case 2  -> searchStudent();
                case 3  -> updateStudent();
                case 4  -> deleteStudent();
                case 5  -> FileManager.displayAll();
                case 6  -> ReportGenerator.generateReport();
                case 7  -> createBackup();
                case 8  -> syncAllFiles();
                case 9  -> FileManager.showFileProperties();
                case 0  -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }

        scanner.close();
    }

    // Prints the main menu
    private static void printMenu() {
        System.out.println("========================================");
        System.out.println("   STUDENT RECORD MANAGEMENT SYSTEM    ");
        System.out.println("========================================");
        System.out.println("1. Add Student");
        System.out.println("2. Search Student by ID");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Display All Students");
        System.out.println("6. Generate Report");
        System.out.println("7. Create Backup");
        System.out.println("8. Sync All Files");
        System.out.println("9. Show File Properties");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    // ADD STUDENT
    private static void addStudent() {
        System.out.println("\n--- Add New Student ---");
        System.out.print("Enter Student ID   : ");
        String id = scanner.nextLine().trim();

        // Check if ID already exists
        if (FileManager.searchById(id) != null) {
            System.out.println("Student with this ID already exists!");
            return;
        }

        System.out.print("Enter Name         : ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Department   : ");
        String dept = scanner.nextLine().trim();

        double gpa = getDoubleInput("Enter GPA (0.0-4.0): ");

        if (gpa < 0.0 || gpa > 4.0) {
            System.out.println("Invalid GPA! Must be between 0.0 and 4.0");
            return;
        }

        Student student = new Student(id, name, dept, gpa);
        FileManager.addStudent(student);
    }

    // SEARCH STUDENT
    private static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine().trim();

        Student found = FileManager.searchById(id);

        if (found != null) {
            System.out.println("\nStudent Found:");
            System.out.println(found);
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }

    // UPDATE STUDENT
    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        System.out.print("Enter Student ID to update: ");
        String id = scanner.nextLine().trim();

        Student existing = FileManager.searchById(id);
        if (existing == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Current Info: " + existing);
        System.out.println("Enter new details (press Enter to keep current value):");

        System.out.print("New Name [" + existing.getName() + "]: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = existing.getName();

        System.out.print("New Department [" + existing.getDepartment() + "]: ");
        String dept = scanner.nextLine().trim();
        if (dept.isEmpty()) dept = existing.getDepartment();

        System.out.print("New GPA [" + existing.getGpa() + "]: ");
        String gpaInput = scanner.nextLine().trim();
        double gpa = gpaInput.isEmpty() ? existing.getGpa() : Double.parseDouble(gpaInput);

        if (gpa < 0.0 || gpa > 4.0) {
            System.out.println("Invalid GPA! Must be between 0.0 and 4.0");
            return;
        }

        FileManager.updateStudent(id, name, dept, gpa);
    }

    // DELETE STUDENT
    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        System.out.print("Enter Student ID to delete: ");
        String id = scanner.nextLine().trim();

        Student existing = FileManager.searchById(id);
        if (existing == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.println("Student to delete: " + existing);
        System.out.print("Are you sure? (yes/no): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            FileManager.deleteStudent(id);
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // CREATE BACKUP
    private static void createBackup() {
        BackupManager.createBackup();
        BackupManager.listBackups();
    }

    // SYNC — updates binary and serialization files to match text file
    private static void syncAllFiles() {
        System.out.println("\nSyncing all files...");
        BinaryFileManager.syncFromTextFile();
        SerializationManager.syncFromTextFile();
        System.out.println("All files synced!");
    }

    // Helper — reads an integer safely
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    // Helper — reads a double safely
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}