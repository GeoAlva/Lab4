import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingUtilities;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {

        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Connessione al server riuscita. Puoi inviare comandi al server.");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new GUI(reader, writer);
                }
            });
            // Per inserimento manuale delle richieste:
            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                writer.println(userInput);

                String serverResponse = reader.readLine();
                System.out.println("Risposta del server: " + serverResponse);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}