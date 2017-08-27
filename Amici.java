package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static Client.Chat.utente;

public class Amici extends JFrame implements ActionListener {
    static int indiceutente = 0;
    public final static ArrayList<JButton> amici = new ArrayList<JButton>();
    static Container areaCentrale;

    public Amici() throws IOException, IllegalAccessException, SQLException, InstantiationException {
        super("ListaAmici");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width/4, height);
        setLocation ( ( width-getWidth()),height-getHeight());
        areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        controlloamicizie();
        setVisible(true);
        this.addWindowListener((new Ascoltatore()));

    }

    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        int indice=cercaamico(com);
        try {
            eliminazioneDatabase(amici.get(indice).getText());
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        areaCentrale.remove(indice);
        amici.remove(indice);
        this.revalidate();
        this.repaint();


    }
    public int cercaamico(String com)
    {
        for(int i=0;i<amici.size();i++)
        {
            if(com.equalsIgnoreCase(amici.get(i).getText()))
                return i;
        }
        return -1;
    }
    public void eliminazioneDatabase(String utentedaeliminare) throws IllegalAccessException, InstantiationException, SQLException {
        String utente1 = Username.returnUsername();
        String ut1, ut2;
        int ac=0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Mysql device driver does not exist");
            System.exit(1);
        }
        Connection conn = DriverManager.getConnection("jdbc:mysql://64.137.197.183:3306/DatabaseChat", "lew3r", "Qwertyuiop1!");
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE  from amici where utente1='" + utente1 + "' && utente2='"+utentedaeliminare +"'");
        stmt.executeUpdate("DELETE  from amici where utente1='" + utentedaeliminare + "' && utente2='"+utente1 +"'");
   }


    public void aggiungerebottone(String utente) throws IOException {
        collegautente(utente);
        JButton bottone = new JButton(utente);
        amici.add(bottone);
        areaCentrale.add(amici.get(indiceutente));
        amici.get(indiceutente).setVisible(true);
        amici.get(indiceutente).addActionListener(this);
        amici.get(indiceutente).setActionCommand(null);
        indiceutente++;
        this.revalidate();
        this.repaint();
    }

    public  void controlloamicizie() throws IllegalAccessException, InstantiationException, SQLException, IOException {
        String utente1 = Username.returnUsername();
        String ut1, ut2;
        int ac=0;
        try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            }
            catch (ClassNotFoundException e) {
                System.out.println("Mysql device driver does not exist");
                System.exit(1);
            }
            Connection conn = DriverManager.getConnection("jdbc:mysql://64.137.197.183:3306/DatabaseChat", "lew3r", "Qwertyuiop1!");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select utente1,utente2,accettata from amici where utente1='" + utente1 + "'");
            while (rs.next()) {
                ut1 = rs.getString("utente1");
                ut2 = rs.getString("utente2");
                ac=rs.getInt("accettata");
                if( ac==2) {
                    aggiungerebottone(ut2);
                    this.revalidate();
                    this.repaint();
                }
            }
            rs.close();
            rs = stmt.executeQuery("select utente1,utente2,accettata from amici where utente2='" + utente1 + "'");
            while (rs.next()) {
                ut1 = rs.getString("utente1");
                ut2 = rs.getString("utente2");
                ac=rs.getInt("accettata");
                if( ac==2) {
                    aggiungerebottone(ut1);
                    this.revalidate();
                    this.repaint();
                }
            }
            rs.close();
            stmt.close();
            conn.close();
    }
    public   void collegautente(String utente)
    {
            String richiesta = "$richiestausername$";
            try {
                Chat.inviadata(richiesta);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            for(int indice=0;indice<Utenti.returnUser().size();indice++)
              if ( Utenti.returnUser().get(indice).getText().equalsIgnoreCase(utente))
                {
                    Chat.aggiornaIndice(indice);
                    Chat.settadestinatario(Utenti.returnUser().get(indice).getText());
                    Chat.settaggioChat(Utenti.returnTestoUser().get(indice).getText());
                    Chat.enableInviaChat();
                }
                else
              {
                  int trovato=1;
                  for(int i=0;i<Utenti.returnUser().size();i++)
                  {
                      if(Utenti.returnUser().get(i).getText().equalsIgnoreCase(utente))
                      {
                          System.out.println(Utenti.returnUser().get(i).getText());
                          if(Utenti.returnUser().get(i).getBackground()==Color.red)
                            trovato=1;
                          else
                              trovato=0;
                          break;
                      }

                  }
                  Utenti.aggiungiutenti(utente,trovato);

              }

    }

}

