import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * ============================================================================
 * CLASS SUMMARY: FreehandShape.java
 * ============================================================================
 * This class is a concrete implementation of the abstract Shape class, enabling
 * continuous, freeform drawing.
 * ----------------------------------------------------------------------------
 * Unlike geometric shapes defined by a simple bounding box (start and end points),
 * freehand drawing requires capturing and stitching together a sequential series
 * of tracking coordinates recorded as the mouse moves across the canvas surface.
 * ============================================================================
 */
// ============================================================================
// REQUIREMENT MET: Inheritance (Subclass Context)
// ============================================================================
// Inherits standard color, thickness, and base structural behaviors from Shape.
public class FreehandShape extends Shape {

    /**
     * ====================================================================
     * REQUIREMENT MET: Data Structures / Collections (State Storage)
     * ====================================================================
     * A sequential historical coordinate stream array list. Marked final
     * because the reference container never changes after instantiation,
     * even as elements are continuously appended to it.
     */
    private final ArrayList<Point> points;

    /**
     * Constructs an initial instance state anchor point for the path tracking.
     *
     * @param startX    The initial horizontal coordinate grid origin.
     * @param startY    The initial vertical coordinate grid origin.
     * @param color     The targeted stroke color profile.
     * @param thickness The dimension width scale allocated to the stroke.
     */
    public FreehandShape(int startX, int startY, Color color, int thickness) {
        // Leverages parent constructor rules to initialize root properties
        super(startX, startY, startX, startY, color, thickness);

        // Allocates memory for our position collection array layout
        points = new ArrayList<>();

        // Locks down the absolute starting coordinate index spot
        points.add(new Point(startX, startY));
    }

    /**
     * ====================================================================
     * REQUIREMENT MET: Functions / Custom Methods
     * ====================================================================
     * Appends live mouse capture coordinate points to the array tracker
     * as drag mouse listener events trigger updates.
     *
     * @param x The current horizontal coordinate point.
     * @param y The current vertical coordinate point.
     */
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    /**
     * Handles the specific vector processing algorithms required to paint
     * a variable continuous point array graph line.
     *
     * @param g2d The configured 2D hardware graphics drawing context wrapper.
     */
    @Override
    protected void drawShape(Graphics2D g2d) {
        // ================================================================
        // REQUIREMENT MET: Conditionals (Single Click Fallback)
        // ================================================================
        // Prevents processing loops if no movement occurs, safely falling back
        // to single dot micro line plotting.
        if (points.size() < 2) {
            g2d.drawLine(startX, startY, startX, startY);
            return; // Interrupts operational execution chain immediately
        }

        // ================================================================
        // REQUIREMENT MET: Loops (Array Path Reconstruction)
        // ================================================================
        // Loops through index point array boundaries to systematically stitch
        // sequential pairs together into a seamless visual string line.
        for (int i = 0; i < points.size() - 1; i++) {
            // Isolates the active starting node segment point reference
            Point p1 = points.get(i);

            // Isolates the subsequent adjacent destination point reference
            Point p2 = points.get(i + 1);

            // Draws an atomic line link bridge spanning from p1 directly to p2
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}