import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import com.google.gson.Gson;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Eventi eventi;

    public ClientHandler(Socket clientSocket, Eventi eventi) {
        this.clientSocket = clientSocket;
        this.eventi = eventi;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String request;
            while ((request = reader.readLine()) != null) {
                String response = processRequest(request);
                writer.println(response);
            }
        } catch (IOException e) {
            System.out.println("Connessione chiusa");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String processRequest(String request) {
        Gson gson = new Gson();
        String jsonString;

        // Evento Prenota
        if (request.startsWith("Prenota")) {
            String[] params = request.split("\\|");
            String nome = params[1];
            int posti = Integer.parseInt(params[2]);

            Boolean outcome = eventi.Prenota(nome, posti);
            if (outcome)
                return "Evento " + nome + ": prenotati " + posti + " posti.";
            else
                return "Impossibile Prenotare l'evento";
        }
        // Evento Lista
        if (request.equals("Lista")) {
            jsonString = gson.toJson(eventi.ListaEventi());
            System.out.println("Stringa: " + jsonString);
            return jsonString;
        }

        return "Comando non riconosciuto";
    }
}