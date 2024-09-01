package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.DatabaseConnectionHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;


public class FinishedGUI extends JFrame implements ActionListener {

    private static final int TEXT_FIELD_WIDTH = 10;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private JLabel congratsLabel;
    private JButton goBack;

    WelcomeStart welcomeStart;
    DatabaseConnectionHandler dbFunc;
    final JFrame finishedParent = new JFrame();

    public FinishedGUI(DatabaseConnectionHandler dbHandler) {

        super("You're done!");
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

        this.finishedParent.setDefaultCloseOperation(0);
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.setResizable(false);

    }

    public void initializeOptions() {

        this.congratsLabel = new JLabel("YAAAAY UR DONE!!!!!");
        this.add(congratsLabel);

        this.goBack = new JButton("Go Back");
        this.goBack.setActionCommand("myButton");
        this.goBack.addActionListener(this);
        this.add(goBack);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("myButton")) {
            JButton button = (JButton)e.getSource();
            if (button.equals(this.goBack)) {
                //this.menuGUI = new MenuGUI(this.customer);
                this.dispose();
                this.welcomeStart = new WelcomeStart(dbFunc);

            } else {
                this.setDefaultCloseOperation(3);
                this.dispose();
            }
        }

    }
}
