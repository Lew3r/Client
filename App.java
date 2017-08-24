package Client;
import javax.swing.*;
import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Menu menu=new Menu();
        menu.setVisible(true);

    }
}
