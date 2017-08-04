package Client;

import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Utenti extends JFrame implements ActionListener {
    public final static String aggiorna = "aggiorna";
    static int indiceutente=0;
    JButton aggiornalista;
    public final static ArrayList<JButton> user= new ArrayList<JButton>();
    static Container areaCentrale;
    static int indice=0;
    public static ArrayList<JTextPane> testoUser=new ArrayList<JTextPane>();

    public Utenti() throws IOException {
        super("Utenti");
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width/4, height);
        setLocation ( ( width-getWidth())*3/4,height-getHeight());               ;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        aggiornalista = new JButton("aggiorna");
        areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(aggiornalista);
        this.addWindowListener((new Ascoltatore()));
        aggiornalista.addActionListener(this);
        aggiornalista.setActionCommand(this.aggiorna);

    }
    public static void aggiungiutenti(String utente,int attivo)
    {
        int trovato=0;
        int indicetemporaneo=-1;
        for(int i=0;i<user.size();i++)
        {
            if(user.get(i).getText().equalsIgnoreCase(utente))
            {
                trovato=1;
                indicetemporaneo=i;
                break;

            }

        }
        if(trovato==0) {
            JButton bottone = new JButton(utente);
            if(attivo==0)
                bottone.setBackground(Color.blue);
            else
                bottone.setBackground(Color.red);
            user.add(bottone);
            areaCentrale.add(user.get(indiceutente));
            user.get(indiceutente).setVisible(true);
            indiceutente++;
            JTextPane pane = new JTextPane();
            testoUser.add(pane);
            try {
                letturatesto(utente);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            if(attivo==0)
            {
                user.get(indicetemporaneo).setBackground(Color.blue);
            }
            else
            {
                user.get(indicetemporaneo).setBackground(Color.red);
            }
    }
    public static void decrementaindicedisconnessione(String utente)
    {
        System.out.println(("utente"+utente));
        System.out.println("indice utente"+returnindice(utente)) ;
        int indiceutente=returnindice(utente);
        areaCentrale.remove(testoUser.get(indiceutente));
        areaCentrale.remove(user.get(indiceutente));
        testoUser.remove(indiceutente);
        user.remove(indiceutente);
        indiceutente--;
    }
    public static void letturatesto(String utente) throws IOException {
        String firstName=Username.returnUsernameStringa();
        String secondName=utente;
        File file = new File(firstName+"$"+secondName+".pdf");
        if(file.exists())
        {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            caricaTestoDaFile(text);
        }
    }
    public static void caricaTestoDaFile(String messaggio) {
        String temp;
        int indirizzo = returnindice("Lew3r");testoUser.get(indirizzo).setText(messaggio);

    }

    public void getUser() {
        for (int k=0;k<user.size();k++)
        {
            user.get(k).addActionListener(this);
            user.get(k).setActionCommand(null);
        }
    }

    public static ArrayList<JTextPane> getTestoUser() {
        return testoUser;
    }


    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();

        if(com==aggiorna) {
            String richiesta = "$richiestausername$";
            try {
                Chat.inviadata(richiesta);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            this.revalidate();
            this.repaint();
        }
        else
       {    int indice = returnindice(com);
            if(indice!=-1) {
                Chat.aggiornaIndice(indice);
                Chat.settadestinatario(user.get(indice).getText());
                Chat.settaggioChat(testoUser.get(indice).getText());
                getUser();
                Chat.enableInviaChat();
            }
        }




    }
    public static int returnindice(String com)
    {
        for(int i=0;i< user.size();i++)
        {
            if(user.get(i).getText().equalsIgnoreCase(com))
                return i;
        }
        return -1;
    }
    public static ArrayList<JButton> returnUser()
    {
        return user;
    }
    public static ArrayList<JTextPane> returnTestoUser()
    {
        return testoUser;
    }


    public static void incrementabottoni(String utente, String mess,String tuttiutenti) throws BadLocationException {
        char b = '$';

        for (int i = 0; !((tuttiutenti.equals('$') || tuttiutenti.equals(""))); i++) {
            String ut = tuttiutenti.substring(0, tuttiutenti.indexOf(b));
            aggiungiutenti(ut,0);
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
                    if(Chat.returnIndice()==i)
                {
                    Chat.settaggioChat(testoUser.get(i).getText());
                }


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
                if(Chat.returnIndice()==i)
                {
                    Chat.settaggioChat(testoUser.get(i).getText());
                }
            }

        }
    }
}