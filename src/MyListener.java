import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyListener implements ActionListener {

    private GUI gui;
    private MyWorker worker;

    public MyListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        worker = new MyWorker();
        worker.execute();
        String selectedRadioButton = getSelectedRadioButton(gui.radioPanel);
        String text = gui.textArea.getText();
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
