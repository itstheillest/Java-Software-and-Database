package handlers;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SlideshowHandler {
    private Timer slideshowTimer;
    private List<String> imagePaths;
    private int currentImageIndex = 0;
    private BufferedImage currentBackgroundImage;
    private JLabel backgroundLabel;

    public SlideshowHandler(JLabel backgroundLabel) {
        this.backgroundLabel = backgroundLabel;
    }

    public void initializeSlideshow() {
        if (slideshowTimer != null) {
            slideshowTimer.stop();
            slideshowTimer = null;
        }

        imagePaths = new ArrayList<>();
        imagePaths.add("handlers/Images/pic1.jpg");
        imagePaths.add("handlers/Images/pic2.jpg");
        imagePaths.add("handlers/Images/pic3.jpg");

        currentImageIndex = 0;

        if (!imagePaths.isEmpty()) {
            showCurrentImage();
            startSlideshow();
        } else {
            showNoImagesMessage();
        }
    }

    private void showNoImagesMessage() {
        // Create a default background when no images are available
        currentBackgroundImage = new BufferedImage(1200, 800, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = currentBackgroundImage.createGraphics();
        g.setColor(new Color(255, 255, 240));
        g.fillRect(0, 0, 1200, 800);
        g.setColor(new Color(200, 200, 200));
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("No Background Images Found", 400, 400);
        g.dispose();

        backgroundLabel.repaint();
    }

    private void showCurrentImage() {
        if (imagePaths.isEmpty()) return;

        String imagePath = imagePaths.get(currentImageIndex);

        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            if (originalIcon.getIconWidth() > 0) {
                // Convert to BufferedImage for background
                Image img = originalIcon.getImage();
                currentBackgroundImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = currentBackgroundImage.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(img, 0, 0, null);
                g.dispose();

                backgroundLabel.repaint();
            } else {
                showNoImagesMessage();
            }
        } catch (Exception e) {
            showNoImagesMessage();
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    private void showNextImage() {
        if (imagePaths.isEmpty()) return;
        currentImageIndex = (currentImageIndex + 1) % imagePaths.size();
        showCurrentImage();
    }

    private void startSlideshow() {
        if (slideshowTimer != null) {
            slideshowTimer.stop();
        }

        slideshowTimer = new Timer(3000, e -> showNextImage());
        slideshowTimer.start();
    }

    public void pauseSlideshow() {
        if (slideshowTimer != null) {
            slideshowTimer.stop();
            slideshowTimer = null;
        }
    }

    // Getter method for the current background image (used by the backgroundLabel's paintComponent)
    public BufferedImage getCurrentBackgroundImage() {
        return currentBackgroundImage;
    }
}