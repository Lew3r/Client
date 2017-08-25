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
    JButton richiesteamicizia;
    static JTextField nominativo;
    static Container areaCentrale;
    Amici amici;

    public VediAmici() throws IOException, IllegalAccessException, SQLException, InstantiationException {
        super("CercaAmici");
        setSize(300, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        verifica = new JButton("cerca");
        richiesteamicizia =new JButton("richieste di amicizia");
        nominativo=new JTextField(5);
        areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(nominativo);
        areaCentrale.add(verifica);
        areaCentrale.add(richiesteamicizia);
        verifica.addActionListener(this);
        verifica.setActionCommand(null);
        richiesteamicizia.addActionListener(this);
        richiesteamicizia.setActionCommand(null);
        amici =new Amici();
    }

    public void actionPerformed(ActionEvent e) {
        Database database=null;
        String com = e.getActionCommand();
        if(com=="cerca")
        {
            try {
                database=connessioneDatabase();
                connDatabase(nominativo.getText(),database.returnConnection(),database.returnStatement());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        if(com=="richieste di amicizia") {

            try {
                database=connessioneDatabase();
                AccettaAmicizie accettaAmicizie = new AccettaAmicizie(Username.returnUsername(), database.returnConnection(), database.returnStatement());
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static Database connessioneDatabase() throws SQLException {
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
            Connection conn = DriverManager.getConnection("jdbc:mysql://64.137.197.183:3306/DatabaseChat", "lew3r", "Qwertyuiop1!");
            Statement stmt = conn.createStatement();
            Database database =new Database(conn,stmt);
            return database;
        }

    public void connDatabase(String utentedaggiungere,Connection conn,Statement stmt) throws IOException, IllegalAccessException, SQLException, InstantiationException {
        String ut = "nessunnomeutente";
        int ac=0;

        try{
            ResultSet rs = stmt.executeQuery("select nomeutente from persone where nomeutente='" + utentedaggiungere + "'");
            while (rs.next()) {
                ut = rs.getString("nomeutente");
               // ac= rs.getInt("accettata");
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("Error accessing DB ");
            System.out.println("  Error code is : " + e.getErrorCode());
            System.out.println("  Error message is :" + e.getMessage());

        }
            if(!(ut.equals("nessunnomeutente")))
            {
                aggiungereadatabase(utentedaggiungere);
                this.dispose();
            }
            else{
                  JOptionPane.showMessageDialog(null, "NomeUtente non esistente");
            }
        stmt.close();
        conn.close();
    }
    public void aggiungereadatabase(String utentedaaggiungere) throws IllegalAccessException, InstantiationException, SQLException {
        String utente1 = Username.returnUsername();
        String ut1, ut2;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Mysql device driver does not exist");
            System.exit(1);
        }
        Connection conn = DriverManager.getConnection("jdbc:mysql://64.137.197.183:3306/DatabaseChat", "lew3r", "Qwertyuiop1!");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO amici (utente1,utente2,accettata) VALUES ('"+utente1+"','"+utentedaaggiungere+"','"+1+"')");
        stmt.close();
        conn.close();
    }


}