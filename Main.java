import dao.*;
import model.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final StudentDAO students = new StudentDAO();
    private static final CourseDAO courses = new CourseDAO();
    private static final EnrollmentDAO enrollments = new EnrollmentDAO();
    private static final ResearchDAO research = new ResearchDAO();

    public static void main(String[] args) {
        System.out.println("=== University Management System ===");
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose: ");
            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudent();
                    case 3 -> listStudentsByDept();
                    case 4 -> listCoursesByDept();
                    case 5 -> enrollStudent();
                    case 6 -> enterGrade();
                    case 7 -> transcript();
                    case 8 -> roster();
                    case 9 -> research.printProjects();
                    case 10 -> addResearchProject();
                    case 11 -> addResearchMember();
                    case 0 -> running = false;
                    default -> System.out.println("Not a valid option.");
                }
            } catch (SQLException e) {
                // keep it simple - show the message and go back to the menu
                System.out.println("Database error: " + e.getMessage());
            }
        }
        System.out.println("Bye!");
    }

    private static void printMenu() {
        System.out.println("\n1. Add student");
        System.out.println("2. View student");
        System.out.println("3. List students by department");
        System.out.println("4. List courses by department");
        System.out.println("5. Enroll student in course");
        System.out.println("6. Enter grade");
        System.out.println("7. Student transcript");
        System.out.println("8. Course roster");
        System.out.println("9. Research projects");
        System.out.println("10. New research project");
        System.out.println("11. Add student to research project");
        System.out.println("0. Exit");
    }

    private static void addStudent() throws SQLException {
        String name = readLine("Name: ");
        String email = readLine("Email: ");
        int dept = readInt("Department id: ");
        int year = readInt("Year (1-6): ");
        int id = students.add(new Student(name, email, dept, year));
        System.out.println("Student added with id " + id);
    }

    private static void viewStudent() throws SQLException {
        Student s = students.findById(readInt("Student id: "));
        System.out.println(s == null ? "No such student." : s);
    }

    private static void listStudentsByDept() throws SQLException {
        for (Student s : students.findByDepartment(readInt("Department id: "))) {
            System.out.println(s);
        }
    }

    private static void listCoursesByDept() throws SQLException {
        for (Course c : courses.listByDepartment(readInt("Department id: "))) {
            System.out.println(c.getId() + ": " + c);
        }
    }

    private static void enrollStudent() throws SQLException {
        int sid = readInt("Student id: ");
        int cid = readInt("Course id: ");
        String sem = readLine("Semester (e.g. 2026-ODD): ");
        if (enrollments.enroll(sid, cid, sem)) System.out.println("Enrolled.");
        else System.out.println("Could not enroll (already enrolled, or invalid id).");
    }

    private static void enterGrade() throws SQLException {
        int sid = readInt("Student id: ");
        int cid = readInt("Course id: ");
        String sem = readLine("Semester: ");
        String grade = readLine("Grade: ").toUpperCase();
        if (enrollments.setGrade(sid, cid, sem, grade)) System.out.println("Grade saved.");
        else System.out.println("No matching enrollment found.");
    }

    private static void transcript() throws SQLException {
        enrollments.printTranscript(readInt("Student id: "));
    }

    private static void roster() throws SQLException {
        int cid = readInt("Course id: ");
        String sem = readLine("Semester: ");
        enrollments.printRoster(cid, sem);
    }

    private static void addResearchProject() throws SQLException {
        String title = readLine("Project title: ");
        int lead = readInt("Lead faculty id: ");
        String date = readLine("Start date (yyyy-mm-dd): ");
        try {
            int id = research.addProject(title, lead, date);
            System.out.println("Created project #" + id);
        } catch (IllegalArgumentException e) {
            System.out.println("That date doesn't look right.");
        }
    }

    private static void addResearchMember() throws SQLException {
        int pid = readInt("Project id: ");
        int sid = readInt("Student id: ");
        String role = readLine("Role: ");
        System.out.println(research.addMember(pid, sid, role) ? "Added." : "Already a member, or invalid id.");
    }

    // small input helpers
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
