import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

public class MyListener implements ActionListener {

    private GUI gui;
    private MyWorker worker;
    private Socket socket;

    public MyListener(GUI gui, Socket socket) {
        this.gui = gui;
        this.socket = socket;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedRadioButton = getSelectedRadioButton(gui.radioPanel);
        String text = gui.textArea.getText();
        worker = new MyWorker(socket, selectedRadioButton, text);
        worker.execute();
        JOptionPane.showMessageDialog(gui,
                "Selected Radio Button: " + selectedRadioButton + "\nText: " + text);
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
