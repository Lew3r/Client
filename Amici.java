package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Amici extends JFrame implements ActionListener {
    static int indiceutente = 0;
    public final static ArrayList<JButton> amici = new ArrayList<JButton>();
    static Container areaCentrale;

    public Amici() throws IOException {
        super("ListaAmici");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width/4, height);
        setLocation ( ( width-getWidth()),height-getHeight());
        areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        this.addWindowListener((new Ascoltatore()));

    }

    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();

    }
    public void aggiungerebottone(String utente) throws IOException {

        JButton bottone = new JButton(utente);
        amici.add(bottone);
        areaCentrale.add(amici.get(indiceutente));
        amici.get(indiceutente).setVisible(true);
        amici.get(indiceutente).addActionListener(this);
        amici.get(indiceutente).setActionCommand(null);
        indiceutente++;
        this.revalidate();
        this.repaint();
        collegautente(utente);
    }
    public void collegautente(String utente)
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
                  int trovato=0;
                  int indicetemporaneo=-1;
                  for(int i=0;i<Utenti.returnUser().size();i++)
                  {
                      if(Utenti.returnUser().get(i).getText().equalsIgnoreCase(utente))
                      {
                          trovato=1;
                          indicetemporaneo=i;
                          break;
                      }

                  }
                  if(trovato==1)
                    Utenti.aggiungiutenti(utente,0);
                  else
                      Utenti.aggiungiutenti(utente,1);
              }

    }

}

