import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.SwingWorker;

public class MyWorker extends SwingWorker<String, Integer> {

    private Socket socket;
    private String selectedRadioButton;
    private String text;

    public MyWorker(Socket socket, String selectedRadioButton, String text) {
        this.socket = socket;
        this.selectedRadioButton = selectedRadioButton;
        this.text = text;
    }

    @Override
    protected String doInBackground() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        System.out.println(selectedRadioButton + text);
        writer.println("Prenota|" + selectedRadioButton + "|" + text);
        String serverResponse = reader.readLine();
        System.out.println("Risposta del server: " + serverResponse);
        return "Done!";
    }

    @Override
    protected void done() {

    }
}
