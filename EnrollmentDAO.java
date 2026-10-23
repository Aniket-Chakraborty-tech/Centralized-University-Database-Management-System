package dao;

import db.DBConnection;

import java.sql.*;

public class EnrollmentDAO {

    // returns false if the student is already enrolled for that semester
    public boolean enroll(int studentId, int courseId, String semester) throws SQLException {
        String sql = "INSERT INTO enrollment (student_id, course_id, semester) VALUES (?,?,?)";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setString(3, semester);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // duplicate (unique key) or bad student/course id
            return false;
        }
    }

    public boolean setGrade(int studentId, int courseId, String semester, String grade) throws SQLException {
        String sql = "UPDATE enrollment SET grade = ? WHERE student_id = ? AND course_id = ? AND semester = ?";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, grade);
            ps.setInt(2, studentId);
            ps.setInt(3, courseId);
            ps.setString(4, semester);
            return ps.executeUpdate() > 0;
        }
    }

    public void printTranscript(int studentId) throws SQLException {
        String sql = "SELECT c.course_code, c.title, c.credits, e.semester, e.grade " +
                     "FROM enrollment e JOIN course c ON c.course_id = e.course_id " +
                     "WHERE e.student_id = ? ORDER BY e.semester, c.course_code";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-8s %-32s %-4s %-10s %-5s%n", "Code", "Title", "Cr", "Semester", "Grade");
                boolean any = false;
                while (rs.next()) {
                    any = true;
                    String grade = rs.getString("grade");
                    System.out.printf("%-8s %-32s %-4d %-10s %-5s%n",
                        rs.getString("course_code"), rs.getString("title"),
                        rs.getInt("credits"), rs.getString("semester"),
                        grade == null ? "-" : grade);
                }
                if (!any) System.out.println("(no enrollments found)");
            }
        }
    }

    public void printRoster(int courseId, String semester) throws SQLException {
        String sql = "SELECT s.student_id, s.full_name, d.dept_name " +
                     "FROM enrollment e " +
                     "JOIN student s ON s.student_id = e.student_id " +
                     "JOIN department d ON d.dept_id = s.dept_id " +
                     "WHERE e.course_id = ? AND e.semester = ? ORDER BY s.full_name";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.setString(2, semester);
            try (ResultSet rs = ps.executeQuery()) {
                boolean any = false;
                while (rs.next()) {
                    any = true;
                    System.out.printf("%d  %-20s %s%n",
                        rs.getInt(1), rs.getString(2), rs.getString(3));
                }
                if (!any) System.out.println("(nobody enrolled)");
            }
        }
    }
}
