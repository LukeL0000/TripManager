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
public class LocationGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel coord1Label;
    private JLabel coord2Label;
    private JLabel coord3Label;
    private JTextField latCoordField;
    private JTextField longCoordField;
    private JButton enterButton;

    ConditionsGUI conditionsGUI;

    DatabaseConnectionHandler dbFunc;


    final JFrame locationParent = new JFrame();

    public LocationGUI(DatabaseConnectionHandler dbHandler) {
        super("Location GUI");
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
        this.locationParent.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void setFrame() {

        this.nameLabel = new JLabel("Name:");
        this.add(nameLabel);

        this.nameField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(nameField);
        nameField.requestFocus();



        this.coord1Label = new JLabel("<");
        this.add(coord1Label);

        this.latCoordField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(latCoordField);
        latCoordField.requestFocus();

        this.coord2Label = new JLabel(",");
        this.add(coord2Label);

        this.longCoordField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(longCoordField);
        longCoordField.requestFocus();

        this.coord3Label = new JLabel(">");
        this.add(coord3Label);

        this.enterButton = new JButton("Enter");
        this.enterButton.setActionCommand("myButton");
        this.enterButton.addActionListener(this);
        this.add(enterButton);
    }

    public void successNotification(int success) {
        if (success == 0) JOptionPane.showMessageDialog(locationParent, "Success!");
        else if (success == 1) JOptionPane.showMessageDialog(locationParent, "Failed, please enter valid data.");
        else if (success == 2) JOptionPane.showMessageDialog(locationParent, "Found data.");
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            String nameText = nameField.getText();
            String latCoord = latCoordField.getText();
            String longCoord = longCoordField.getText();

            if (button.equals(this.enterButton)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise

                // function that either creates a new location or selects a previous location


                /*
                int success;

                if (name.equals("")) success = 1;
                else success = dbFunc.setTripOrganizer(name, userID);

                successNotification(success);

                if (success != 1){
                    tripIDGUI = new TripIDGUI(dbFunc);
                    this.dispose();
                }
                 */
                try {
                    int success = dbFunc.setTripLocation(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord));
                    successNotification(success);

                    if (success != 1){
                        conditionsGUI = new ConditionsGUI(dbFunc);
                        this.dispose();
                    }

                }
                catch (RuntimeException exception){
                    System.out.println("Runtime Exception occurred" + exception.getMessage());
                    successNotification(1);
                    dbFunc.setTripLocation(nameText, 0, 0);
                }

            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
