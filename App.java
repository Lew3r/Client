package Client;
import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException {

        Client tcpClient = new Client();
        tcpClient.start();
    }
}
