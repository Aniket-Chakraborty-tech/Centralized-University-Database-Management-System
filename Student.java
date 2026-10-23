package model;

public class Student {
    private int id;
    private String name;
    private String email;
    private int deptId;
    private int year;

    public Student() {}

    public Student(String name, String email, int deptId, int year) {
        this.name = name;
        this.email = email;
        this.deptId = deptId;
        this.year = year;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | dept=%d | year %d", id, name, email, deptId, year);
    }
}
