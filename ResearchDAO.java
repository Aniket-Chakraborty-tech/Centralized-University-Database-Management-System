package dao;

import db.DBConnection;

import java.sql.*;

public class ResearchDAO {

    public int addProject(String title, int leadFacultyId, String startDate) throws SQLException {
        String sql = "INSERT INTO research_project (title, lead_faculty_id, started_on) VALUES (?,?,?)";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setInt(2, leadFacultyId);
            ps.setDate(3, Date.valueOf(startDate)); // expects yyyy-mm-dd
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean addMember(int projectId, int studentId, String role) throws SQLException {
        String sql = "INSERT INTO project_member (project_id, student_id, role) VALUES (?,?,?)";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, studentId);
            ps.setString(3, role);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        }
    }

    public boolean updateStatus(int projectId, String status) throws SQLException {
        String sql = "UPDATE research_project SET status = ? WHERE project_id = ?";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status.toUpperCase());
            ps.setInt(2, projectId);
            return ps.executeUpdate() > 0;
        }
    }

    public void printProjects() throws SQLException {
        // LEFT JOIN so projects with no members still show up
        String sql = "SELECT p.project_id, p.title, p.status, f.full_name AS lead, COUNT(m.student_id) AS members " +
                     "FROM research_project p " +
                     "JOIN faculty f ON f.faculty_id = p.lead_faculty_id " +
                     "LEFT JOIN project_member m ON m.project_id = p.project_id " +
                     "GROUP BY p.project_id, p.title, p.status, f.full_name " +
                     "ORDER BY p.project_id";
        try (Connection con = DBConnection.get();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("#%d  %s  [%s]  lead: %s  members: %d%n",
                    rs.getInt("project_id"), rs.getString("title"),
                    rs.getString("status"), rs.getString("lead"), rs.getInt("members"));
            }
        }
    }
}
