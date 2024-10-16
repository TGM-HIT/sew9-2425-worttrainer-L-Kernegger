package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.swing.ImageIcon;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageModelTest {

    private ImageModel imageModel;
    private File mockFolder;
    private File[] mockFiles;

    @BeforeEach
    public void setUp() {
        // Mocking a folder with image files
        mockFolder = mock(File.class);
        mockFiles = new File[]{
                new File("image1.jpg"),
                new File("image2.png"),
                new File("image3.gif")
        };
        when(mockFolder.listFiles()).thenReturn(mockFiles);

        imageModel = spy(new ImageModel(mockFolder.getPath()));
        doReturn(mockFiles).when(imageModel).loadImages(mockFolder.getPath());  // Mock the loadImages
    }

    @Test
    public void testLoadImages() {
        imageModel = new ImageModel(mockFolder.getPath());

        assertEquals(3, imageModel.imageCount(), "Image count should match the number of valid images.");
    }

    @Test
    public void testGetNextImage() {
        imageModel = new ImageModel(mockFolder.getPath());
        ImageIcon nextImage = imageModel.getNextImage();

        assertNotNull(nextImage, "Next image should not be null.");
        assertTrue(nextImage.getDescription().endsWith(".jpg") || nextImage.getDescription().endsWith(".png") || nextImage.getDescription().endsWith(".gif"),
                "Next image should be an image file with correct extension.");
    }

    @Test
    public void testIsImageNameMatching() {
        imageModel = new ImageModel(mockFolder.getPath());
        imageModel.getNextImage();  // To set the current index

        assertTrue(imageModel.isImageNameMatching("image1"), "Image name should match for 'image1'.");
        assertFalse(imageModel.isImageNameMatching("wrongName"), "Image name should not match a wrong name.");
    }

    @Test
    public void testGetCurrentImageName() {
        imageModel = new ImageModel(mockFolder.getPath());
        imageModel.getNextImage();  // To set the current index

        assertEquals("image1", imageModel.getCurrentImageName(), "Current image name should be 'image1'.");
    }

    @Test
    public void testAllImagesUsed() {
        imageModel = new ImageModel(mockFolder.getPath());

        // Simulate getting all images
        for (int i = 0; i < 3; i++) {
            imageModel.getNextImage();
        }

        assertTrue(imageModel.allImagesUsed(), "All images should be used after iterating through all images.");
    }

    @Test
    public void testResetUses() {
        imageModel = new ImageModel(mockFolder.getPath());

        // Use all images
        for (int i = 0; i < 3; i++) {
            imageModel.getNextImage();
        }

        imageModel.resetUses();
        assertFalse(imageModel.allImagesUsed(), "All images should not be used after reset.");
    }

    @Test
    public void testGetRandomNames() {
        imageModel = new ImageModel(mockFolder.getPath());

        List<String> randomNames = imageModel.getRandomNames(2);
        assertEquals(2, randomNames.size(), "Should return the requested number of random names.");
        assertTrue(randomNames.contains("image1") || randomNames.contains("image2") || randomNames.contains("image3"), "Names should be valid image names.");
    }

    @Test
    public void testShuffleImages() {
        imageModel = new ImageModel(mockFolder.getPath());

        List<String> beforeShuffle = imageModel.getRandomNames(3);
        imageModel.shuffleImages();
        List<String> afterShuffle = imageModel.getRandomNames(3);

        assertNotEquals(beforeShuffle, afterShuffle, "Shuffled images should be in a different order.");
    }
}
