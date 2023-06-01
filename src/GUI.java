import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GUI extends JFrame {

    protected JTextArea textArea;
    protected JPanel radioPanel;
    protected ButtonGroup bg;

    public GUI(BufferedReader reader, PrintWriter writer) {
        setTitle("Gestore Prenotazioni");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        // Create a scroll pane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Create a panel to hold the radio buttons
        radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        bg = new ButtonGroup();

        InitRadButtons(reader, writer, bg);

        // Add the radio panel to the scroll pane
        scrollPane.setViewportView(radioPanel);
        // Create a label for the text area
        JLabel label = new JLabel("Posti da Prentare:     ");
        // Create a text area
        textArea = new JTextArea(1, 20);
        textArea.setLineWrap(true);

        // Create a submit button
        JButton submitButton = new JButton("Prenota");
        MyListener buttonListener = new MyListener(this, reader, writer);
        submitButton.addActionListener(buttonListener);

        // Create refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                InitRadButtons(reader, writer, bg);
            }
        });

        // Create a panel to hold the text area and submit button
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(refreshButton);

        // Create a container panel to hold the scroll pane and the panel with the text
        // area and submit button
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        containerPanel.add(panel, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the container panel to the frame
        getContentPane().add(containerPanel);

        setVisible(true);
    }

    protected void InitRadButtons(BufferedReader reader, PrintWriter writer, ButtonGroup bg) {

        radioPanel.removeAll();
        bg.clearSelection();
        // rimuovi tutti i bottoni
        Enumeration<AbstractButton> buttons = bg.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            bg.remove(button);
        }

        writer.println("Lista");
        String serverResponse = "";
        try {
            serverResponse = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Risposta del server: " + serverResponse);
        // per convertire la hasmap stringa in un oggetto
        Gson gson = new Gson();
        TypeToken<ConcurrentHashMap<String, Integer>> typeToken = new TypeToken<ConcurrentHashMap<String, Integer>>() {
        };
        ConcurrentHashMap<String, Integer> objectResponse = gson.fromJson(serverResponse, typeToken.getType());
        // Create radio buttons and add them to the panel
        for (ConcurrentHashMap.Entry<String, Integer> entry : objectResponse.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            JRadioButton radioButton = new JRadioButton(key + ", Posti disponibili: " + value);
            radioPanel.add(radioButton);
            bg.add(radioButton);

        }
        radioPanel.revalidate();
        radioPanel.repaint();

    }

}
