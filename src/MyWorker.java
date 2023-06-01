import java.io.BufferedReader;
import java.io.PrintWriter;

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
        writer.println("Prenota|" + selectedRadioButton + "|" + text);
        String serverResponse = reader.readLine();
        System.out.println(selectedRadioButton + text);
        System.out.println("Risposta del server: " + serverResponse);
        return "Done!";
    }

    @Override
    protected void done() {
        gui.InitRadButtons(reader, writer, gui.bg);
        JOptionPane.showMessageDialog(gui,
                "Prenotazione completata: " + selectedRadioButton + " Posti: " + text);
    }
}
