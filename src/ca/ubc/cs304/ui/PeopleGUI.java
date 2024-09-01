package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.Controller;
import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.model.People;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.jar.JarEntry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


public class PeopleGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    private ArrayList<People> people;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JButton showTable;

    private JLabel nameLabel;
    private JLabel userIDLabel;

    private JTextField nameField;
    private JTextField userIDField;
    private JButton filter;
    private JButton goBack;
    private JLabel label;

    ParticipantGUI participantGUI;

    final JFrame parent3 = new JFrame();

    DatabaseConnectionHandler dbFunc;


    public PeopleGUI(DatabaseConnectionHandler dbHandler) {

        super("People");
        this.setPreferredSize(new Dimension(1000, 700));
        ((JPanel)this.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        this.setLayout(new FlowLayout());
        dbFunc = dbHandler;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // this should actually just be getting the current data in people table
        people = dbFunc.getPeople();

        this.initializeOptions();
        //this.initializeTable();
        this.parent3.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);


    }

    public void initializeOptions() {

        this.label = new JLabel("People");
        this.add(label);

        this.showTable = new JButton("Show Updated Table");
        this.showTable.setActionCommand("myButton");
        this.showTable.addActionListener(this);
        this.add(showTable);

        this.nameLabel = new JLabel("Name:");
        this.add(nameLabel);

        this.nameField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(nameField);
        nameField.requestFocus();



        this.userIDLabel = new JLabel("User ID:");
        this.add(userIDLabel);

        this.userIDField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(userIDField);
        userIDField.requestFocus();

        this.filter = new JButton("Filter Table");
        this.filter.setActionCommand("myButton");
        this.filter.addActionListener(this);
        this.add(filter);

        this.goBack = new JButton("Go back");
        this.goBack.setActionCommand("myButton");
        this.goBack.addActionListener(this);
        this.add(goBack);

    }

    public void initializeTable() {
        //ins table here lol

        JFrame frame = new JFrame("Table");

        //parent3.setDefaultCloseOperation(0);

        String[][] data;
        String[] columnNames = new String[]{"Name", "UserID"};;
        // Create table data
        if (people == null) {
            data = new String[][]{};
        }
        else {
            data = new String[people.size()][2];

            for (int i = 0; i < people.size(); i++) {
                People person = people.get(i);
                data[i][0] = person.getName();
                data[i][1] = person.getUserID();
            }

        }


        // Create a table model with the data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create a JTable with the table model
        JTable table = new JTable(model);

        // Set table properties
        table.setPreferredScrollableViewportSize(new Dimension(300, 200));
        table.setFillsViewportHeight(true);

        // Create a scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        //parent3.getContentPane().add(scrollPane);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
            }
        });

        // Add the close button to the frame
        frame.getContentPane().add(closeButton, BorderLayout.SOUTH);

        // Set the size and make the frame visible
        frame.setSize(400, 300);
        frame.setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            String name = nameField.getText();
            String userID = userIDField.getText();
            if (button.equals(this.goBack)) {
                //this.menuGUI = new MenuGUI(this.customer);
                this.participantGUI = new ParticipantGUI(dbFunc);
                this.dispose();
            }  else if (button.equals(showTable)) {
                this.initializeTable();
            }  else if (button.equals(filter)) {
                if(name.isEmpty()) {
                    name = null;
                }
                if(userID.isEmpty()) {
                    userID = null;
                }
                //filter info
                people = dbFunc.findUsers(name, userID);

            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
