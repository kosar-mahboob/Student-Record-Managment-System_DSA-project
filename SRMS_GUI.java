import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SRMS_GUI extends JFrame {
    private StudentManager manager;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, rollNumberField, marksField, searchField;
    private JButton addButton, deleteButton, searchButton, clearButton;
    private JButton sortRollButton, sortNameButton, sortMarksButton, displayAllButton;
    private JComboBox<String> searchTypeCombo;

    // Colors for modern look
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color ACCENT_COLOR = new Color(231, 76, 60);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);

    public SRMS_GUI() {
        manager = new StudentManager();
        initializeGUI();
        createMenuBar();
        addSampleData();
    }

    private void initializeGUI() {
        setTitle("Student Record Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.WEST);

        // Table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Control panel
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("STUDENT RECORD MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(5, 0, 5, 0));

        JLabel subtitleLabel = new JLabel("Developed by:  Kosar Bibi", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 2, true),
            new EmptyBorder(15, 15, 15, 15)
        ));
        inputPanel.setPreferredSize(new Dimension(300, 0));

        // Title
        JLabel inputTitle = new JLabel("Student Information");
        inputTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        inputTitle.setForeground(PRIMARY_COLOR);
        inputTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(inputTitle);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Name field
        inputPanel.add(createLabel("Name:"));
        nameField = createTextField();
        inputPanel.add(nameField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Roll Number field
        inputPanel.add(createLabel("Roll Number:"));
        rollNumberField = createTextField();
        inputPanel.add(rollNumberField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Marks field
        inputPanel.add(createLabel("Marks:"));
        marksField = createTextField();
        inputPanel.add(marksField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        addButton = createStyledButton("Add Student", SUCCESS_COLOR);
        deleteButton = createStyledButton("Delete Student", ACCENT_COLOR);
        clearButton = createStyledButton("Clear Fields", SECONDARY_COLOR);

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        inputPanel.add(buttonPanel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Search section
        JLabel searchTitle = new JLabel("Search Student");
        searchTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        searchTitle.setForeground(PRIMARY_COLOR);
        searchTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(searchTitle);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Search type combo
        String[] searchTypes = {"By Roll Number", "By Name"};
        searchTypeCombo = new JComboBox<>(searchTypes);
        searchTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchTypeCombo.setBackground(Color.WHITE);
        inputPanel.add(searchTypeCombo);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        inputPanel.add(createLabel("Search:"));
        searchField = createTextField();
        inputPanel.add(searchField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        searchButton = createStyledButton("Search", SECONDARY_COLOR);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(searchButton);

        return inputPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY_COLOR, 2, true),
            new EmptyBorder(10, 10, 10, 10)
        ));

        // Table model
        String[] columns = {"Roll Number", "Name", "Marks", "Grade"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        studentTable.setRowHeight(25);
        studentTable.setSelectionBackground(SECONDARY_COLOR);
        studentTable.setSelectionForeground(Color.WHITE);
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentTable.getTableHeader().setBackground(PRIMARY_COLOR);
        studentTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(BACKGROUND_COLOR);

        displayAllButton = createStyledButton("Display All Records", PRIMARY_COLOR);
        sortRollButton = createStyledButton("Sort by Roll Number", SECONDARY_COLOR);
        sortNameButton = createStyledButton("Sort by Name", SECONDARY_COLOR);
        sortMarksButton = createStyledButton("Sort by Marks", SECONDARY_COLOR);

        controlPanel.add(displayAllButton);
        controlPanel.add(sortRollButton);
        controlPanel.add(sortNameButton);
        controlPanel.add(sortMarksButton);

        return controlPanel;
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY_COLOR);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(Color.WHITE);
        helpMenu.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());

        fileMenu.add(exitItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    // Helper methods for creating UI components
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(PRIMARY_COLOR);
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setMaximumSize(new Dimension(250, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(SECONDARY_COLOR, 1),
            new EmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(color);
            }
        });

        // Add action listeners based on button text
        if (text.equals("Add Student")) {
            button.addActionListener(e -> addStudent());
        } else if (text.equals("Delete Student")) {
            button.addActionListener(e -> deleteStudent());
        } else if (text.equals("Clear Fields")) {
            button.addActionListener(e -> clearFields());
        } else if (text.equals("Search")) {
            button.addActionListener(e -> searchStudent());
        } else if (text.equals("Display All Records")) {
            button.addActionListener(e -> displayAllStudents());
        } else if (text.equals("Sort by Roll Number")) {
            button.addActionListener(e -> sortByRollNumber());
        } else if (text.equals("Sort by Name")) {
            button.addActionListener(e -> sortByName());
        } else if (text.equals("Sort by Marks")) {
            button.addActionListener(e -> sortByMarks());
        }

        return button;
    }

    // Action methods
    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            String rollNumber = rollNumberField.getText().trim();
            double marks = Double.parseDouble(marksField.getText().trim());

            if (name.isEmpty() || rollNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student student = new Student(name, rollNumber, marks);
            if (manager.addStudent(student)) {
                JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                displayAllStudents();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid marks!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        String rollNumber = rollNumberField.getText().trim();
        if (rollNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter roll number to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete student with roll number: " + rollNumber + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (manager.deleteStudent(rollNumber)) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                displayAllStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void searchStudent() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter search term!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int searchType = searchTypeCombo.getSelectedIndex();
        tableModel.setRowCount(0); // Clear table

        if (searchType == 0) { // Search by roll number
            Student student = manager.searchByRollNumber(searchText);
            if (student != null) {
                addRowToTable(student);
                JOptionPane.showMessageDialog(this, "Student found!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else { // Search by name
            List<Student> results = manager.searchByName(searchText);
            if (!results.isEmpty()) {
                for (Student student : results) {
                    addRowToTable(student);
                }
                JOptionPane.showMessageDialog(this, "Found " + results.size() + " student(s)!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No students found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayAllStudents() {
        tableModel.setRowCount(0);
        List<Student> students = manager.getAllStudents();
        for (Student student : students) {
            addRowToTable(student);
        }
        JOptionPane.showMessageDialog(this, "Displaying all " + students.size() + " student(s)!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sortByRollNumber() {
        manager.sortByRollNumber();
        displayAllStudents();
        JOptionPane.showMessageDialog(this, "Sorted by Roll Number!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sortByName() {
        manager.sortByName();
        displayAllStudents();
        JOptionPane.showMessageDialog(this, "Sorted by Name!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sortByMarks() {
        manager.sortByMarks();
        displayAllStudents();
        JOptionPane.showMessageDialog(this, "Sorted by Marks!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        nameField.setText("");
        rollNumberField.setText("");
        marksField.setText("");
        searchField.setText("");
    }

    private void addRowToTable(Student student) {
        tableModel.addRow(new Object[]{
            student.getRollNumber(),
            student.getName(),
            student.getMarks(),
            student.getGrade()
        });
    }

    private void showAboutDialog() {
        String aboutText = "Student Record Management System\n\n" +
                          "Developed by:\n" +
                          "• Bilal Saeed (B24F1501A1018)\n" +
                          "• Harram Noor (B24F1000A1212)\n" +
                          "• Kosar Bibi (B24F1055A1025)\n" +
                          "• Saood Khan (B24F1830A1036)\n\n" +
                          "Department: Artificial Intelligence (Blue)\n" +
                          "Course: Data Structures & Algorithms (COMP-201)\n\n" +
                          "Features:\n" +
                          "• Add/Delete Students\n" +
                          "• Search by Roll Number/Name\n" +
                          "• Sort by Roll Number, Name, or Marks\n" +
                          "• Linked List Data Structure\n" +
                          "• Bubble Sort Algorithm\n" +
                          "• Linear & Binary Search";

        JOptionPane.showMessageDialog(this, aboutText, "About SRMS", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addSampleData() {
        // Add some sample data for demonstration
        manager.addStudent(new Student("Bilal Saeed", "B24F1501A1018", 88.5));
        manager.addStudent(new Student("Harram Noor", "B24F1000A1212", 92.0));
        manager.addStudent(new Student("Kosar Bibi", "B24F1055A1025", 98.5));
        manager.addStudent(new Student("Saood Khan", "B24F1830A1036", 85.0));
        displayAllStudents();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new SRMS_GUI().setVisible(true);
        });
    }
}
