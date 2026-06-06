import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS SUMMARY: FileHandler.java
 * ============================================================================
 * This utility class manages data persistence and image generation for the
 * paint application. It operates entirely using static routines, functioning as
 * the data infrastructure manager.
 * ----------------------------------------------------------------------------
 * It fulfills two distinct roles:
 * 1. Project Session Storing/Loading: Serializes and deserializes vector object
 * data graphs to maintain full editing capabilities.
 * 2. Visual Layer Flattening: Intercepts the component rendering pipeline to
 * rasterize and bake geometry paths into standard, portable PNG image formats.
 * ============================================================================
 */
public class FileHandler {

    /**
     * ====================================================================
     * REQUIREMENT MET: File I/O (Write State) / Collections (Input Parameter)
     * ====================================================================
     * Converts an active ArrayList of dynamic polymorphic structural shapes
     * into a flattened binary sequence, saving it permanently to disk.
     *
     * @param shapes   The active vector history tracking collection from the canvas.
     * @param filename The absolute target system file destination path.
     */
    public static void saveArtwork(ArrayList<Shape> shapes, String filename) {
        // Initializes automatic resource management (try-with-resources) to close streams automatically
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            // Serializes the entire object graph recursively across the collection array
            oos.writeObject(shapes);
            System.out.println("Successfully saved to " + filename);
        } catch (IOException e) {
            // Gracefully handles structural pipeline access anomalies or restricted system writes
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    /**
     * ====================================================================
     * REQUIREMENT MET: File I/O (Read State) / Collections (Return Type)
     * ====================================================================
     * Opens an existing project file, recreates the serialized binary stream
     * back into live heap-allocated objects, and passes the reconstructed array back.
     *
     * @param filename The target system data file location path.
     * @return Rebuilt instance tracking past drawn vector objects.
     */
    @SuppressWarnings("unchecked") // Suppresses explicit type-casting alerts from raw deserialization mapping
    public static ArrayList<Shape> loadArtwork(String filename) {
        // try-with-resources safely encapsulates stream context disposal chains
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            System.out.println("Successfully loaded from " + filename);
            // Extracts and type-casts binary representation data graph to its runtime shape list format
            return (ArrayList<Shape>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Catch handles missing file exceptions or structural mutations in target shapes safely
            System.err.println("Error loading file: " + e.getMessage());
            // Returns an empty fallback tracking list to preserve app execution flow stability
            return new ArrayList<>();
        }
    }

    /**
     * ====================================================================
     * REQUIREMENT MET: File I/O (Standard Export Format)
     * ====================================================================
     * Captures a runtime graphic element panel layer snapshot and flattens it
     * out directly into an independent PNG file container.
     *
     * @param canvas   The visible application workspace panel to capture.
     * @param filename The targeted user chosen filename path destination.
     */
    public static void exportImage(JPanel canvas, String filename) {
        try {
            // ================================================================
            // REQUIREMENT MET: Conditionals (File Suffix Verification)
            // ================================================================
            // Enforces output string sanity rules by confirming standard file extension formats
            if (!filename.toLowerCase().endsWith(".png")) {
                filename += ".png";
            }

            // Constructs an off-screen blank layout canvas bitmap layer tracking RGB color pixels
            BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);

            // Extracts the internal rendering drawing tool link context bound to this off-screen image
            Graphics2D g2d = image.createGraphics();

            // Reroutes the drawing engine pipeline: instructs the view canvas to paint directly
            // onto our memory-allocated bitmap surface instead of rendering to the physical monitor screen.
            canvas.paint(g2d);

            // Explicitly releases hardware graphics system allocations immediately
            g2d.dispose();

            // Writes the final uncompressed image matrix directly to persistent file storage
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Successfully exported to " + filename);
        } catch (Exception e) {
            // Insulates program thread context layers from experiencing dynamic export runtime failures
            System.err.println("Error exporting image: " + e.getMessage());
        }
    }
}