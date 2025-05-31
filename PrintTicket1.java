import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PrintTicket1 extends JFrame {
    public PrintTicket1(String sFrom, String sTo, String sClass, Integer iAdult, Integer iChildren, Integer iInfant, String sBookingDate, Integer iPrice, String sTime) {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        // Chatbot Button
        JButton Chatbot = new JButton("Chatbot");
        Chatbot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    try {
                        ChatbotAPIClient.main(new String[]{});
                    } catch (Exception ex) {
                        ex.printStackTrace(); 
                    }
                });
            }
        });
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10)); // Rows, Columns, H-Gap, V-Gap
        panel.setBackground(Color.WHITE);

        JLabel LTitle = new JLabel("Airline Ticket", JLabel.CENTER);
        LTitle.setFont(new Font("Arial", Font.BOLD, 24));
        LTitle.setForeground(new Color(199, 21, 133));

        JLabel LFrom = new JLabel("From: " + sFrom);
        JLabel LTo = new JLabel("To: " + sTo);
        JLabel LClass = new JLabel("Class: " + sClass);
        JLabel LBookingDate = new JLabel("Travel Date: " + sBookingDate);
        JLabel LPrice = new JLabel("Total Price: $" + iPrice);
        JLabel LTime = new JLabel("Departure Time: " + sTime);
        JLabel LAdult = new JLabel("Adults: " + iAdult);
        JLabel LChildren = new JLabel("Children: " + iChildren);
        JLabel LInfant = new JLabel("Infants: " + iInfant);
        JLabel LWishes = new JLabel("Wish you a happy journey!");

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        LFrom.setFont(labelFont);
        LTo.setFont(labelFont);
        LClass.setFont(labelFont);
        LBookingDate.setFont(labelFont);
        LPrice.setFont(labelFont);
        LTime.setFont(labelFont);
        LAdult.setFont(labelFont);
        LChildren.setFont(labelFont);
        LInfant.setFont(labelFont);
        LWishes.setFont(new Font("Arial", Font.ITALIC, 14));
        LWishes.setForeground(new Color(210, 180, 140)); 

        panel.add(LFrom);
        panel.add(LTo);
        panel.add(LClass);
        panel.add(LBookingDate);
        panel.add(LAdult);
        panel.add(LChildren);
        panel.add(LInfant);
        panel.add(LPrice);
        panel.add(LTime);
        panel.add(LWishes);

        JPanel chatbotPanel = new JPanel();
        chatbotPanel.add(Chatbot);

        c.add(LTitle, BorderLayout.NORTH);
        c.add(panel, BorderLayout.CENTER);
        c.add(chatbotPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new PrintTicket1("New York", "Los Angeles", "Business", 2, 1, 0, "2025-04-10", 1200, "10:30 AM");
    }
}
