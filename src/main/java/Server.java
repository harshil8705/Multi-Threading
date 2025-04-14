import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        return ((clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String clientMsg = fromClient.readLine();
                System.out.println("Received from client: " + clientMsg);

                toClient.println("Hello from the Server.");

                fromClient.close();
                toClient.close();
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) {
        int port = 8080;
        Server server = new Server();

        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening on the port : " + port);

            while (true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
