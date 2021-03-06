package GUI.Manager_Account.Update_personal_info.Update_username;
import Customer.CMSerialization;
import Customer.LoginSystem;
import Flight.FlightManager;
import GUI.Manager_Account.ManageAccount;
import GUI.Manager_Account.Update_personal_info.Update_PersonalinfoFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Customer.CustomerManager;
import Luggage.LuggageManager;
import Ticket.TicketManager;
import Customer.PHManager;


public class Update_username_verifiedFrame extends JFrame implements ActionListener {
    JPanel panel = new JPanel();
    JLabel label1 = new JLabel("Update account username");

    String instruction = "<html>Verified! Your username is in system, please enter your new username below:";
    JLabel label2 = new JLabel(instruction);
    JButton to_personal_information_menu = new JButton("Back to Personal Information Menu");
    JButton to_manage_account_menu = new JButton("Back to Manage Account Menu");
    Color darkRed = new Color(101,15,43);
    Color lightPink = new Color(218,198,205);

    // create a new frame to store text field and button
    JFrame textfield = new JFrame("textfield");


    // create a new button
    JButton submitb = new JButton("submit");

    // create an object of JTextField with 16 columns and a given initial text
    JTextField initialText = new JTextField("Please enter your new username", 16);
    CustomerManager cm;
    FlightManager fm;
    TicketManager tm;
    PHManager phm;
    LuggageManager lm;
    String username;

    // default constructor
    Update_username_verifiedFrame(CustomerManager customerManager, FlightManager flightManager,
                                  TicketManager ticketManager, String username, PHManager phm,LuggageManager lm) {
        this.cm = customerManager;
        this.fm = flightManager;
        this.tm = ticketManager;
        this.phm = phm;
        this.lm = lm;
        this.username=username;

        to_personal_information_menu.setFont(new Font("Times", Font.PLAIN,25));
        to_personal_information_menu.setForeground(darkRed);
        to_personal_information_menu.addActionListener(this);

        to_manage_account_menu.setFont(new Font("Times", Font.PLAIN,25));
        to_manage_account_menu.setForeground(darkRed);
        to_manage_account_menu.addActionListener(this);

        label1.setBackground(lightPink);
        label1.setFont(new Font("Times", Font.BOLD,30));
        label1.setForeground(darkRed);
        label1.setHorizontalTextPosition(JLabel.CENTER);
        label1.setVerticalTextPosition(JLabel.CENTER);
        label1.setOpaque(true);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(50,50,100,100);

        label2.setBackground(lightPink);
        label2.setForeground(darkRed);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setVerticalTextPosition(JLabel.CENTER);
        label2.setOpaque(true);
        label2.setFont(new Font("Times", Font.ITALIC,20));
        label2.setVerticalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBounds(50,50,300,300);

        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.add(label1);
        panel.add(Box.createRigidArea(new Dimension(20,10)));
        panel.add(label2);
        panel.add(Box.createRigidArea(new Dimension(10,10)));
        panel.add(initialText);
        panel.add(submitb);
        submitb.addActionListener(this);
        textfield.setSize(new Dimension(2,2));
        textfield.add(panel);


        panel.add(Box.createRigidArea(new Dimension(20,20)));
        panel.add(to_personal_information_menu);

        panel.add(Box.createRigidArea(new Dimension(20,10)));
        panel.add(to_manage_account_menu);

        panel.setBackground(lightPink);
        panel.add(Box.createHorizontalGlue());
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // set the size of frame
        textfield.setSize(20, 20);

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 320));
        this.setLocation(new Point(500, 300));
        this.pack();
        this.setVisible(true);
    }
    /*
        public static void main(String[] args) {
            // create a object of the text class
            new Update_username_verifiedFrame(cm,fm,tm);
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(submitb == e.getSource()){
            String newName = initialText.getText();
            try {
                if (LoginSystem.changeUsername(this.username, newName)){
                    this.dispose();

                    this.cm.changeUsername(newName, this.cm.showCustomer(this.username));
                    CMSerialization cmSerialization = new CMSerialization();
                    cmSerialization.saveCM(this.cm, "CMManager.ser");
                    Update_usernamesuccessFrame change_username= new Update_usernamesuccessFrame(this.cm, this.fm, this.tm,
                            this.username, this.phm, this.lm);//instantiate next page for routes picking
                    }else{
                    System.out.println("error");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }else if(to_personal_information_menu == e.getSource()){
            this.dispose();
            Update_PersonalinfoFrame personal_info = new Update_PersonalinfoFrame(this.cm, this.fm, this.tm,
                    this.username, this.phm, this.lm);//instantiate main menu
        }else if(to_manage_account_menu == e.getSource()){
            this.dispose();
            ManageAccount ManageAccountMenu = new ManageAccount(this.cm, this.fm, this.tm, this.username, this.phm, this.lm);//instantiate main menu
        }
        String s = e.getActionCommand();

    }
}

