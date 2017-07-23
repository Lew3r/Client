package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Utenti extends JFrame implements ActionListener {
    public final static String inviare1 = "socket1";
    public final static String inviare2 = "socket2";
    public final static String inviare3 = "socket3";
    public final static String inviare4 = "socket4";
    public final static JButton[] user= new JButton[5];
    static int indice=0;
    public static ArrayList<JTextArea> testoUser=new ArrayList<JTextArea>();
    JButton bottone;

    public Utenti() throws IOException {
        super("Chat");
        setSize(300, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container areaCentrale = getContentPane();
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        for(int i=0;i<5;i++) {
            user[i]=(bottone = new JButton("null"));
            areaCentrale.add(user[i]).setVisible(false);
            user[i].addActionListener(this);
        }
        user[0].setActionCommand(this.inviare1);
        user[1].setActionCommand(this.inviare2);
        user[2].setActionCommand(this.inviare3);
        user[3].setActionCommand(this.inviare4);
    }

    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if(com==inviare1)
           Chat.settaggioChat(testoUser.get(0).getText());
        if(com==inviare2)
            Chat.settaggioChat(testoUser.get(1).getText());
        if(com==inviare3)
           Chat.settaggioChat(testoUser.get(2).getText());
        if(com==inviare4)
            Chat.settaggioChat(testoUser.get(3).getText());
        Chat.enableInviaChat();

    }
    public  static void incrementabottoni(String utente, String mess,String tuttiutenti) {
        char b='$';
        System.out.println("ciao"+tuttiutenti);

        for(int i=0;!((tuttiutenti.equals('$')||tuttiutenti.equals("")));i++)
        {
            System.out.println("aaaaa "+tuttiutenti);

                String ut=tuttiutenti.substring(0,tuttiutenti.indexOf(b));
                System.out.println(("test"+ ut));
                user[i].setText(ut);
                user[i].setVisible(true);
                JTextArea area = new JTextArea();
                testoUser.add(area);
                tuttiutenti = tuttiutenti.substring(tuttiutenti.indexOf(b));
                tuttiutenti=tuttiutenti.substring(1);
            }
        for (int i = 0; i < 5; i++)
        {
            //System.out.println("test1 "+ user[i].getText()+ "  test1 " + utente);
            if (user[i].getText().equals(utente))
            {
               // System.out.println("test "+ testoUser.get(i).getText()+ "  test " + messaggio);
                testoUser.get(i).append(mess);
            }
        }

    }
}