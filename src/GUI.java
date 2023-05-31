import javax.swing.*;
import java.awt.*;
import java.net.Socket;
/* 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
*/
public class GUI extends JFrame {

    protected JTextArea textArea;
    protected JPanel radioPanel;

    public GUI(Socket socket) {
        setTitle("Gestore Prenotazioni");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        // Create a scroll pane
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(300, 200));

        // Create a panel to hold the radio buttons
        radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        ButtonGroup bg = new ButtonGroup();

    /* ROBA DA RIVEDERE PER GENERARE I RADIO BUTTON, ERRORI CON SOCKET
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ){
            writer.println("Lista");
            String serverResponse="";
            try {
                serverResponse = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Risposta del server: " + serverResponse);
            //per convertire la hasmap stringa in un oggetto
            Gson gson = new Gson();
            TypeToken<ConcurrentHashMap<String, Integer>> typeToken = new TypeToken<ConcurrentHashMap<String, Integer>>() {};
            ConcurrentHashMap<String, Integer> objectResponse = gson.fromJson(serverResponse, typeToken.getType());
            // Create radio buttons and add them to the panel
            System.out.println("RISPOSTA SERVER"+serverResponse.toString());
            for (ConcurrentHashMap.Entry<String, Integer> entry : objectResponse.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                JRadioButton radioButton = new JRadioButton(key + "" + value);
                radioPanel.add(radioButton);
                bg.add(radioButton);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
       
        JRadioButton radioButton0 = new JRadioButton("Metal");
        radioPanel.add(radioButton0);
        bg.add(radioButton0);
        // Create radio buttons and add them to the panel
         for (int i = 1; i <= 20; i++) {
            JRadioButton radioButton = new JRadioButton("Radio Button " + i);
            radioPanel.add(radioButton);
            bg.add(radioButton);
        }

        // Add the radio panel to the scroll pane
        scrollPane.setViewportView(radioPanel);
        // Create a label for the text area
        JLabel label = new JLabel("Posti da Prentare:     ");
        // Create a text area
        textArea = new JTextArea(1, 20);
        textArea.setLineWrap(true);

        // Create a submit button
        JButton submitButton = new JButton("Prenota");
        MyListener buttonListener = new MyListener(this, socket);
        submitButton.addActionListener(buttonListener);

        // Create a panel to hold the text area and submit button
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.WEST);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        // Create a container panel to hold the scroll pane and the panel with the text
        // area and submit button
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(scrollPane, BorderLayout.CENTER);
        containerPanel.add(panel, BorderLayout.SOUTH);

        // Add the container panel to the frame
        getContentPane().add(containerPanel);

        setVisible(true);
    }

}
