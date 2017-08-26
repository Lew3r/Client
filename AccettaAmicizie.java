package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AccettaAmicizie extends JFrame implements ActionListener {
    public final static ArrayList<JButton> amiciziedaaccettare = new ArrayList<JButton>();
    static Container areaCentrale;
    String username;
    Connection conn;
    Statement stmt;
    int indice=0;

    public AccettaAmicizie(String username, Connection conn, Statement stmt) throws SQLException {
        super("RichiesteAmici");
        this.username=username;
        this.conn=conn;
        this.stmt=stmt;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width / 4, height);
        setLocation((width - getWidth()), height - getHeight());
        areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        aggiungirichieste(username,conn,stmt);
        setVisible(true);
    }
    public void aggiungirichieste(String username, Connection conn, Statement stmt) throws SQLException {

        String ut1, ut2;
        int ac=0;



         ResultSet rs = stmt.executeQuery("select utente1,utente2,accettata from amici where utente2='" + username + "'");
        while (rs.next()) {
            ut1 = rs.getString("utente1");
            ut2 = rs.getString("utente2");
            ac=rs.getInt("accettata");
            if( ac==1)
            {
                JButton amicizia=new JButton(ut1);
                amiciziedaaccettare.add(amicizia);
                areaCentrale.add(amiciziedaaccettare.get(indice));
                amiciziedaaccettare.get(indice).setVisible(true);
                amiciziedaaccettare.get(indice).addActionListener(this);
                amiciziedaaccettare.get(indice).setActionCommand(null);
                indice++;
                this.revalidate();
                this.repaint();
            }


        }
        rs.close();
        stmt.close();
        conn.close();

    }
    public void actionPerformed(ActionEvent e) {
        int elementodaeliminare=-1;
        String com = e.getActionCommand();
        try {
            Database database =VediAmici.connessioneDatabase();
            database.returnStatement().executeUpdate("UPDATE amici SET accettata =2 WHERE utente1='"+username+"'  && utente2='"+com+"'");
            database.returnStatement().executeUpdate("UPDATE amici SET accettata =2 WHERE utente2='"+username+"'  && utente1='"+com+"'");
            elementodaeliminare=trovaindice(com);
            areaCentrale.remove(amiciziedaaccettare.get(elementodaeliminare));
            amiciziedaaccettare.remove(elementodaeliminare);
            indice--;
            this.revalidate();
            this.repaint();
        } catch (SQLException e1) {

        }


    }
    public int trovaindice(String com)
    {   int indice =0;
        for(int i =0;i<amiciziedaaccettare.size();i++)
            if(com.equalsIgnoreCase(amiciziedaaccettare.get(i).getText()))
            {
                indice=i;
                break;
            }
                return indice;
    }
}
