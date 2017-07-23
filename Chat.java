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
    public static JTextArea chatText;
    public static JTextArea chatTextInvio;
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
        chatTextInvio=new JTextArea();
        inviachat = new JButton("Invia messaggio");
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(chatText);
        areaCentrale.add(chatTextInvio);
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
        String mesdainviare= chatText.getText();
        System.out.println(mesdainviare);
        try {
            os.writeBytes(mesdainviare + '\n');
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("scegli a chi inviare il messaggio");
        String destinatario = null;
        try {
            destinatario = stdIn.readLine();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            os.writeBytes(destinatario + '\n');
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    public static void settaT(String messaggio)
    {
        chatText.append(messaggio);
    }
}
