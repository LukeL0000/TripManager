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
public class RecommendedGUI extends JFrame implements ActionListener {

    // generate checklist items of activities from conditionsGUI
    // new option to add new activity with supplies and cert

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JLabel certificationLabel;
    private JTextField certificationField;
    private JLabel suppliesLabel;
    private JLabel typeLabel;
    private JLabel modelLabel;
    private JLabel costLabel;
    private JTextField typeField;
    private JTextField modelField;
    private JTextField costField;

    private JLabel suppleIDLabel;
    private JTextField suppleIDField;



    private JButton contButton;

    private JButton enterNewSupplies;
    private JButton enterOldSupplies;
    private JButton enterCertification;
    AddPeopleGUI addPeopleGUI;

    final JFrame activitiesParent = new JFrame();

    DatabaseConnectionHandler dbFunc;


    public RecommendedGUI(DatabaseConnectionHandler dbHandler) {
        super("Recommended GUI");
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
        this.createChecklist();
        this.activitiesParent.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void setFrame() {

        this.certificationLabel = new JLabel("Certification:");
        this.add(certificationLabel);

        this.certificationField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(certificationField);
        certificationField.requestFocus();

        this.enterCertification = new JButton("Add certification");
        this.enterCertification.setActionCommand("myButton");
        this.enterCertification.addActionListener(this);
        this.add(enterCertification);

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

        this.enterNewSupplies = new JButton("Add new supplies");
        this.enterNewSupplies.setActionCommand("myButton");
        this.enterNewSupplies.addActionListener(this);
        this.add(enterNewSupplies);


        this.suppliesLabel = new JLabel("Or add supply ID:");
        this.add(suppliesLabel);

        this.suppleIDField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(suppleIDField);
        suppleIDField.requestFocus();

        this.enterOldSupplies = new JButton("Add supply ID");
        this.enterOldSupplies.setActionCommand("myButton");
        this.enterOldSupplies.addActionListener(this);
        this.add(enterOldSupplies);

        this.contButton = new JButton("Continue");
        this.contButton.setActionCommand("myButton");
        this.contButton.addActionListener(this);
        this.add(contButton);
    }

    public void createChecklist() {
        //ins table here lol
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            String certification = certificationField.getText();
            String supplyType = typeField.getText();
            String supplyModel = modelField.getText();
            String supplyCost = costField.getText();
            String supplyID = suppleIDField.getText();

            JButton button = (JButton)e.getSource();
            if (button.equals(this.enterCertification)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise

                //String activity = addActivityField.getText();
                //dbFunc.addCertification(certification);



                // add activity to the list of activities for the conditions here

            }
            else if (button.equals(this.enterNewSupplies)) {

                try {
                    //dbFunc.setTripLocation(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord));
                    //dbFunc.setNewTransport(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord), type, model, Double.parseDouble(cost));
                    //dbFunc.addNewSupplies(supplyType, supplyModel, Double.parseDouble(supplyCost));
                    this.dispose();
                }
                catch (RuntimeException exception){
                    System.out.println("Runtime Exception occurred" + exception.getMessage());
                    //dbFunc.setTripLocation(nameText, 0, 0);
                }

            }
            else if (button.equals(this.enterOldSupplies)) {

                try {
                    //dbFunc.setTripLocation(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord));
                    /*
                    if (dbFunc.addOldSupplies(supplyID)){
                    } else {
                        System.out.println("Transport ID is invalid or cannot be used for trip.");
                    }
                    */

                } catch (RuntimeException exception){
                    System.out.println("Runtime Exception occurred" + exception.getMessage());
                    //dbFunc.setTripLocation(nameText, 0, 0);
                } }

            else if (button.equals(this.contButton)) {
                // go next

                //addPeopleGUI =
                addPeopleGUI = new AddPeopleGUI(dbFunc);


                this.dispose();
            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
