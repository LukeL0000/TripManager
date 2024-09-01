package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.Controller;
import ca.ubc.cs304.database.DatabaseConnectionHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class AdditionalFunctionalityGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JLabel updateTripNameLabel;
    private JTextField updateTripNameField;
    private JLabel updateTripIDLabel;
    private JTextField updateTripIDField;
    private JButton updateTrip;

    private JLabel removeTripLabel;
    private JTextField removeTripField;
    private JButton removeTrip;

    private JButton agg1Button;
    private JButton agg2Button;
    private JButton agg3Button;
    private JButton divButton;
    private JLabel label;

    private JButton goBack;

    WelcomeStart welcomeStart;
    final JFrame parent = new JFrame();

    DatabaseConnectionHandler dbFunc;


    public AdditionalFunctionalityGUI(DatabaseConnectionHandler dbHandler) {

        super("Additional Functionality");
        this.setPreferredSize(new Dimension(1000, 700));
        ((JPanel)this.getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        this.setLayout(new FlowLayout());
        dbFunc = dbHandler;
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.initializeOptions();

        this.parent.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);

    }

    public void popUp(ArrayList<String> names) {

        /*
        JFrame popupFrame = new JFrame("Returning information");
        //popupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JDialog popup = new JDialog(popupFrame, "Popup Window", true);

        // Create a JList with the ArrayList as the data source
        JList<String> list = new JList<>(names.toArray(new String[0]));

        // Add the list to the content pane of the popup window
        popup.getContentPane().add(new JScrollPane(list));

        popup.setSize(200, 200);
        popup.setLocationRelativeTo(popupFrame);
        popup.setVisible(true);

        // Create a close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                popupFrame.dispose(); // Close the frame
            }
        });

        // Add the close button to the frame
        popupFrame.getContentPane().add(closeButton, BorderLayout.SOUTH);

        // Add the button to the frame
        //popupFrame.getContentPane().add(popupButton);
        popupFrame.setSize(300, 200);
        popupFrame.setVisible(true);

         */
        String data = "";

        if (names != null) {
            for (int i = 0; i < names.size(); i++) {
                if (i == names.size() - 1) data += names.get(i);
                else data += names.get(i) + "\n";
            }
        } else {
            data = "Query ran, but returned no data.";
        }

        if (data.equals("")) data = "Nothing found.";

        JOptionPane.showMessageDialog(parent, data);
    }

    public void initializeOptions() {

        this.label = new JLabel("Additional Functionality Options");
        this.add(label);

        this.updateTripNameLabel = new JLabel("Update a trip (Trip Name):");
        this.add(updateTripNameLabel);

        this.updateTripNameField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(updateTripNameField);
        updateTripNameField.requestFocus();

        this.updateTripIDLabel = new JLabel("Update a trip (Trip ID):");
        this.add(updateTripIDLabel);

        this.updateTripIDField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(updateTripIDField);
        updateTripIDField.requestFocus();

        this.updateTrip = new JButton("UPDATE: Update Trip Name");
        this.updateTrip.setActionCommand("myButton");
        this.updateTrip.addActionListener(this);
        this.add(updateTrip);

        this.removeTripLabel = new JLabel("Remove a trip using the trip ID:");
        this.add(removeTripLabel);

        this.removeTripField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(removeTripField);
        removeTripField.requestFocus();

        this.removeTrip = new JButton("DELETE: Remove Trip");
        this.removeTrip.setActionCommand("myButton");
        this.removeTrip.addActionListener(this);
        this.add(removeTrip);

        this.agg1Button = new JButton("Find locations that were visited more than once (Calls aggregationByHaving)");
        this.agg1Button.setActionCommand("myButton");
        this.agg1Button.addActionListener(this);
        this.add(agg1Button);

        this.agg2Button = new JButton("Number of people on each trip (Calls aggregationByGrouping)");
        this.agg2Button.setActionCommand("myButton");
        this.agg2Button.addActionListener(this);
        this.add(agg2Button);

        this.agg3Button = new JButton("Number of trips at each location with people who went on another trip (Calls nestedAggregationByGrouping)");
        this.agg3Button.setActionCommand("myButton");
        this.agg3Button.addActionListener(this);
        this.add(agg3Button);

        this.divButton = new JButton("People who've been on every trip (Calls divide)");
        this.divButton.setActionCommand("myButton");
        this.divButton.addActionListener(this);
        this.add(divButton);

        this.goBack = new JButton("Go Back");
        this.goBack.setActionCommand("myButton");
        this.goBack.addActionListener(this);
        this.add(goBack);
    }

    public void successNotification(boolean success) {
        if (success) JOptionPane.showMessageDialog(parent, "Success!");
        else JOptionPane.showMessageDialog(parent, "Failed, please enter valid data.");
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            String tripIDremove = removeTripField.getText();
            String newName = updateTripNameField.getText();
            String tripIDupdate = updateTripIDField.getText();
            JButton button = (JButton)e.getSource();


            if (button.equals(this.removeTrip)) {
                successNotification(dbFunc.removeTrip(tripIDremove));
            } else if (button.equals(this.updateTrip)) {
                if (newName.equals("")) successNotification(false);
                else successNotification(dbFunc.updateTripName(newName, tripIDupdate));
            } else if (button.equals(this.agg1Button)) {
                popUp(dbFunc.aggregationByHaving());

            } else if (button.equals(this.agg2Button)) {
                popUp(dbFunc.aggregationByGrouping());

            } else if (button.equals(this.agg3Button)) {
                popUp(dbFunc.nestedAggregationByGrouping());
            } else if (button.equals(this.divButton)) {
                popUp(dbFunc.divide());

            } else if (button.equals(this.goBack)) {
                welcomeStart = new WelcomeStart(dbFunc);
                this.dispose();
            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
