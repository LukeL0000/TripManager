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
public class NameGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel userIDLabel;
    private JTextField userIDField;
    //private JButton filterNames;
    private JButton enterButton;
    TripIDGUI tripIDGUI;

    final JFrame parent1 = new JFrame();

    DatabaseConnectionHandler dbFunc;

    public NameGUI(DatabaseConnectionHandler dbHandler) {
        super("Name GUI");
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

        this.nameLabel = new JLabel("Name:");
        this.add(nameLabel);

        this.nameField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(nameField);
        nameField.requestFocus();



        this.userIDLabel = new JLabel("User ID:");
        this.add(userIDLabel);

        this.userIDField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(userIDField);
        userIDField.requestFocus();

        /*
        this.filterNames = new JButton("Is this you?");
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
    public void checkList(ArrayList<People> people){
        JFrame frame = new JFrame("Is this you?");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // this code generates a new checklist item for a given array (replace with name instead of items)

        //String[] items = {"Item 1", "Item 2", "Item 3", "Item 4"};
        // first column is people, second column is their userID
        for (People person : people) {
            String person_data = person.getName() + " " + person.getUserID();
            JCheckBox checkBox = new JCheckBox(person_data);
            checkBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox source = (JCheckBox) e.getSource();
                    String selectedItem = source.getText();
                    boolean selected = source.isSelected();

                    if (selected) {
                        dbFunc.setTripOrganizer(person.getName(), person.getUserID());
                        tripIDGUI = new TripIDGUI();
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

    public void successNotification(int success) {
        if (success == 0) JOptionPane.showMessageDialog(parent1, "Success!");
        else if (success == 1) JOptionPane.showMessageDialog(parent1, "Failed, please enter valid data.");
        else if (success == 2) JOptionPane.showMessageDialog(parent1, "Found data.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            String name = nameField.getText();
            String userID = userIDField.getText();
            if (button.equals(this.enterButton)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise
                int success;

                if (name.equals("")) success = 1;
                else success = dbFunc.setTripOrganizer(name, userID);

                successNotification(success);

                if (success != 1){
                    tripIDGUI = new TripIDGUI(dbFunc);
                    this.dispose();
                }



            }
            /*
            else if (button.equals(this.filterNames)) {
                // !!! filter action that retrieves filtered names
                // replace the array with an actual filtered array
                ArrayList<People> people = dbFunc.findUsers(name);
                this.checkList(people);
                //this.checkList(new Object[][]{{"Bob", 1}, {"Anna", 2}, {"George", 3}});

            }
            */
            else {

                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
