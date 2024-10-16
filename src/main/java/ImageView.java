import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ImageView extends JFrame {
    private JLabel imageLabel;
    private JTextField inputField;
    private JButton submitButton;
    private JButton[] optionButtons;
    private JButton modeSubmitButton;
    private JPanel inputPanel;
    private JPanel optionPanel;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public static final String TEXT_INPUT_MODE = "TextInputMode";
    public static final String BUTTON_SELECTION_MODE = "ButtonSelectionMode";
    public static final String DIRECTORY_SELECTION_MODE = "DirectorySelectionMode";

    private JButton directorySubmitButton;
    private JTextField directoryInputField;

    public ImageView() {
        setTitle("Image Matching Game");

        // Set minimum size for the window
        setMinimumSize(new Dimension(300, 200));

        // Use BorderLayout for automatic resizing
        setLayout(new BorderLayout());

        // Image display
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);  // Center the image
        add(imageLabel, BorderLayout.CENTER);

        // Use CardLayout to switch between the modes
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // First card (Mode 1: Text input and submit button)
        inputPanel = new JPanel(new GridBagLayout());  // Use GridBagLayout for better scaling
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputField = new JTextField(20);
        submitButton = new JButton("Submit");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;  // Allow horizontal expansion
        inputPanel.add(inputField, gbc);

        gbc.gridy = 1;
        gbc.weightx = 0;  // Reset weight for the button
        inputPanel.add(submitButton, gbc);

        cardPanel.add(inputPanel, TEXT_INPUT_MODE);  // Add this as the first card

        // Second card (Mode 2: Four option buttons and submit answer)
        optionPanel = new JPanel(new GridBagLayout());
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            gbc = new GridBagConstraints();
            gbc.gridx = i % 2; // Arrange in 2 columns
            gbc.gridy = i / 2; // Row changes after 2 buttons
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH; // Ensure buttons scale both ways
            optionPanel.add(optionButtons[i], gbc);
        }

        modeSubmitButton = new JButton("Submit Answer");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span the entire width of the panel
        gbc.fill = GridBagConstraints.HORIZONTAL;
        optionPanel.add(modeSubmitButton, gbc);
        cardPanel.add(optionPanel, BUTTON_SELECTION_MODE);  // Add this as the second card

        // Third card (Directory selection mode)
        JPanel directoryPanel = new JPanel(new GridBagLayout());
        directoryInputField = new JTextField(20);
        directorySubmitButton = new JButton("Load Directory");

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;  // Allow text field to grow
        directoryPanel.add(directoryInputField, gbc);

        gbc.gridy = 1;
        gbc.weightx = 0;
        directoryPanel.add(directorySubmitButton, gbc);

        cardPanel.add(directoryPanel, DIRECTORY_SELECTION_MODE);  // Add this as the directory selection card

        // Add the card panel to the frame
        add(cardPanel, BorderLayout.SOUTH);

        // Allow resizing to fill space
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeComponents();
            }
        });

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void setImage(ImageIcon image) {
        // Scale image based on window size
        int width = Math.max(300, getWidth() - 100);  // Ensure minimum width
        int height = Math.max(200, getHeight() - 200); // Ensure minimum height
        Image scaledImage = image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    public String getInputText() {
        return inputField.getText();
    }

    public void setSubmitAction(ActionListener listener) {
        submitButton.addActionListener(listener);
    }

    public void setOptionButtonActions(ActionListener listener) {
        for (JButton button : optionButtons) {
            button.addActionListener(listener);
        }
    }

    public void setModeSubmitAction(ActionListener listener) {
        modeSubmitButton.addActionListener(listener);
    }

    public void setDirectorySubmitAction(ActionListener listener) {
        directorySubmitButton.addActionListener(listener);
    }

    public String getDirectoryInput() {
        return directoryInputField.getText();
    }

    public void setOptionButtonText(int index, String text) {
        optionButtons[index].setText(text);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Switch between modes using CardLayout
    public void toggleMode(String mode) {
        cardLayout.show(cardPanel, mode);
        revalidate();
        repaint();
    }

    // Ensure components scale with window resizing
    private void resizeComponents() {
        setImage((ImageIcon) imageLabel.getIcon());  // Rescale the image
    }
}
