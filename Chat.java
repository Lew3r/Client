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
    public final static String sceltautente = "scegliere";
    public static String utente;
    public static String message;
    static int indiceChat = -1;
    static JTextPane chatText;
    static JTextArea destinatario;
    static JTextArea messDaInviare;
    static JButton aggiungiamico;
    JLabel testo;
    JLabel dest;
    JLabel testodainviare;
    static Socket socket;
    static DataOutputStream os;
    static JButton inviachat;
    JButton scegliUtente;

    public Chat() throws IOException {
        super("Chat");
        setSize(300, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        setBackground(UIManager.getColor("control"));
        chatText = new JTextPane();
        chatText.setEditable(false);
        messDaInviare = new JTextArea();
        destinatario = new JTextArea();
        aggiungiamico=new JButton("cerca amico");
        scegliUtente = new JButton("Scegli utente");
        inviachat = new JButton("Invia messaggio");
        dest = new JLabel("inserire username destinatario");
        testodainviare = new JLabel("inserire testo da inviare");
         setLayout(new GridLayout(10,1));
         testo = new JLabel("chat");
        JScrollPane scrollPane = new JScrollPane(chatText);
        JScrollPane scrollPane2 = new JScrollPane(messDaInviare);
         add(aggiungiamico);
         add(testo);
         add(scrollPane);
         add(testodainviare);
         add(scrollPane2);
         add(dest);
         add(destinatario);
         add(inviachat);
        inviachat.addActionListener(this);
        inviachat.setActionCommand(this.inviare);
        aggiungiamico.addActionListener(this);
        aggiungiamico.setActionCommand(null);
        socket = new Socket("localhost", 2000);
        os = new DataOutputStream(socket.getOutputStream());
        scrivi();

    }
    public static void set()
    {
        messDaInviare.setText("ciao");
    }

    public static Socket returnsocket() {
        return socket;
    }

    public static DataOutputStream returndata() {
        return os;
    }

    public static void settadestinatario(String dest) {
        destinatario.setText(dest);
    }

    public void scrivi() throws IOException {
        os.writeBytes(Username.returnUsername() + '\n');
        Lettura lettura = new Lettura(socket);
        new Thread(lettura).start();

    }

    public static void inviadata(String richiesta) throws IOException {
        os.writeBytes(richiesta + '\n');
    }

    public void actionPerformed(ActionEvent e) {

        String com = e.getActionCommand();
        Utenti ut = null;
        if (com == inviare) {
            String mesdainviare = messDaInviare.getText();

            try {
                os.writeBytes(mesdainviare + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String mesdainviare2 = destinatario.getText();
            try {
                Utenti.appendchatrelativablue(mesdainviare, mesdainviare2);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
            try {
                os.writeBytes(mesdainviare2 + '\n');
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            utente = mesdainviare;
            message = mesdainviare2;
        }
        if(com=="cerca amico")
        {
            VediAmici vediAmici= null;
            try {
                vediAmici = new VediAmici();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            vediAmici.setVisible(true);
        }
    }

    public static void enableInviaChat() {
        inviachat.setVisible(true);
    }

    public static void aggiornaIndice(int indice) {
        indiceChat = indice;
    }

    public static int returnIndice() {
        return indiceChat;
    }

    public static void settaT(String messaggio) throws IOException, BadLocationException {
        char a = '%';
        char b = '$';
        if (messaggio.charAt(0) != ('£')) {
            String mess = null, us = null, user = null;

            mess = messaggio.substring(0, messaggio.indexOf(a));
            us = messaggio.substring(messaggio.indexOf(a) + 1, messaggio.indexOf(b));
            user = messaggio.substring(messaggio.indexOf(b) + 1);

            if (!(messaggio.equals("Impossibile mandare messaggio" + "%" + "nessun unsername"))) {
                //chatText.append(mess);
                Utenti.incrementabottoni(us, mess, user);
            }
            utente = us;
            message = mess;
        } else {

            if (messaggio.charAt(1) == ('$')) {
                String mes = messaggio.substring(2);
                Utenti.incrementabottoni("£", "£", mes);
            } else {
                String mes = messaggio.substring(1);
                Utenti ut = new Utenti();
                ut.setVisible(true);
                Utenti.incrementabottoni("£", "£", mes);
                ut.getUser();

            }
        }

    }

    public static void append(String messaggio) {
        StyledDocument doc = chatText.getStyledDocument();
        Style style = chatText.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.red);

        try {
            doc.insertString(doc.getLength(), messaggio + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void appendblue(String messaggio) {
        StyledDocument doc = chatText.getStyledDocument();
        Style style = chatText.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, Color.blue);


        try {
            doc.insertString(doc.getLength(), messaggio + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }


    }

    public static String returnUtente() {
        return utente;
    }

    public static void settaggioChat(String messaggio) {


        chatText.setText("");
        String temp;
        if(!(messaggio.equals(""))) {
            temp = messaggio.substring(1);
            while (temp.indexOf("£") != -1 || temp.indexOf("$") != -1) {
                temp = messaggio.substring(1);
                String testo;
                int indicedoll = messaggio.indexOf("$");
                int indicester = messaggio.indexOf("£");
                int indicedoll2 = temp.indexOf("$");
                int indicester2 = temp.indexOf("£");
                if (indicedoll == -1)
                    indicedoll = 100000000;
                if (indicester == -1)
                    indicester = 100000000;
                if (indicedoll2 == -1)
                    indicedoll2 = 100000000;
                if (indicester2 == -1)
                    indicester2 = 100000000;

                if (indicedoll < indicester) {
                    if (indicedoll2 < indicester2) {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicedoll2);
                            append(testo);
                            messaggio = temp.substring(indicedoll2);
                        }

                    } else {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicester2);
                            append(testo);
                            messaggio = temp.substring(indicester2);
                        }

                    }
                } else {
                    if (indicedoll2 < indicester2) {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicedoll2);
                            appendblue(testo);
                            messaggio = temp.substring(indicedoll2);
                        }
                    } else {
                        if (indicedoll2 != indicester2) {
                            testo = temp.substring(0, indicester2);
                            appendblue(testo);
                            messaggio = temp.substring(indicester2);
                        }
                    }
                }

            }
           if(messaggio.charAt(0)=='$')
                append(messaggio.substring(1));
            else
            appendblue(messaggio.substring(1));
        }
    }
}
