import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton addButton, deleteButton, saveButton, loadButton;

    private static final String FILE_NAME = "tasks.txt";

    public ToDoListApp() {
        setTitle("To-Do List App");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for input
        JPanel topPanel = new JPanel();
        taskInput = new JTextField(20);
        addButton = new JButton("Add Task");
        topPanel.add(taskInput);
        topPanel.add(addButton);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for task list
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        JScrollPane scrollPane = new JScrollPane(taskList);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for actions
        JPanel bottomPanel = new JPanel();
        deleteButton = new JButton("Delete Task");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        bottomPanel.add(deleteButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add task action
        addButton.addActionListener(e -> {
            String task = taskInput.getText().trim();
            if (!task.isEmpty()) {
                taskListModel.addElement(task);
                taskInput.setText("");
            }
        });

        // Delete task action
        deleteButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                taskListModel.remove(selectedIndex);
            }
        });

        // Save tasks to file
        saveButton.addActionListener(e -> saveTasks());

        // Load tasks from file
        loadButton.addActionListener(e -> loadTasks());

        setVisible(true);
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < taskListModel.size(); i++) {
                writer.write(taskListModel.get(i));
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Tasks saved successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving tasks.");
        }
    }

    private void loadTasks() {
        taskListModel.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String task;
            while ((task = reader.readLine()) != null) {
                taskListModel.addElement(task);
            }
            JOptionPane.showMessageDialog(this, "Tasks loaded successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading tasks.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoListApp::new);
    }
}

