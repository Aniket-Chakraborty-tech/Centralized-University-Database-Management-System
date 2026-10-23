package dao;

import db.DBConnection;
import model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void add(Course c) throws SQLException {
        String sql = "INSERT INTO course (course_code, title, credits, dept_id, faculty_id) VALUES (?,?,?,?,?)";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCode());
            ps.setString(2, c.getTitle());
            ps.setInt(3, c.getCredits());
            ps.setInt(4, c.getDeptId());
            if (c.getFacultyId() == null) ps.setNull(5, Types.INTEGER);
            else ps.setInt(5, c.getFacultyId());
            ps.executeUpdate();
        }
    }

    public List<Course> listByDepartment(int deptId) throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE dept_id = ? ORDER BY course_code";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course c = new Course(
                        rs.getString("course_code"),
                        rs.getString("title"),
                        rs.getInt("credits"),
                        rs.getInt("dept_id"),
                        (Integer) rs.getObject("faculty_id"));
                    c.setId(rs.getInt("course_id"));
                    list.add(c);
                }
            }
        }
        return list;
    }

    public boolean assignFaculty(int courseId, int facultyId) throws SQLException {
        String sql = "UPDATE course SET faculty_id = ? WHERE course_id = ?";
        try (Connection con = DBConnection.get();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, facultyId);
            ps.setInt(2, courseId);
            return ps.executeUpdate() > 0;
        }
    }
}
