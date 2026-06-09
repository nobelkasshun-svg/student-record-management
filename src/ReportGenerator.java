import java.util.*;

public class ReportGenerator {

    // Takes the list of students and prints full report
    public static void generateReport() {
        List<Student> students = FileManager.loadAll();

        if (students.isEmpty()) {
            System.out.println("No students found to generate report!");
            return;
        }

        int total = students.size();
        double highest = students.get(0).getGpa();
        double lowest  = students.get(0).getGpa();
        double sum     = 0;

        String highestName = students.get(0).getName();
        String lowestName  = students.get(0).getName();

        // Loop through every student to find highest, lowest, sum
        for (Student s : students) {
            double gpa = s.getGpa();
            sum += gpa;

            if (gpa > highest) {
                highest     = gpa;
                highestName = s.getName();
            }

            if (gpa < lowest) {
                lowest     = gpa;
                lowestName = s.getName();
            }
        }

        double average = sum / total;

        // Print the report
        System.out.println("\n========================================");
        System.out.println("         STUDENT RECORD REPORT          ");
        System.out.println("========================================");
        System.out.println("Total Students  : " + total);
        System.out.println("Highest GPA     : " + highest + " (" + highestName + ")");
        System.out.println("Lowest GPA      : " + lowest  + " (" + lowestName  + ")");
        System.out.printf( "Average GPA     : %.2f%n", average);
        System.out.println("========================================\n");

        // Also show GPA breakdown per department
        departmentBreakdown(students);
    }

    // Groups students by department and shows average GPA per department
    private static void departmentBreakdown(List<Student> students) {
        Map<String, List<Double>> deptMap = new HashMap<>();

        for (Student s : students) {
            String dept = s.getDepartment();
            // If department not in map yet, create new list for it
            if (!deptMap.containsKey(dept)) {
                deptMap.put(dept, new ArrayList<>());
            }
            deptMap.get(dept).add(s.getGpa());
        }

        System.out.println("--- GPA by Department ---");
        for (String dept : deptMap.keySet()) {
            List<Double> gpas = deptMap.get(dept);
            double deptSum = 0;
            for (double g : gpas) deptSum += g;
            double deptAvg = deptSum / gpas.size();
            System.out.printf("%-20s: %d students | Avg GPA: %.2f%n",
                    dept, gpas.size(), deptAvg);
        }
        System.out.println("-------------------------\n");
    }
}