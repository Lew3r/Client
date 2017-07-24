package Client;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.lang.String;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;



public class Chat extends JFrame implements ActionListener {
    public final static String inviare = "invia";
    public final static String sceltautente="scegliere";
    public static String utente;
    public static String message;
    static JTextPane chatText;
    static JTextArea destinatario;
    static JTextArea messDaInviare;
    JLabel testo;
    JLabel dest;
    JLabel testodainviare;
    Socket socket;
    DataOutputStream os;
    static JButton inviachat;
    JButton scegliUtente;
     public Chat() throws IOException {
         super("Chat");
        setSize(300, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container areaCentrale = getContentPane();
        chatText = new JTextPane();;
        messDaInviare =new JTextArea();
        destinatario=new JTextArea();
        chatText.setEditable(false);
        scegliUtente= new JButton("Scegli utente");
        inviachat = new JButton("Invia messaggio");
        testo=new JLabel("chat");
        dest=new JLabel("inserire username destinatario");
        testodainviare=new JLabel("inserire testo da inviare");
        chatText.setContentType("text/html");
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(testo);
        areaCentrale.add(chatText);
        areaCentrale.add(testodainviare);
        areaCentrale.add(messDaInviare);
        areaCentrale.add(dest);
        areaCentrale.add(destinatario);
        areaCentrale.add(inviachat).setVisible(false);
        inviachat.addActionListener(this);
        inviachat.setActionCommand(this.inviare);
        socket = new Socket("localhost", 2000);
        os = new DataOutputStream(socket.getOutputStream());
        scrivi();

    }
    public static void settadestinatario(String dest)
    {
        destinatario.setText(dest);
    }
    public void  scrivi() throws IOException {
        System.out.println("username"+Username.returnUsername());
        os.writeBytes(Username.returnUsername() + '\n');
        Lettura lettura =new Lettura(socket);
         new Thread(lettura).start();

    }

    public void actionPerformed(ActionEvent e) {

        String com = e.getActionCommand();
        Utenti ut = null;
        if (com == inviare) {
            String mesdainviare = messDaInviare.getText();
            System.out.println(mesdainviare);
            appendinviato(mesdainviare);


            try {
                os.writeBytes(mesdainviare + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String mesdainviare2 = destinatario.getText();
            try {
                Utenti.appendchatrelativa(mesdainviare,mesdainviare2);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
            System.out.println(mesdainviare2);
            try {
                os.writeBytes(mesdainviare2 + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            utente = mesdainviare;
            message = mesdainviare2;
        }
     }
    public static void enableInviaChat()
    {
        inviachat.setVisible(true);
    }

    public static void settaT(String messaggio) throws IOException, BadLocationException {
        char a='%';
        char b='$';
        System.out.println(messaggio.charAt(0));
        if(messaggio.charAt(0)!=('£'))
        {
            String mess = null, us = null, user = null;

            mess = messaggio.substring(0, messaggio.indexOf(a));
            us = messaggio.substring(messaggio.indexOf(a) + 1, messaggio.indexOf(b));
            user = messaggio.substring(messaggio.indexOf(b) + 1);
            System.out.println("Prova7" + us);

            if (!(messaggio.equals("Impossibile mandare messaggio" + "%" + "nessun unsername"))) {
                append(mess);
                //chatText.append(mess);
                Utenti.incrementabottoni(us, mess, user);
            } else
                JOptionPane.showMessageDialog(null, "Impossibile mandare messaggio");
            //chatText.append(System.getProperty("line.separator"));
            utente = us;
            message = mess;
            String message = chatText.getText();
        }
        else
        {
            String mes =messaggio.substring(1);
            Utenti  ut = new Utenti();
            ut.show();
            Utenti.incrementabottoni("£","£", mes);
        }
    }   public  static void appendinviato(String messaggio)
    {
        StyledDocument doc= chatText.getStyledDocument();
        Style style = chatText.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.blue);


        try {
            doc.insertString(doc.getLength(), messaggio+"\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    public  static void append(String messaggio)
    {
        StyledDocument doc= chatText.getStyledDocument();
        Style style = chatText.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.red);


        try {
            doc.insertString(doc.getLength(), messaggio+"\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }  public  static void appendblue(String messaggio)
    {
        StyledDocument doc= chatText.getStyledDocument();
        Style style = chatText.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.blue);


        try {
            doc.insertString(doc.getLength(), messaggio+"\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }




    public static String returnUtente()
    {
        return utente;
    }
    public static void settaggioChat(String messaggio)
    {

        int indicedollaro2,indicesterlina2;
        String temp2;
        String temp;
        System.out.println(messaggio);

        if(!(messaggio.equals("")))
        {
            temp=messaggio.substring(1);
           while(temp.indexOf('$')!=-1||temp.indexOf('£')!=-1) {
               temp=messaggio.substring(1);
                indicedollaro2 = temp.indexOf('$');
                indicesterlina2 = temp.indexOf('£');
                if (indicedollaro2 == -1)
                    indicedollaro2 = 1000000000;
                if (indicesterlina2 == -1)
                    indicesterlina2 = 1000000000;
                if (indicedollaro2 < indicesterlina2) {
                    temp2 = temp.substring(0, temp.indexOf('$'));
                    System.out.println("colore rosso "+temp2);
                    append(temp2);
                    messaggio= temp.substring(temp.indexOf('$'));
                     System.out.println("indice temp "+temp.indexOf('$'));
                }
                else {
                    if(indicedollaro2!=indicesterlina2)
                    {
                        temp2 = temp.substring(0, temp.indexOf('£'));
                        System.out.println("colore blue" + temp2);
                        appendblue(temp2);
                        messaggio = temp.substring(temp.indexOf('£'));
                        System.out.println("colore blue rimanente " + temp2);
                    }
                    else {
                        if (messaggio.charAt(0) == '$') {
                            appendblue(temp);
                            break;
                        }
                        else
                        {
                            appendblue(temp);
                            break;
                        }
                    }

                }
            }
         }
         else{
           isempty();
        }

    }
    public static void isempty()
    {
       chatText.setText("");
    }
}
