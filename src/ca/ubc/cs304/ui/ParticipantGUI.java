package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ParticipantGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JButton people;
    private JButton trips;
    private JButton goBack;

    private JLabel label;

    PeopleGUI peopleGUI;
    TripsGUI tripsGUI;
    WelcomeStart welcomeStart;
    DatabaseConnectionHandler dbFunc;
    final JFrame parent2 = new JFrame();
    public ParticipantGUI(DatabaseConnectionHandler dbHandler) {

        super("Participant");
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
        this.parent2.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void initializeOptions() {

        this.label = new JLabel("Please select one of the following options:");
        this.add(label);

        this.people = new JButton("People");
        this.people.setActionCommand("myButton");
        this.people.addActionListener(this);
        this.add(people);

        this.trips = new JButton("Trips");
        this.trips.setActionCommand("myButton");
        this.trips.addActionListener(this);
        this.add(trips);

        this.goBack = new JButton("Go back");
        this.goBack.setActionCommand("myButton");
        this.goBack.addActionListener(this);
        this.add(goBack);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            if (button.equals(this.people)) {
                //this.menuGUI =
                this.peopleGUI = new PeopleGUI(dbFunc);
                this.dispose();
            } else if (button.equals(this.trips)) {
                //this.orderGUI = new OrderGUI(this.customer);
                //this.nameGUI = new NameGUI();
                this.tripsGUI = new TripsGUI(dbFunc);

                this.dispose();
            } else if (button.equals((this.goBack))) {
                this.welcomeStart = new WelcomeStart(dbFunc);
                this.dispose();


            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
