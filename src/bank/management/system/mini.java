package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class mini extends JFrame implements ActionListener {

    String pin;
    JButton button;

    mini(String pin) {

        this.pin = pin;

        setTitle("Mini Statement");

        getContentPane().setBackground(new Color(255, 204, 204));
        setSize(400, 600);
        setLocation(20, 20);
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20, 140, 350, 250);
        add(label1);

        JLabel label2 = new JLabel("HarshCoder H.P");
        label2.setFont(new Font("System", Font.BOLD, 15));
        label2.setBounds(140, 20, 200, 20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20, 80, 350, 20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20, 420, 300, 20);
        label4.setFont(new Font("System", Font.BOLD, 14));
        add(label4);

        // Fetch Card Number
        try {

            Con c = new Con();

            ResultSet resultSet =
                    c.statement.executeQuery(
                            "select * from login where pin = '" + pin + "'");

            while (resultSet.next()) {

                String card = resultSet.getString("card_number");

                label3.setText(
                        "Card Number: "
                                + card.substring(0, 4)
                                + "XXXXXXXX"
                                + card.substring(card.length() - 4)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Fetch Transaction History
        try {

            int balance = 0;

            Con c = new Con();

            ResultSet resultSet =
                    c.statement.executeQuery(
                            "select * from bank where pin = '" + pin + "'");

            StringBuilder miniStatement = new StringBuilder();

            while (resultSet.next()) {

                miniStatement.append("<html>")
                        .append(resultSet.getString("date"))
                        .append("&nbsp;&nbsp;&nbsp;")
                        .append(resultSet.getString("type"))
                        .append("&nbsp;&nbsp;&nbsp;Rs ")
                        .append(resultSet.getString("amount"))
                        .append("<br><br></html>");

                if (resultSet.getString("type").equalsIgnoreCase("Deposit")) {

                    balance += Integer.parseInt(
                            resultSet.getString("amount"));

                } else {

                    balance -= Integer.parseInt(
                            resultSet.getString("amount"));
                }
            }

            label1.setText(miniStatement.toString());

            label4.setText("Your Total Balance is Rs " + balance);

        } catch (Exception e) {
            e.printStackTrace();
        }

        button = new JButton("Exit");
        button.setBounds(140, 500, 100, 30);
        button.addActionListener(this);
        add(button);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {
            setVisible(false);
        }
    }

    public static void main(String[] args) {

        new mini("4832");
    }
}