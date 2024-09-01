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
public class TransportationGUI extends JFrame implements ActionListener {

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

    private JLabel transportLabel;
    private JLabel typeLabel;
    private JLabel modelLabel;
    private JLabel costLabel;
    private JTextField typeField;
    private JTextField modelField;
    private JTextField costField;

    private JLabel transportIDLabel;
    private JTextField transportIDField;

    private JButton enterNewTransportButton;
    private JButton enterOldTransportButton;

    FinishedGUI finishedGUI;

    DatabaseConnectionHandler dbFunc;

    final JFrame locationParent = new JFrame();

    public TransportationGUI(DatabaseConnectionHandler dbHandler) {
        super("Transportation GUI");
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

        this.transportLabel = new JLabel("Transport:");
        this.add(transportLabel);

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

        this.enterNewTransportButton = new JButton("Enter");
        this.enterNewTransportButton.setActionCommand("myButton");
        this.enterNewTransportButton.addActionListener(this);
        this.add(enterNewTransportButton);

        this.transportIDLabel = new JLabel("Or enter transportID: ");
        this.add(transportIDLabel);

        this.transportIDField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(transportIDField);
        transportIDField.requestFocus();

        this.enterOldTransportButton = new JButton("Enter");
        this.enterOldTransportButton.setActionCommand("myButton");
        this.enterOldTransportButton.addActionListener(this);
        this.add(enterOldTransportButton);
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            String nameText = nameField.getText();
            String latCoord = latCoordField.getText();
            String longCoord = longCoordField.getText();

            String type = typeField.getText();
            String model = modelField.getText();
            String cost = costField.getText();

            String transportID = transportIDField.getText();

            if (button.equals(this.enterNewTransportButton)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise

                // function that either creates a new location or selects a previous location

                try {
                    //dbFunc.setTripLocation(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord));
                    //dbFunc.setNewTransport(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord), type, model, Double.parseDouble(cost));

                    // findtransport func

                    finishedGUI = new FinishedGUI(dbFunc);
                    this.dispose();
                }
                catch (RuntimeException exception){
                    System.out.println("Runtime Exception occurred" + exception.getMessage());
                    //dbFunc.setTripLocation(nameText, 0, 0);
                }

            }
            if (button.equals(this.enterOldTransportButton)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise

                // function that either creates a new location or selects a previous location

                try {
                    //dbFunc.setTripLocation(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord));
                    /*
                    if (dbFunc.setOldTransport(nameText, Double.parseDouble(latCoord), Double.parseDouble(longCoord), transportID)){
                        finishedGUI = new FinishedGUI(dbFunc);
                        this.dispose();
                    } else {
                        System.out.println("Transport ID is invalid or cannot be used for trip.");
                    }
                */

                }
                catch (RuntimeException exception){
                    System.out.println("Runtime Exception occurred" + exception.getMessage());
                    //dbFunc.setTripLocation(nameText, 0, 0);
                }

            }else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
