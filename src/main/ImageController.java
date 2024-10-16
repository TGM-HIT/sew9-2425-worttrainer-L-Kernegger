package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;
import java.util.Random;

public class ImageController {
    private ImageModel model;
    private ImageView view;
    private boolean mode2Active = false;
    private String correctAnswer;

    public ImageController(ImageModel model, ImageView view) {
        this.model = model;
        this.view = view;

        view.setSubmitAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        view.setOptionButtonActions(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                correctAnswer = button.getText();
            }
        });

        view.setModeSubmitAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkMode2Answer();
            }
        });

        loadNextImage();
    }

    private void checkAnswer() {
        String input = view.getInputText();
        if (model.isImageNameMatching(input)) {
            view.showMessage("Correct!");
        } else {
            view.showMessage("Incorrect. The correct answer was: " + model.getCurrentImageName());
        }
        loadNextImage();
    }

    private void checkMode2Answer() {
        if (correctAnswer.equalsIgnoreCase(model.getCurrentImageName())) {
            view.showMessage("Correct!");
        } else {
            view.showMessage("Incorrect. The correct answer was: " + model.getCurrentImageName());
        }
        loadNextImage();
    }

    private void loadNextImage() {
        if (!model.allImagesUsed()) {
            view.setImage(model.getNextImage());

            if (mode2Active) {
                List<String> names = model.getRandomNames(Math.min(4, model.imageCount()));
                correctAnswer = model.getCurrentImageName();
                names.set(new Random().nextInt(Math.min(4, model.imageCount())), correctAnswer);  // Set one correct answer
                for (int i = 0; i < Math.min(4, model.imageCount()); i++) {
                    view.setOptionButtonText(i, names.get(i));
                }
            }

            // Ensure GUI updates properly

        } else {
            view.showMessage("All images used! \n The images will now be reshuffled");
            model.shuffleImages();
            model.resetUses();
            loadNextImage();

        }
        view.revalidate();
        view.repaint();
    }

    public void toggleMode2() {
        mode2Active = !mode2Active;

        if (mode2Active) {
            view.toggleMode(ImageView.BUTTON_SELECTION_MODE);  // Switch to button selection mode
        } else {
            view.toggleMode(ImageView.TEXT_INPUT_MODE);  // Switch to text input mode
        }

        loadNextImage();  // Load the next image when switching modes
    }
}
