package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Username extends JFrame implements ActionListener {
    public final static String inviare = "InviaUsername";
    static JTextField Username;
    static String usernameStringa;
    JButton inviaUsername;

    public Username() {
        super("Iniziale");
        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container areaCentrale = getContentPane();
        JOptionPane.showMessageDialog(null, "Inserisci Username");
        Username = new JTextField(15);
        inviaUsername = new JButton("Conferma Username");
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(Username);
        areaCentrale.add(inviaUsername);
        inviaUsername.addActionListener(this);
        inviaUsername.setActionCommand(this.inviare);

    }
    public static String returnUsername()
    {
        return usernameStringa;
    }

    public void actionPerformed(ActionEvent e) {
        usernameStringa = Username.getText();
        String com = e.getActionCommand();
        if(com==inviare) {
            Chat chat=null;
            try {
                chat = new Chat();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            chat.show();
            System.out.println(Username.getText());




        }


    }
}