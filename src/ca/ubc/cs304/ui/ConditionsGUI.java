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
public class ConditionsGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JLabel weatherLabel;
    private JLabel terrainLabel;
    private JLabel rulesLabel;
    private JLabel hazardLabel;
    private JTextField weatherField;
    private JTextField terrainField;
    private JTextField rulesField;
    private JTextField hazardField;

    private JLabel activityLabel;
    private JTextField activityField;

    private JButton contButton;

    private String conditionID = null;

    private JButton enterActivitiesButton;
    ActivitiesGUI activitiesGUI;

    final JFrame locationParent = new JFrame();
    DatabaseConnectionHandler dbFunc;

    public ConditionsGUI(DatabaseConnectionHandler dbHandler) {
        super("Conditions GUI");
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

        this.weatherLabel = new JLabel("Weather:");
        this.add(weatherLabel);

        this.weatherField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(weatherField);
        weatherField.requestFocus();

        this.terrainLabel = new JLabel("Terrain:");
        this.add(terrainLabel);

        this.terrainField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(terrainField);
        terrainField.requestFocus();

        this.rulesLabel = new JLabel("Rules:");
        this.add(rulesLabel);

        this.rulesField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(rulesField);
        rulesField.requestFocus();

        this.hazardLabel = new JLabel("Hazards:");
        this.add(hazardLabel);

        this.hazardField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(hazardField);
        hazardField.requestFocus();

        this.activityLabel = new JLabel("Add Activities:");
        this.add(activityLabel);

        this.activityField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(activityField);
        activityField.requestFocus();

        this.enterActivitiesButton = new JButton("Add!");
        this.enterActivitiesButton.setActionCommand("myButton");
        this.enterActivitiesButton.addActionListener(this);
        this.add(enterActivitiesButton);






        this.contButton = new JButton("Continue");
        this.contButton.setActionCommand("myButton");
        this.contButton.addActionListener(this);
        this.add(contButton);
    }

    public void successNotification(boolean success) {
        if (success) JOptionPane.showMessageDialog(locationParent, "Success!");
        else JOptionPane.showMessageDialog(locationParent, "Failed, please enter valid data.");
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            String weather = weatherField.getText();
            String terrain = terrainField.getText();
            String rules = rulesField.getText();
            String hazards = hazardField.getText();
            JButton button = (JButton)e.getSource();
            if (button.equals(this.enterActivitiesButton)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise


                String activity = activityField.getText();

                if (conditionID == null) {
                    conditionID = dbFunc.generateConditionID(weather, terrain, rules, hazards);
                    successNotification(true);
                }

                successNotification(dbFunc.recommendActivity(activity, conditionID));

                // add activity to the list of activities for the conditions here

            } else if (button.equals(this.contButton)) {

                if (conditionID == null) {
                    conditionID = dbFunc.generateConditionID(weather, terrain, rules, hazards);
                    successNotification(true);
                }



                activitiesGUI = new ActivitiesGUI(dbFunc);
                this.dispose();
            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
