import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupManager {

    private static final String SOURCE_PATH = "data/students.txt";
    private static final String BACKUP_DIR  = "data/backup/";

    // Creates backup folder if it doesn't exist
    public static void initialize() {
        File dir = new File(BACKUP_DIR);
        if (!dir.exists()) dir.mkdirs();
        System.out.println("Backup folder ready: " + dir.getAbsolutePath());
    }

    // Creates a backup of students.txt using Buffered Streams
    public static void createBackup() {
        // Backup filename includes timestamp so you never overwrite old backups
        String timestamp  = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupPath = BACKUP_DIR + "students_backup_" + timestamp + ".txt";

        File source = new File(SOURCE_PATH);

        if (!source.exists() || source.length() == 0) {
            System.out.println("No data to backup yet!");
            return;
        }

        // BufferedReader reads chunks at a time — faster than reading char by char
        try (BufferedReader reader = new BufferedReader(new FileReader(SOURCE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupPath))) {

            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine(); // adds line break after each student
                count++;
            }

            System.out.println("Backup created successfully!");
            System.out.println("Location : " + backupPath);
            System.out.println("Students backed up: " + count);

        } catch (IOException e) {
            System.out.println("Error creating backup: " + e.getMessage());
        }
    }

    // Shows all existing backup files
    public static void listBackups() {
        File dir = new File(BACKUP_DIR);
        File[] backups = dir.listFiles();

        if (backups == null || backups.length == 0) {
            System.out.println("No backups found!");
            return;
        }

        System.out.println("\n--- Available Backups ---");
        for (File f : backups) {
            System.out.println("Name : " + f.getName());
            System.out.println("Size : " + f.length() + " bytes");
            System.out.println("Date : " + new Date(f.lastModified()));
            System.out.println("---");
        }
    }
}