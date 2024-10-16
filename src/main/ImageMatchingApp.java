package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;


public class ImageMatchingApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String folderPath = JOptionPane.showInputDialog("Enter the folder path of images:");
            if (folderPath != null) {
                ImageModel model = new ImageModel(folderPath);
                ImageView view = new ImageView();
                ImageController controller = new ImageController(model, view);

                view.setVisible(true);

                // Mode switch using Ctrl+Alt+S
                view.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK), "switchMode");
                view.getRootPane().getActionMap().put("switchMode", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.toggleMode2();
                    }
                });
            }
        });
    }
}
