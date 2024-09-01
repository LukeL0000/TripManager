package ca.ubc.cs304.ui;

import ca.ubc.cs304.controller.Controller;
import ca.ubc.cs304.database.DatabaseConnectionHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class WelcomeStart extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JButton makeTrip;
    private JButton findTrip;
    private JButton additionalFunctionalityButton;
    private JLabel label;

    NameGUI nameGUI;
    ParticipantGUI participantGUI;
    final JFrame parent = new JFrame();

    AdditionalFunctionalityGUI additionalFunctionalityGUI;

    DatabaseConnectionHandler dbFunc;

    public WelcomeStart(DatabaseConnectionHandler dbHandler) {
        super("Welcome!");
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

    public void initializeOptions() {

        this.label = new JLabel("Welcome! Please select one of the following options:");
        this.add(label);

        this.findTrip = new JButton("Find Trip");
        this.findTrip.setActionCommand("myButton");
        this.findTrip.addActionListener(this);
        this.add(findTrip);

        this.makeTrip = new JButton("Make Trip");
        this.makeTrip.setActionCommand("myButton");
        this.makeTrip.addActionListener(this);
        this.add(makeTrip);

        this.additionalFunctionalityButton = new JButton("Additional Functionality");
        this.additionalFunctionalityButton.setActionCommand("myButton");
        this.additionalFunctionalityButton.addActionListener(this);
        this.add(additionalFunctionalityButton);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            if (button.equals(this.findTrip)) {
                //this.menuGUI = new MenuGUI(this.customer);
                this.dispose();
                this.participantGUI = new ParticipantGUI(dbFunc);

            } else if (button.equals(this.makeTrip)) {
                //this.orderGUI = new OrderGUI(this.customer);
                this.dispose();
                this.nameGUI = new NameGUI(dbFunc);


            }  else if (button.equals(this.additionalFunctionalityButton)) {
                //this.orderGUI = new OrderGUI(this.customer);
                this.dispose();
                this.additionalFunctionalityGUI = new AdditionalFunctionalityGUI(dbFunc);


            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
