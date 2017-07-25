package Client;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.text.Style;


public class Utenti extends JFrame implements ActionListener {
    public final static String inviare1 = "socket1";
    public final static String inviare2 = "socket2";
    public final static String inviare3 = "socket3";
    public final static String inviare4 = "socket4";
    public final static String aggiorna = "aggiorna";
    static int indiceutente=0;
    String bottonepremuto;

    JButton aggiornalista;
    public final static ArrayList<JButton> user= new ArrayList<JButton>();
    static Container areaCentrale;
    static int indice=0;
    public static ArrayList<JTextPane> testoUser=new ArrayList<JTextPane>();
    JButton bottone;

    public Utenti() throws IOException {
        super("Chat");
        setSize(300, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        aggiornalista = new JButton("aggiorna");
        areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(aggiornalista);
        aggiornalista.addActionListener(this);
        aggiornalista.setActionCommand(this.aggiorna);

        //for(int i=0;i<5;i++) {
            //user[i]=(bottone = new JButton("null"));
            //areaCentrale.add(user[i]).setVisible(false);
           // user[i].addActionListener(this);

        //user[0].setActionCommand(this.inviare1);
        //user[1].setActionCommand(this.inviare2);
        //user[2].setActionCommand(this.inviare3);
        //user[3].setActionCommand(this.inviare4);
    }
    public static void aggiungiutenti(String utente)
    {
        int trovato=0;

        for(int i=0;i<user.size();i++)
        {
            System.out.println("bonucci"+user.get(i).getText()+"ranocchia"+utente);
           if(user.get(i).getText().equals(utente))
                trovato=1;
        }
        if(trovato==0) {
            JButton bottone = new JButton(utente);
            user.add(bottone);
            areaCentrale.add(user.get(indiceutente));
            user.get(indiceutente).setVisible(true);
            indiceutente++;
            JTextPane pane = new JTextPane();
            testoUser.add(pane);
        }
    }

    public void getUser() {
        for (int k=0;k<user.size();k++)
        {
            user.get(k).addActionListener(this);
            user.get(k).setActionCommand(null);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();

        if(com==aggiorna) {
            String richiesta = "$richiestausername$";
            System.out.println("dybala"+ richiesta);
            try {
                Chat.inviadata(richiesta);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else
       {    int indice = returnindice(com);
            if(indice!=-1) {
                Chat.settadestinatario(user.get(indice).getText());
                Chat.settaggioChat(testoUser.get(indice).getText());
                Chat.aggiornaIndice(indice);
            }
        }

        Chat.enableInviaChat();

    }
    public int returnindice(String com)
    {
        for(int i=0;i< user.size();i++)
        {
            if(user.get(i).getText().equals(com))
                return i;
        }
        return -1;
    }


    public static void incrementabottoni(String utente, String mess,String tuttiutenti) throws BadLocationException {
        char b = '$';
        System.out.println("ciao" + tuttiutenti);

        for (int i = 0; !((tuttiutenti.equals('$') || tuttiutenti.equals(""))); i++) {
            System.out.println("aaaaa " + tuttiutenti);
            String ut = tuttiutenti.substring(0, tuttiutenti.indexOf(b));
            System.out.println(("test" + ut));
            aggiungiutenti(ut);
            tuttiutenti = tuttiutenti.substring(tuttiutenti.indexOf(b));
            tuttiutenti = tuttiutenti.substring(1);
        }

               appendchatrelativa(mess,utente);


    }
    public static void appendchatrelativa(String messaggio,String destinatario) throws BadLocationException {
        for (int i = 0; i<user.size(); i++) {
            if (user.get(i).getText().equals(destinatario)) {
                    StyledDocument doc = testoUser.get(i).getStyledDocument();
                    Style style = testoUser.get(i).addStyle("I'm a Style", null);
                    StyleConstants.setForeground(style, Color.red);
                    doc.insertString(doc.getLength(), "$"+messaggio, style);

            }
        }
    }
    public static void appendchatrelativablue(String messaggio,String destinatario) throws BadLocationException {
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getText().equals(destinatario)) {
                StyledDocument doc = testoUser.get(i).getStyledDocument();
                Style style = testoUser.get(i).addStyle("I'm a Style", null);
                StyleConstants.setForeground(style, Color.blue);
                doc.insertString(doc.getLength(), "£" + messaggio, style);
            }
        }
    }
}