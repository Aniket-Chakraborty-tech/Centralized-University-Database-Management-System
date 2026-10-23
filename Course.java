package model;

public class Course {
    private int id;
    private String code;
    private String title;
    private int credits;
    private int deptId;
    private Integer facultyId; // can be null if nobody is assigned yet

    public Course() {}

    public Course(String code, String title, int credits, int deptId, Integer facultyId) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.deptId = deptId;
        this.facultyId = facultyId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public int getDeptId() { return deptId; }
    public Integer getFacultyId() { return facultyId; }

    @Override
    public String toString() {
        return code + " - " + title + " (" + credits + " cr)";
    }
}
