package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditingGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JButton people;
    private JButton tripName;
    private JButton schedule;
    private JButton location;


    private JLabel label;
    private DatabaseConnectionHandler dbFunc;

    EditingGUI editingGUI;
    final JFrame editParent = new JFrame();
    public EditingGUI(DatabaseConnectionHandler dbHandler) {

        super("EditingGUI");
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
        this.editParent.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void initializeOptions() {

        this.label = new JLabel("What would you like to change?");
        this.add(label);

        this.people = new JButton("People");
        this.people.setActionCommand("myButton");
        this.people.addActionListener(this);
        this.add(people);

        this.tripName = new JButton("Trip Name");
        this.tripName.setActionCommand("myButton");
        this.tripName.addActionListener(this);
        this.add(tripName);

        this.schedule = new JButton("Schedule");
        this.schedule.setActionCommand("myButton");
        this.schedule.addActionListener(this);
        this.add(schedule);

        this.location = new JButton("Location");
        this.location.setActionCommand("myButton");
        this.location.addActionListener(this);
        this.add(location);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            if (button.equals(this.people)) {
                //this.menuGUI =
                //this.peopleGUI = new PeopleGUI();
                this.dispose();
            } else if (button.equals(this.tripName)) {
                //this.orderGUI = new OrderGUI(this.customer);
                //this.nameGUI = new NameGUI();
                //this.tripsGUI = new TripsGUI();

                this.dispose();
            } else if (button.equals((this.schedule))) {
                //this.welcomeStart = new WelcomeStart();
                this.dispose();


            } else if (button.equals((this.location))) {
                //this.welcomeStart = new WelcomeStart();
                this.dispose();


            }else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
