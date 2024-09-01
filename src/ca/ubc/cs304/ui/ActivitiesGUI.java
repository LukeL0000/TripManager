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
public class ActivitiesGUI extends JFrame implements ActionListener {

    // generate checklist items of activities from conditionsGUI
    // new option to add new activity with supplies and cert

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JLabel addActivityLabel;
    private JTextField addActivityField;

    private JLabel certificationLabel;
    private JTextField certificationField;
    private JLabel suppliesLabel;
    private JLabel typeLabel;
    private JLabel modelLabel;
    private JLabel costLabel;
    private JTextField typeField;
    private JTextField modelField;
    private JTextField costField;

    private JLabel supplyIDLabel;
    private JTextField supplyIDField;



    private JButton contButton;

    private JButton addActivity;
    private JButton addCertification;
    private JButton addOldSupplies;
    private JButton addNewSupplies;

    AddPeopleGUI addPeopleGUI;

    final JFrame activitiesParent = new JFrame();

    DatabaseConnectionHandler dbFunc;

    public ActivitiesGUI(DatabaseConnectionHandler dbHandler) {
        super("Activities GUI");
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
        //this.createChecklist();
        this.activitiesParent.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void setFrame() {

        this.addActivityLabel = new JLabel("Add new activity:");
        this.add(addActivityLabel);

        this.addActivityField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(addActivityField);
        addActivityField.requestFocus();

        this.addActivity = new JButton("Add activity!");
        this.addActivity.setActionCommand("myButton");
        this.addActivity.addActionListener(this);
        this.add(addActivity);
        /*
        this.certificationLabel = new JLabel("Certification:");
        this.add(certificationLabel);

        this.certificationField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(certificationField);
        certificationField.requestFocus();

        this.addCertification = new JButton("Add certification to activity");
        this.addCertification.setActionCommand("myButton");
        this.addCertification.addActionListener(this);
        this.add(addCertification);

        this.suppliesLabel = new JLabel("Supplies:");
        this.add(suppliesLabel);

        this.typeLabel = new JLabel("Type:");
        this.add(typeLabel);

        this.typeField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(typeField);
        typeField.requestFocus();

        this.modelLabel = new JLabel("Model:");
        this.add(modelLabel);

        this.modelField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(modelField);
        modelField.requestFocus();

        this.costLabel = new JLabel("Cost: $");
        this.add(costLabel);

        this.costField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(costField);
        costField.requestFocus();

        this.addNewSupplies = new JButton("Add new supplies to activity");
        this.addNewSupplies.setActionCommand("myButton");
        this.addNewSupplies.addActionListener(this);
        this.add(addNewSupplies);

        this.supplyIDLabel = new JLabel("Supply ID:");
        this.add(supplyIDLabel);

        this.supplyIDField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(supplyIDField);
        supplyIDField.requestFocus();

        this.addOldSupplies = new JButton("Add given supplies to activity");
        this.addOldSupplies.setActionCommand("myButton");
        this.addOldSupplies.addActionListener(this);
        this.add(addOldSupplies);

         */





        this.contButton = new JButton("Continue");
        this.contButton.setActionCommand("myButton");
        this.contButton.addActionListener(this);
        this.add(contButton);
    }

    /*
    public void createChecklist() {
        //ins table here lol
    }

     */

    public void successNotification(boolean success) {
        if (success) JOptionPane.showMessageDialog(activitiesParent, "Success!");
        else JOptionPane.showMessageDialog(activitiesParent, "Failed, please enter valid data.");
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            String activity = addActivityField.getText();
            /*
            String type = typeField.getText();
            String model = modelField.getText();
            String cost = costField.getText();

            String supplyID = supplyIDField.getText();

             */

            JButton button = (JButton)e.getSource();
            if (button.equals(this.addActivity)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise
                // add activity to the list of activities for the conditions here

                successNotification(dbFunc.addActivity(activity));


            } /*
            else if (button.equals(addCertification)) {

                successNotification(dbFunc.addActivityCertification(activity, certification));


            } else if (button.equals(addOldSupplies)) {
                successNotification(dbFunc.addActivityOldSupply(activity, supplyID));

            } else if (button.equals(addNewSupplies)) {
                try {
                    successNotification(dbFunc.addActivityNewSupply(activity, type, model, Double.parseDouble(cost)));


                }
                catch (RuntimeException exception){
                    System.out.println("Runtime Exception occurred" + exception.getMessage());
                    successNotification(false);
                    //dbFunc.setTripLocation(nameText, 0, 0);
                }


            }*/ else if (button.equals(this.contButton)) {
                // go next
                addPeopleGUI = new AddPeopleGUI(dbFunc);
                this.dispose();
            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
