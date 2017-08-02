package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class VediAmici extends JFrame implements ActionListener {
    static int indiceutente = 0;
    JButton verifica;
    static JTextField nominativo;
    static Container areaCentrale;
    Amici amici;

    public VediAmici() throws IOException {
        super("CercaAmici");
        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        verifica = new JButton("cerca");
        nominativo=new JTextField(5);
        areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(nominativo);
        areaCentrale.add(verifica);
        verifica.addActionListener(this);
        verifica.setActionCommand(null);
        amici =new Amici();
    }

    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if(com=="cerca")
        {
            try {
                connDatatbase(nominativo.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
    public void connDatatbase(String utentedaggiungere) throws IOException {
        String ut = "nessunnomeutente";
        try {

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (ClassNotFoundException e) {
                System.out.println("Mysql device driver does not exist");
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            Connection conn = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11188231?verifyServerCertificate=false&useSSL=true", "sql11188231", "aQm46gC9rK");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select nomeutente from persone where nomeutente='" + utentedaggiungere + "'");
            while (rs.next()) {
                ut = rs.getString("nomeutente");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error accessing DB ");
            System.out.println("  Error code is : " + e.getErrorCode());
            System.out.println("  Error message is :" + e.getMessage());

        }
            if(!(ut.equals("nessunnomeutente")))
            {

                amici.setVisible(true);
                amici.aggiungerebottone(utentedaggiungere);
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "NomeUtente non esistente");
            }
    }


}