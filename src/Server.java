import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8888;

    public static void main(String[] args) {
        Eventi eventi = new Eventi();
        eventi.Crea("Metal", 200);
        eventi.Crea("Classico", 80);
        eventi.Crea("Rap", 30);
        eventi.Crea("Pop", 27);
        eventi.Crea("Jazz", 75);
        eventi.Crea("Country", 308);
        eventi.Crea("Rock", 53);
        eventi.Crea("Disco", 42);
        eventi.Crea("Techno", 912);
        eventi.Crea("Funk", 473);
        eventi.Crea("Hip_hop", 297);

        try {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server avviato. In attesa di connessioni...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Nuova connessione accettata.");

                    // Creazione di un thread per gestire la connessione del client
                    ClientHandler clientHandler = new ClientHandler(clientSocket, eventi);
                    Thread clientThread = new Thread(clientHandler);
                    clientThread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}