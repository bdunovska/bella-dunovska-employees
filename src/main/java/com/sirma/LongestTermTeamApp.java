package com.sirma;

import com.sirma.io.InputFileReader;
import com.sirma.model.Employee;
import com.sirma.model.Team;
import com.sirma.parser.EmployeeParser;
import com.sirma.service.EmployeeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class LongestTermTeamApp extends JPanel
        implements ActionListener {
    private final String[] COLUMN_NAMES = {"EmployeeID #1", "EmployeeID #2", "Project ID", "Days Worked"};
    private JButton openButton;
    private JFileChooser fileChooser;
    private JTable table;

    public LongestTermTeamApp() {
        super(new BorderLayout());

        fileChooser = new JFileChooser();
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        table = new JTable(new Object[][]{}, COLUMN_NAMES);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == openButton) {
            int returnVal = fileChooser.showOpenDialog(LongestTermTeamApp.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                openEmployeeFile(file);
            }
        }
    }

    private void openEmployeeFile(File file) {
        InputFileReader inputFileReader = new InputFileReader(file);
        List<String> fileContent = inputFileReader.readFile();

        EmployeeParser employeeParser = new EmployeeParser();
        List<Employee> employees = fileContent.stream()
                .map(EmployeeParser::parse)
                .collect(Collectors.toList());

        EmployeeService employeeService = new EmployeeService(employees);
        Team teamWithLongestCollaboration = employeeService.getTeamWithLongestCollaboration();

        Object[][] employeeData = {{
                teamWithLongestCollaboration.getFirstEmployeeId(),
                teamWithLongestCollaboration.getSecondEmployeeId(),
                teamWithLongestCollaboration.getProjectId(),
                teamWithLongestCollaboration.getTotalDuration()}
        };
        populateGrid(employeeData);
    }

    private void populateGrid(Object[][] data) {
        DefaultTableModel dtm = new DefaultTableModel(data, COLUMN_NAMES);
        table.setModel(dtm);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Longest Term Team App");
        frame.setSize(700, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new LongestTermTeamApp());
        frame.setVisible(true);
    }
}
