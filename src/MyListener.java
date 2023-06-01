import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class MyListener implements ActionListener {

    private GUI gui;
    private MyWorker worker;
    private BufferedReader reader;
    private PrintWriter writer;

    public MyListener(GUI gui, BufferedReader reader, PrintWriter writer) {
        this.gui = gui;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String selectedRadioButton = getSelectedRadioButton(gui.radioPanel);
        String text = gui.textField.getText();
        if (selectedRadioButton == null || text == null) {
            JOptionPane.showMessageDialog(gui,
                    "Nessuna opzione o numero di posti selezionati");
            return;
        }
        String[] radioButtonLabel = selectedRadioButton.split(",");
        System.out.println(radioButtonLabel[0]);
        worker = new MyWorker(reader, writer, radioButtonLabel[0], text, gui);
        worker.execute();

    }

    private String getSelectedRadioButton(JPanel radioJPanel) {
        Component[] components = radioJPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) component;
                if (radioButton.isSelected()) {
                    return radioButton.getText();
                }
            }
        }
        return null;
    }

}
