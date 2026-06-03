import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics2D; // ERROR FIX: Missing import
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static void saveArtwork(ArrayList<Shape> shapes, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(shapes);
            System.out.println("Successfully saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Shape> loadArtwork(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            System.out.println("Successfully loaded from " + filename);
            return (ArrayList<Shape>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void exportImage(JPanel canvas, String filename) {
        try {
            if (!filename.toLowerCase().endsWith(".png")) {
                filename += ".png";
            }
            BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            canvas.paint(g2d); // Works perfectly now that Graphics2D is imported
            g2d.dispose();

            ImageIO.write(image, "png", new File(filename));
            System.out.println("Successfully exported to " + filename);
        } catch (Exception e) {
            System.err.println("Error exporting image: " + e.getMessage());
        }
    }
}