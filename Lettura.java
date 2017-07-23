package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


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
            }
        }
    }

    public void inarrivomes(String messaggio) throws IOException {
        System.out.println("test"+messaggio);
        if(!(messaggio.equals(null)))
        {
            Chat.settaT(messaggio);
        }




    }

}