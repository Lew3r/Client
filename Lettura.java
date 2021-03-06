package Client;
import javax.swing.text.BadLocationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLException;


public class Lettura implements Runnable {
    Socket socket;
    BufferedReader buffer;

    public Lettura(Socket socket) throws IOException {
        this.socket = socket;
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        while (true) {
            try {
                if (buffer.ready()) {
                    inarrivomes(buffer.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BadLocationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void inarrivomes(String messaggio) throws IOException, BadLocationException, IllegalAccessException, SQLException, InstantiationException {
        if(!(messaggio.equals(null)))
        {
            Chat.settaT(messaggio);
        }
    }

}