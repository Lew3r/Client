package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JFrame implements ActionListener {
    static JButton registrazione;
    static JButton accesso;

    public Menu() {
        super("Menu");
        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container areaCentrale = getContentPane();
        registrazione = new JButton("Nuovo Utente");
        accesso = new JButton("Accedi");
        areaCentrale.setLayout(new BoxLayout(areaCentrale, BoxLayout.Y_AXIS));
        areaCentrale.add(registrazione);
        areaCentrale.add(accesso);
        registrazione.addActionListener(this);
        registrazione.setActionCommand(null);
        accesso.addActionListener(this);
        accesso.setActionCommand(null);


    }


    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com == "Nuovo Utente") {
            Registrazione registr = new Registrazione();
            setVisible(false);
            registr.setVisible(true);
        }
        if (com == "Accedi") {
            Username username = null;
            username = new Username();
            username.setVisible(true);
        }
    }
    }
