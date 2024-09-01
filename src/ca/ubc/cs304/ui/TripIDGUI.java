package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.Controller;
import ca.ubc.cs304.database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//UNFINISHED
public class TripIDGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JLabel tripNameLabel;
    private JTextField tripNameField;
    //private JLabel tripIDLabel;
    //private JTextField tripIDField;
    //private JButton filterNames;
    private JButton enterButton;


    //EditingGUI editingGUI;
    LocationGUI locationGUI;

    final JFrame parent1 = new JFrame();

    DatabaseConnectionHandler dbFunc;

    public TripIDGUI(DatabaseConnectionHandler dbHandler) {
        super("Trip GUI");
        this.setPreferredSize(new Dimension(1000, 700));
        ((JPanel)this.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        this.setLayout(new FlowLayout());
        dbFunc = dbHandler;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setFrame();
        this.parent1.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void setFrame() {

        this.tripNameLabel = new JLabel("Trip Name:");
        this.add(tripNameLabel);

        this.tripNameField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(tripNameField);
        tripNameField.requestFocus();

        /*
        this.tripIDLabel = new JLabel("User ID:");
        this.add(tripIDLabel);

        this.tripIDField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(tripIDField);
        tripIDField.requestFocus();
        */

/*
        this.filterNames = new JButton("Is this your trip?");
        this.filterNames.setActionCommand("myButton");
        this.filterNames.addActionListener(this);
        this.add(filterNames);


 */


        this.enterButton = new JButton("Enter");
        this.enterButton.setActionCommand("myButton");
        this.enterButton.addActionListener(this);
        this.add(enterButton);
    }

    /*
    public void checkList(ArrayList<Trip> trips){
        JFrame frame = new JFrame("Is this your trip?");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // this code generates a new checklist item for a given array (replace with name instead of items)

        //String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
        // first column is people, second column is their userID
        for (Trip trip : trips) {
            String person_data = trip.getTripName() + " " + trip.getTripID();
            JCheckBox checkBox = new JCheckBox(person_data);
            checkBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox source = (JCheckBox) e.getSource();
                    String selectedItem = source.getText();
                    boolean selected = source.isSelected();

                    if (selected) {
                        editingGUI = new EditingGUI();
                        frame.dispose();
                        dispose();
                    }
                    System.out.println("Selected Item: " + selectedItem + ", Selected: " + selected);
                }
            });
            panel.add(checkBox);
        }

        frame.getContentPane().add(panel);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

     */

    public void successNotification(boolean success) {
        if (success) JOptionPane.showMessageDialog(parent1, "Success!");
        else JOptionPane.showMessageDialog(parent1, "Failed, please enter valid data.");
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            String tripName = tripNameField.getText();
            JButton button = (JButton)e.getSource();
            if (button.equals(this.enterButton)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise

                //String tripID = tripIDLabel.getText();

                // here choose between editing or creating
                //editingGUI = new EditingGUI();
                /*
                dbFunc.setTripName(tripName);
                locationGUI = new LocationGUI(dbFunc);

                this.dispose();
                */

                boolean success = dbFunc.setTripName(tripName);
                successNotification(dbFunc.setTripName(tripName));
                if (success) {
                    locationGUI = new LocationGUI(dbFunc);
                    this.dispose();
                }

            } /*
            else if (button.equals(this.filterNames)) {
                // !!! filter action that retrieves filtered names
                // replace the array with an actual filtered array
                ArrayList<Trip> trips = dbFunc.findTrips(tripName);
                this.checkList(trips);

            }
            */
             else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
