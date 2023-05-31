import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    protected JTextArea textArea;
    protected JPanel radioPanel;

    public GUI() {
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
        MyListener buttonListener = new MyListener(this);
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
