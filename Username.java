package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;


public class Username extends JFrame implements ActionListener {
    public final static String inviare = "InviaUsername";
    static JTextField username;
    static JTextField password;
    static JLabel user;
    static JLabel pass;
    static String usernameStringa;
    static JButton inviaUsername;

    public Username() {
        super("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container areaCentrale = getContentPane();
        username = new JTextField(15);
        password = new JTextField(15);
        user =new JLabel("nome utente");
        pass =new JLabel("password");
        inviaUsername = new JButton("Conferma Username");
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(user);
        areaCentrale.add(username);
        areaCentrale.add(pass);
        areaCentrale.add(password);
        areaCentrale.add(inviaUsername);
        inviaUsername.addActionListener(this);
        inviaUsername.setActionCommand(this.inviare);

    }
    public static String returnUsername()
    {
        return usernameStringa;
    }

    public void actionPerformed(ActionEvent e) {

        String com = e.getActionCommand();
        if (com == inviare) {
            try {
                connettidatabase();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            }

        }
    }
    public void connettidatabase() throws IllegalAccessException, InstantiationException {
        try {
        // Load the Mysql JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println ("Mysql device driver does not exist");
            System.exit(1);
        } // Connect to the database
        // You can put a database name after the @ sign in the connection URL.
        Connection conn = DriverManager.getConnection ("jdbc:mysql://64.137.197.183:3306/DatabaseChat", "lew3r", "Qwertyuiop1!");
        // Create a Statement
        Statement stmt = conn.createStatement ();
        // Select the ENAME column from the EMP table
        //ResultSet rset = stmt.executeQuery (" select ID, Cognome, Nome, Tel , Email,Homepage  from Persone");
        //StringBuffer buf = new StringBuffer();
        // Close the RseultSet
        String utente= username.getText();
        String pass= password.getText();
        ResultSet rs = stmt.executeQuery("select nomeutente,password from persone where nomeutente='"+utente+"'");
        String ut="nessunnomeutente";
        String ps="nessunapassword";
        while (rs.next())
        {
            ut= rs.getString("nomeutente");
            ps=rs.getString("password");

        }
        rs.close();
        if(ut.equals("nessunnomeutente")) {
            JOptionPane.showMessageDialog(null, "NomeUtente non presente nel database");
            rs.close();

        }
        else {
            if(ps.equals(pass))

            {
                JOptionPane.showMessageDialog(null, "NomeUtente e Password corretti");
                usernameStringa = username.getText();
                Chat chat = null;
                try {

                    chat = new Chat();
                    setVisible(false);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                chat.setVisible(true);

            }
            else
            {
                JOptionPane.showMessageDialog(null, "Password errata");
            }
        }
        // Close the Statement
        stmt.close();
        // Close the connection
        conn.close();
        } catch (SQLException e) {
        System.out.println("Error accessing DB ");
        System.out.println("  Error code is : "+e.getErrorCode());
        System.out.println("  Error message is :"+e.getMessage());
    }
    }
    public static String returnUsernameStringa()
    {
        return usernameStringa;
    }

}


