package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;




public class Registrazione extends JFrame implements ActionListener{

    JTextField nomeutente;
    JTextField password;
    JButton inviaDati;
    public Registrazione() {
        super("Registrazione");
        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container areaCentrale = getContentPane();
        nomeutente=new JTextField();
        password=new JTextField();
        inviaDati = new JButton("Ok");
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(nomeutente);
        areaCentrale.add(password);
        areaCentrale.add(inviaDati);
        inviaDati.addActionListener(this);
        inviaDati.setActionCommand(null);

    }
    public void actionPerformed(ActionEvent e)
    {
        String com = e.getActionCommand();
        if(com=="Ok")
        {
            try {
                connettiAlDatabase();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            }
        }


    }
    public void connettiAlDatabase() throws IllegalAccessException, InstantiationException {
        try {
            // Load the Mysql JDBC driver
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (ClassNotFoundException e) {
                System.out.println ("Mysql device driver does not exist");
                System.exit(1);
            } // Connect to the database
            // You can put a database name after the @ sign in the connection URL.
            Connection conn = DriverManager.getConnection ("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11188231?verifyServerCertificate=false&useSSL=true","sql11188231","aQm46gC9rK");
            // Create a Statement
            Statement stmt = conn.createStatement ();
            // Select the ENAME column from the EMP table
            //ResultSet rset = stmt.executeQuery (" select ID, Cognome, Nome, Tel , Email,Homepage  from Persone");
            //StringBuffer buf = new StringBuffer();
            // Close the RseultSet
            String utente= nomeutente.getText();
            String pass= password.getText();
            ResultSet rs = stmt.executeQuery("select nomeutente from persone where nomeutente='"+utente+"'");
            String ut="nessunnomeutente";
            while (rs.next())
            {
                ut= rs.getString("nomeutente");              ;

            }
            rs.close();
            if(ut.equals("nessunnomeutente")) {
                stmt.executeUpdate("INSERT INTO persone (nomeutente,password) VALUES ('"+utente+"','"+pass+"')");
                JOptionPane.showMessageDialog(null, "NomeUtente  registrato");
                rs.close();
                this.dispose();

            }
            else {
                JOptionPane.showMessageDialog(null, "NomeUtente già registrato");
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
}
