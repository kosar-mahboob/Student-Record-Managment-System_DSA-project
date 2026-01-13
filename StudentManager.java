import java.util.*;
import javax.swing.JOptionPane;

public class StudentManager {
    private LinkedList<Student> studentList;
    private List<Student> arrayList; // For binary search

    public StudentManager() {
        studentList = new LinkedList<>();
        arrayList = new ArrayList<>();
    }

    // Add student
    public boolean addStudent(Student student) {
        // Check if roll number already exists
        for (Student s : studentList) {
            if (s.getRollNumber().equals(student.getRollNumber())) {
                JOptionPane.showMessageDialog(null, 
                    "Student with roll number " + student.getRollNumber() + " already exists!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        studentList.add(student);
        arrayList.add(student); // Keep arrayList synchronized
        return true;
    }

    // Delete student by roll number
    public boolean deleteStudent(String rollNumber) {
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getRollNumber().equals(rollNumber)) {
                iterator.remove();
                // Remove from arrayList as well
                arrayList.removeIf(s -> s.getRollNumber().equals(rollNumber));
                return true;
            }
        }
        return false;
    }

    // Search by roll number using linear search
    public Student searchByRollNumber(String rollNumber) {
        for (Student student : studentList) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    // Search by name using linear search
    public List<Student> searchByName(String name) {
        List<Student> results = new ArrayList<>();
        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(student);
            }
        }
        return results;
    }
    // Sort by roll number using bubble sort
    public void sortByRollNumber() {
        for (int i = 0; i < studentList.size() - 1; i++) {
            for (int j = 0; j < studentList.size() - i - 1; j++) {
                Student s1 = studentList.get(j);
                Student s2 = studentList.get(j + 1);
                if (s1.getRollNumber().compareTo(s2.getRollNumber()) > 0) {
                    // Swap
                    Collections.swap(studentList, j, j + 1);
                }
            }
        }
        // Sync arrayList
        arrayList = new ArrayList<>(studentList);
    }

    // Sort by name using bubble sort
    public void sortByName() {
        for (int i = 0; i < studentList.size() - 1; i++) {
            for (int j = 0; j < studentList.size() - i - 1; j++) {
                Student s1 = studentList.get(j);
                Student s2 = studentList.get(j + 1);
                if (s1.getName().compareToIgnoreCase(s2.getName()) > 0) {
                    // Swap
                    Collections.swap(studentList, j, j + 1);
                }
            }
        }
        // Sync arrayList
        arrayList = new ArrayList<>(studentList);
    }

    // Sort by marks using bubble sort (descending)
    public void sortByMarks() {
        for (int i = 0; i < studentList.size() - 1; i++) {
            for (int j = 0; j < studentList.size() - i - 1; j++) {
                Student s1 = studentList.get(j);
                Student s2 = studentList.get(j + 1);
                if (s1.getMarks() < s2.getMarks()) {
                    // Swap
                    Collections.swap(studentList, j, j + 1);
                }
            }
        }
        // Sync arrayList
        arrayList = new ArrayList<>(studentList);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentList);
    }

    // Get student count
    public int getStudentCount() {
        return studentList.size();
    }

    // Binary search by roll number (requires sorted list by roll number)
    public Student binarySearchByRollNumber(String rollNumber) {
        sortByRollNumber(); // Ensure the list is sorted
        int left = 0;
        int right = arrayList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Student midStudent = arrayList.get(mid);
            int comparison = midStudent.getRollNumber().compareTo(rollNumber);

            if (comparison == 0) {
                return midStudent;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }
}
