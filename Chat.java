package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.lang.String;

public class Chat extends JFrame implements ActionListener {
    public final static String inviare = "invia";
    static JTextArea chatText;
    static JTextArea destinatario;
    static JTextArea messDaInviare;
    JLabel testo;
    JLabel dest;
    JLabel testodainviare;
    Socket socket;
    DataOutputStream os;
    JButton inviachat;
    BufferedReader stdIn;
    public Chat() throws IOException {
         super("Chat");
        setSize(300, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container areaCentrale = getContentPane();
        chatText = new JTextArea();
        messDaInviare =new JTextArea();
        destinatario=new JTextArea();
        chatText.setLineWrap(true);
        inviachat = new JButton("Invia messaggio");
        testo=new JLabel("chat");
        dest=new JLabel("inserire username destinatario");
        testodainviare=new JLabel("inserire testo da inviare");
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(testo);
        areaCentrale.add(chatText);
        areaCentrale.add(testodainviare);
        areaCentrale.add(messDaInviare);
        areaCentrale.add(dest);
        areaCentrale.add(destinatario);
        areaCentrale.add(inviachat);
        inviachat.addActionListener(this);
        inviachat.setActionCommand(this.inviare);
        socket = new Socket("localhost", 2000);
        stdIn= new BufferedReader(new InputStreamReader(System.in));
        os = new DataOutputStream(socket.getOutputStream());
        scrivi();

    }
    public void  scrivi() throws IOException {
        System.out.println("username"+Username.returnUsername());
        os.writeBytes(Username.returnUsername() + '\n');
        Lettura lettura =new Lettura(socket);
         new Thread(lettura).start();

    }

    public void actionPerformed(ActionEvent e) {

        String com = e.getActionCommand();
        String mesdainviare= messDaInviare.getText();
        System.out.println(mesdainviare);
        try {
            os.writeBytes(mesdainviare + '\n');
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String mesdainviare2= destinatario.getText();
        System.out.println(mesdainviare2);
        try {
            os.writeBytes(mesdainviare2 + '\n');
        } catch (IOException e1) {
            e1.printStackTrace();
        }




    }

    public static void settaT(String messaggio)
    {
        if(!(messaggio.equals("Impossibile mandare messaggio")))
        chatText.append(messaggio);
        else
            JOptionPane.showMessageDialog(null, "Impossibile mandare messaggio");
        //chatText.append(System.getProperty("line.separator"));
    }
}
