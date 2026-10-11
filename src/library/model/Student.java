package library.model;

public class Student {

    private int studentId;
    private String name;
    private String rollNo;
    private String department;
    private int year;
    private String phone;

    public Student() {
    }

    public Student(String name, String rollNo, String department,
                   int year, String phone) {

        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.year = year;
        this.phone = phone;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}