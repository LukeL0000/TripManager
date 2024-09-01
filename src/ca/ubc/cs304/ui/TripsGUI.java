package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.Controller;
import ca.ubc.cs304.database.DatabaseConnectionHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;


public class TripsGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static String[] master_checklist_Columns = {"t.Name", "t.TripID", "p.Name", "p.UserID",
            "t.LocationName", "t.Latitude", "t.Longitude", "e.ActivityName"};
    private static boolean[] columns_included;
    private ArrayList<String> current_checklist_columns;
    private ArrayList<ArrayList<String>> tripData;
    private JButton goBack;
    private JButton showTable;
    private JLabel label;

    private JButton showBy;

    ParticipantGUI participantGUI;

    final JFrame parent3 = new JFrame();

    DatabaseConnectionHandler dbFunc;


    public TripsGUI(DatabaseConnectionHandler dbHandler) {

        super("Trips");
        this.setPreferredSize(new Dimension(1000, 700));
        ((JPanel)this.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        this.setLayout(new FlowLayout());
        dbFunc = dbHandler;
        current_checklist_columns = new ArrayList<>();
        current_checklist_columns.addAll(Arrays.asList(master_checklist_Columns));
        //current_checklist_columns = master_checklist_Columns;
        columns_included = new boolean[]{true, true, true, true, true, true, true, true, true};
        tripData = dbFunc.filterTripData(current_checklist_columns);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.initializeOptions();
        this.parent3.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);


    }

    public void initializeOptions() {

        this.label = new JLabel("Trips");
        this.add(label);

        this.showTable = new JButton("Show Table");
        this.showTable.setActionCommand("myButton");
        this.showTable.addActionListener(this);
        this.add(showTable);

        this.goBack = new JButton("Go back");
        this.goBack.setActionCommand("myButton");
        this.goBack.addActionListener(this);
        this.add(goBack);

        this.showBy = new JButton("Show by:");
        this.showBy.setActionCommand("myButton");
        this.showBy.addActionListener(this);
        this.add(showBy);

    }

    public void initializeTable() {
        //ins table here lol
        JFrame frame = new JFrame("Table");
        //parent3.setDefaultCloseOperation(0);

        // Create table data
        /*
        Object[][] data = {
                {"John", 25, "Male"},
                {"Jane", 30, "Female"},
                {"Mike", 35, "Male"}
        };

         */

        // Create table column names
        //String[] columnNames = {"Name", "Age", "Gender"};

        String[][] data;
        String[] columnNames = current_checklist_columns.toArray(new String[0]);

        if (tripData == null) {
            data = new String[][]{{}};
        }
        else {
            data = new String[tripData.size()][tripData.get(0).size()];

            for (int x = 0; x < tripData.size(); x ++) {
                for (int y = 0; y <tripData.get(0).size(); y++) {
                    String input = tripData.get(x).get(y);

                    int max_size = 30;
                    if (input.length() > max_size) {
                        input = input.substring(0, max_size) + "\n"
                                + input.substring(max_size, input.length());
                    }
                    data[x][y] = input;
                }
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
        frame.setSize(2500, 600);
        frame.setVisible(true);


    }

    public void checkList(){
        JFrame frame = new JFrame("Show only");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton closeButton = new JButton("Close");
        add(closeButton, BorderLayout.SOUTH);

        columns_included = new boolean[]{false, false, false, false, false, false, false, false, false};

        // this code generates a new checklist item for a given array (replace with name instead of items)

        //String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
        // first column is people, second column is their userID
        for (String column : master_checklist_Columns) {
            //String person_data = person.getName() + " " + person.getUserID();
            JCheckBox checkBox = new JCheckBox(column);
            checkBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox source = (JCheckBox) e.getSource();
                    String selectedItem = source.getText();
                    boolean selected = source.isSelected();

                    if (selected) {
                        /*
                        dbFunc.setTripOrganizer(person.getName(), person.getUserID());
                        tripIDGUI = new TripIDGUI();
                        frame.dispose();
                        dispose();

                         */

                        for (int i = 0; i < master_checklist_Columns.length; i++) {
                            if (master_checklist_Columns[i].equals(selectedItem)){
                                columns_included[i] = true;
                                break;
                            }
                        }


                    }
                    else {
                        for (int i = 0; i < master_checklist_Columns.length; i++) {
                            if (master_checklist_Columns[i].equals(selectedItem)){
                                columns_included[i] = false;
                                break;
                            }
                        }
                    }
                    System.out.println("Selected Item: " + selectedItem + ", Selected: " + selected);
                }
            });
            panel.add(checkBox);

        }

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the application
                // update trip data information
                current_checklist_columns.clear();

                for (int i = 0; i < master_checklist_Columns.length; i++) {
                    if (columns_included[i]) current_checklist_columns.add(master_checklist_Columns[i]);
                }

                dbFunc.filterTripData(current_checklist_columns);
                frame.dispose();
            }
        });

        panel.add(closeButton);

        frame.getContentPane().add(panel);
        frame.setSize(300, 600);
        frame.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            if (button.equals(this.goBack)) {
                //this.menuGUI = new MenuGUI(this.customer);
                this.participantGUI = new ParticipantGUI(dbFunc);
                this.dispose();
            } else if (button.equals(showTable)) {
                this.initializeTable();
            } else if (button.equals(showBy)) {
                this.checkList();
            }else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
