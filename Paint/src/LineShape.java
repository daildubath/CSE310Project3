import java.awt.Color;
import java.awt.Graphics2D;

/**
 * ============================================================================
 * CLASS SUMMARY: LineShape.java
 * ============================================================================
 * This class is a concrete implementation of the abstract Shape class, responsible
 * for representing and rendering a standard two-point linear segment vector.
 * ----------------------------------------------------------------------------
 * It represents the simplest geometric shape in the application, defined strictly
 * by an absolute starting coordinate origin and an absolute terminal destination coordinate.
 * ============================================================================
 */
// ============================================================================
// REQUIREMENT MET: Inheritance (Subclass Implementation)
// ============================================================================
// Inherits standard fields (startX, startY, endX, endY, color, thickness)
// and the core drawing workflow from its abstract parent class, Shape.
public class LineShape extends Shape {

    /**
     * Constructs a LineShape instance by passing configuration parameters directly
     * up to the base parent constructor layer.
     *
     * @param startX    The absolute horizontal starting coordinate point.
     * @param startY    The absolute vertical starting coordinate point.
     * @param endX      The absolute horizontal ending coordinate destination.
     * @param endY      The absolute vertical ending coordinate destination.
     * @param color     The specific Color profile allocated to this vector object.
     * @param thickness The stroke width mapping size allocated to the line.
     */
    public LineShape(int startX, int startY, int endX, int endY, Color color, int thickness) {
        // Leverages inheritance properties to chain tracking initialization to the superclass
        super(startX, startY, endX, endY, color, thickness);
    }

    /**
     * Implements the unique, encapsulated drawing behavior required to project
     * a 2D line segment between two explicit coordinate points.
     *
     * @param g2d The pre-configured Graphics2D execution engine context wrapper.
     */
    @Override
    protected void drawShape(Graphics2D g2d) {
        // Renders a basic vector line stretching directly from the initial anchor coordinates
        // across the interface grid layout to the final terminal dragging release parameters.
        g2d.drawLine(startX, startY, endX, endY);
    }
}