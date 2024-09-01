package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

//UNFINISHED
public class ScheduleGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private JLabel startLabel;
    private JTextField startField;
    private JLabel endLabel;
    private JTextField endField;
    private JButton enterButton;
    ActivitiesGUI activitiesGUI;

    final JFrame scheduleParent = new JFrame();
    DatabaseConnectionHandler dbFunc;

    public ScheduleGUI(DatabaseConnectionHandler dbHandler) {
        super("Schedule GUI");
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
        this.scheduleParent.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public void setFrame() {

        this.startLabel = new JLabel("Start time:");
        this.add(startLabel);

        this.startField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(startField);
        startField.requestFocus();



        this.endLabel = new JLabel("End Time:");
        this.add(endLabel);

        this.endField = new JTextField(TEXT_FIELD_WIDTH);
        this.add(endField);
        endField.requestFocus();

        this.enterButton = new JButton("Enter");
        this.enterButton.setActionCommand("myButton");
        this.enterButton.addActionListener(this);
        this.add(enterButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            String startTime = startField.getText();
            String endTime = endField.getText();
            JButton button = (JButton)e.getSource();
            if (button.equals(this.enterButton)) {
                //this.menuGUI = new MenuGUI(this.customer);
                // possible new name instance should be made here!!
                // if userID in database, keep going, but create new otherwise

                //delimiter here lol check if if works if not give error screen etcetc

                String pattern = "yyyy-MM-dd HH:mm:ss";

                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                dateFormat.setLenient(false);

                try {
                    dateFormat.parse(startTime);
                    dateFormat.parse(endTime);
                    System.out.println("The string follows the specified pattern.");

                    //dbFunc.setTripTime(startTime, endTime);

                    activitiesGUI = new ActivitiesGUI(dbFunc);
                    this.dispose();
                } catch (ParseException p) {
                    System.out.println("The string does not match the specified pattern.");
                }





            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
