import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int port = 8080;
                try{
                    InetAddress inetAddress = InetAddress.getByName("localhost");
                    Socket socket = new Socket(inetAddress, port);
                    try{
                        PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        toServer.println("Hello From the Client : " + socket.getLocalSocketAddress());
                        String line = fromServer.readLine();
                        System.out.println("Response from server : " + line);
                    } catch (Exception e){
                        System.out.println("Error : " + e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("Error : " + e.getMessage());
                }
            }
        };
    }

    public static void main(String[] args) {
        try{
            Client client = new Client();
            for(int i = 0; i < 10000; i++){
                Thread thread = new Thread(client.getRunnable());
                thread.start();
            }
        } catch (Exception e){
            System.out.println("Error : " + e.getMessage());
        }
    }
}
