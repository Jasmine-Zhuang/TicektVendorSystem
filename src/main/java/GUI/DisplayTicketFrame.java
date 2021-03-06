package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Customer.PHManager;
import Customer.PHMSerialiazation;
import Customer.CMSerialization;
import Customer.CustomerManager;
import Flight.FlightManager;
import Flight.FlightSerialization;
import Luggage.LuggageManager;
import Ticket.Ticket;
import Ticket.TicketManager;
import Ticket.TicketSerialization;

public class DisplayTicketFrame extends JFrame implements ActionListener{
    CustomerManager cm;
    FlightManager fm;
    TicketManager tm;
    PHManager phm;
    LuggageManager lm;
    String flightNum;
    String username;
    String classType;
    int ticketPrice;
    String d_city;
    String a_city;
    LocalDateTime d_time;
    LocalDateTime a_time;
    String boardingGate;
    String seatNum;
    String t_id;
    JButton buttonFinish = new JButton("Finish");
    JLabel label = new JLabel("Ticket Booked!!");
    JLabel label2 = new JLabel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    FlightSerialization flightSerialization = new FlightSerialization();
    TicketSerialization ticketSerialization = new TicketSerialization();
    PHMSerialiazation phmSerialiazation = new PHMSerialiazation();
    CMSerialization cmSerialization = new CMSerialization();

    DisplayTicketFrame(FlightManager fm, CustomerManager cm, TicketManager tm,
                       String flightNum, String d_city, String a_city, LocalDateTime d_time,
                       LocalDateTime a_time, String b_gate, String seat_num, String username,
                       String classType, int ticketPrice, PHManager phm,LuggageManager lm){
        this.cm = cm;
        this.fm = fm;
        this.tm = tm;
        this.phm=phm;
        this.lm=lm;
        this.username = username;
        this.flightNum = flightNum;
        this.ticketPrice = ticketPrice;
        this.classType = classType;
        this.d_city = d_city;
        this.a_city = a_city;
        this.d_time = d_time;
        this.a_time = a_time;
        this.boardingGate = b_gate;
        this.seatNum = seat_num;

        Ticket t = tm.generateTicket(flightNum, d_city, a_city, fm.getFlightByNum(flightNum).getDepartureTime(),
                fm.getFlightByNum(flightNum).getArrivalTime(), b_gate, seat_num,
                ticketPrice, cm.showCustomer(username).getName(), username, classType);
        tm.bookTickets(t);
        this.t_id = t.getTicket_id();
        this.cm.decrBalance(ticketPrice,cm.showCustomer(this.username));
        this.cm.incrMileage(this.tm.getMileage(t,this.fm),cm.showCustomer(username));
        this.cm.calculateRedeemPoint(cm.showCustomer(this.username));
        this.fm.reserveSeat(this.flightNum, this.seatNum);
        this.cm.showCustomer(this.username).getPurchaseHistory().addPurchasedTickets(t, cm.showCustomer(this.username));
        this.phm.updateHistory(cm.showCustomer(this.username).getPurchaseHistory());// update purchase history

        flightSerialization.saveFM(this.fm,"FlightManager.ser"); // save FM
        ticketSerialization.saveTM(this.tm,"TicketManager.ser");//save TM
        phmSerialiazation.savePHM(this.phm,"PHManager.ser");//save PHM
        cmSerialization.saveCM(this.cm, "CMManager.ser");

        //        String msg = t.toString();
        DateTimeFormatter FormatObj = DateTimeFormatter.ofPattern("yyyy MMM dd  HH:mm:ss");
        String formattedArrivalTime = fm.getFlightByNum(flightNum).getArrivalTime().format(FormatObj);
        String formattedDepartureTime = fm.getFlightByNum(flightNum).getDepartureTime().format(FormatObj);
        String msg = "<html> --------Air Ticket--------" +
                "<br/> <br/> Name of Passenger: " + cm.showCustomer(username).getName() +
                "<br/> <br/> Flight: " + flightNum +
                "<br/> <br/> Seat: " + seat_num + " <br/><br/> Class Type: " + classType +
                "<br/> <br/>From " + d_city + " to " + a_city +
                "<br/> <br/> Departure time: " + formattedDepartureTime +
                "<br/> <br/>Estimate arrival time: " + formattedArrivalTime +
                "<br/> <br/> Boarding Gate: " + b_gate +
                "<br/> <br/> Price: $" + ticketPrice +
                "<br/> <br/>Boarding time will be one hour before departure." +
                "<br/> <br/>And gate closes 20 minutes before departure." +
                "<br/> <br/>Have a nice trip!" +
                "<br/> -----------------------<html>";
        label2.setText(msg);

        label.setFont(new Font("Times", Font.BOLD,40));
        label.setForeground(Color.white);
        label2.setFont(new Font("Times", Font.PLAIN,20));

        buttonFinish.addActionListener(this);

        panel1.setBounds(250, 250, 250, 200);
        panel1.setBackground(Color.white);
        panel1.add(label2);

        panel2.setBackground(new Color(0, 76, 153));
        panel2.setPreferredSize(new Dimension(100, 100));
        panel2.add(label);

        panel3.setBackground(Color.white);
        panel3.setPreferredSize(new Dimension(100, 50));
        panel3.add(buttonFinish);

        this.setTitle("U-Ticket Booking System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 800);
        this.setLocation(new Point(500, 300));
        this.setVisible(true);
        this.add(panel1);
        this.add(panel2, BorderLayout.NORTH);
        this.add(panel3, BorderLayout.SOUTH);







    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== buttonFinish ) {
            this.dispose();
            Luggage_Meal_Main lmm = new Luggage_Meal_Main(this.cm, this.fm, this.tm,
                    this.username, this.phm, this.lm, this.t_id);
        }
    }

}
