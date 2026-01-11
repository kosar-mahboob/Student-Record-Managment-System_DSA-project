public class Student {
    private String name;
    private String rollNumber;
    private double marks;
    private char grade;

    public Student(String name, String rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
        calculateGrade();
    }

    // Getters
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public double getMarks() { return marks; }
    public char getGrade() { return grade; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    
    public void setMarks(double marks) {
        this.marks = marks;
        calculateGrade();
    }

    private void calculateGrade() {
        if (marks >= 90) grade = 'A';
        else if (marks >= 80) grade = 'B';
        else if (marks >= 70) grade = 'C';
        else if (marks >= 60) grade = 'D';
        else grade = 'F';
    }

    @Override
    public String toString() {
        return String.format("%-15s %-20s %-10.2f %-8s", rollNumber, name, marks, grade);
    }
}
