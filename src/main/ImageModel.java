package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;

public class ImageModel {
    private List<File> imageFiles;
    private int currentIndex;
    private boolean[] usedImages;

    public ImageModel(String folderPath) {
        loadImages(folderPath);
        currentIndex = 0;
        usedImages = new boolean[imageFiles.size()];
    }

    private void loadImages(String folderPath) {
        File folder = new File(folderPath);
        imageFiles = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isFile() && isImageFile(file)) {
                imageFiles.add(file);
            }
        }

        // Debug: Check if images are loaded
        System.out.println("Loaded " + imageFiles.size() + " images.");
        for (File image : imageFiles) {
            System.out.println("Image: " + image.getName());
        }
    }

    private boolean isImageFile(File file) {
        String[] extensions = { "jpg", "jpeg", "png", "gif" };
        String name = file.getName().toLowerCase();
        for (String ext : extensions) {
            if (name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public ImageIcon getNextImage() {
        while (usedImages[currentIndex]) {
            currentIndex = (currentIndex + 1) % imageFiles.size();
        }
        usedImages[currentIndex] = true;
        return new ImageIcon(imageFiles.get(currentIndex).getPath());
    }

    public boolean isImageNameMatching(String text) {
        return text.equalsIgnoreCase(imageFiles.get(currentIndex).getName().split("\\.")[0]);
    }

    public String getCurrentImageName() {
        return imageFiles.get(currentIndex).getName().split("\\.")[0];
    }

    public boolean allImagesUsed() {
        for (boolean used : usedImages) {
            if (!used) return false;
        }
        return true;
    }

    public List<String> getRandomNames(int count) {
        List<String> names = new ArrayList<>();
        for (File file : imageFiles) {
            names.add(file.getName().split("\\.")[0]);
        }
        Collections.shuffle(names);
        return names.subList(0, count);
    }

    public void shuffleImages() {
        Collections.shuffle(imageFiles);
    }

    public void resetUses(){
        for(int i  = 0; i < imageFiles.size(); i++){
            usedImages[i] = false;
        }
    }

    public int imageCount(){
        return imageFiles.size();
    }
}
