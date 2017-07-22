package Client;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    public void start() throws IOException, InterruptedException {
        //Connessione della Socket con il Server
        Socket socket = new Socket("localhost", 2000);
        //Stream di byte da passare al Socket
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Inserire Username");
        String Username = stdIn.readLine();
        os.writeBytes(Username + '\n');
        Lettura lettura =new Lettura(socket);
        new Thread(lettura).start();
        while(true) {
            System.out.println("Inserisci testo");
            String testo = stdIn.readLine();
            os.writeBytes(testo + '\n');
             System.out.println("scegli a chi inviare il messaggio");
            String destinatario = stdIn.readLine();
             os.writeBytes(destinatario + '\n');

        }


          //Ciclo infinito per inserimento testo del Client
     /*   while (true) {
                System.out.print(is.readLine());
                String userInput = stdIn.readLine();
                if (userInput.equals("QUIT"))
                    break;
                os.writeBytes(userInput + '\n');

            }*/

        //Chiusura dello Stream e del Socket

}}