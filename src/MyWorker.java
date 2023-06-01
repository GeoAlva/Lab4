import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

public class MyWorker extends SwingWorker<String, Integer> {

    // private Socket socket;
    private String selectedRadioButton;
    private String text;
    private BufferedReader reader;
    private PrintWriter writer;
    private GUI gui;

    public MyWorker(BufferedReader reader, PrintWriter writer, String selectedRadioButton, String text, GUI gui) {
        this.selectedRadioButton = selectedRadioButton;
        this.text = text;
        this.reader = reader;
        this.writer = writer;
        this.gui = gui;
    }

    @Override
    protected String doInBackground() throws Exception {
        if (selectedRadioButton.isEmpty() || text.isEmpty())
            return "null";
        writer.println("Prenota|" + selectedRadioButton + "|" + text);
        String serverResponse = reader.readLine();
        System.out.println(selectedRadioButton + text);
        System.out.println("Risposta del server: " + serverResponse);
        if (serverResponse.startsWith("Impossibile")) {
            return "error";
        }
        return "done";
    }

    @Override
    protected void done() {
        String returnValue;
        try {
            returnValue = get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Errore MyWorker get() " + e.getMessage());
            return;
        }

        switch (returnValue) {
            case "error":
                JOptionPane.showMessageDialog(gui, "Non è possibile prenotare piu posti di quelli disponibili");
                break;

            default:
                gui.InitRadButtons(reader, writer, gui.bg);
                JOptionPane.showMessageDialog(gui,
                        "Prenotazione completata: " + selectedRadioButton + " Posti: " + text);
                break;
        }

    }

}
