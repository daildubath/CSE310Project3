import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * ============================================================================
 * CLASS SUMMARY: Shape.java
 * ============================================================================
 * This abstract class serves as the core foundational blueprint for the
 * applications' object-oriented vector drawing model.
 * ----------------------------------------------------------------------------
 * It establishes uniform spatial dimensions, custom colors, and line weight states
 * shared by all geometric elements. By implementing Serializable, it permits the
 * full state of any subclass object to be written to disk as raw binary project data.
 * ============================================================================
 */
// ============================================================================
// REQUIREMENT MET: Inheritance (Abstract Superclass Blueprint)
// ============================================================================
// Mandates a shared polymorphic contract that allows different types of shapes
// to be managed inside a single unified collection list.
public abstract class Shape implements Serializable {

    // --- PROTECTED INSTANCE VARIABLES ---
    // Accessible by concrete subclass implementations while remaining hidden from outside objects.
    protected int startX, startY, endX, endY; // Spatial coordinate anchor tracking bounds
    protected Color color;                     // The explicit color state variable
    protected int thickness;                   // Tracking state variable defining the line width scale

    /**
     * Common constructor ensuring every subclass instance instantiates with
     * explicit layout, color, and stroke weight properties.
     *
     * @param startX    The initial horizontal click coordinate.
     * @param startY    The initial vertical click coordinate.
     * @param endX      The target terminal horizontal drag coordinate.
     * @param endY      The target terminal vertical drag coordinate.
     * @param color     The Color instance bound to this specific shape.
     * @param thickness The width pixel diameter allocated to the rendering stroke.
     */
    public Shape(int startX, int startY, int endX, int endY, Color color, int thickness) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.thickness = thickness;
    }

    /**
     * ====================================================================
     * REQUIREMENT MET: Functions / Custom Methods (Template Method Pattern)
     * ====================================================================
     * Actively intercepts the hardware rendering pipeline request to establish
     * common canvas attributes (color context and line stroke properties) before
     * passing structural execution details down to individual child shapes.
     *
     * @param g The base AWT structural graphics engine context pipeline channel.
     */
    public void draw(Graphics g) {
        // Safe type-cast conversion up to the modern Graphics2D subsystem interface wrapper
        Graphics2D g2d = (Graphics2D) g;

        // Updates context rendering configurations to match this shape object's saved parameters
        g2d.setColor(color);

        // Configures line behavior properties. Using rounded caps and joints eliminates
        // jagged separation artifacts during fast freehand drawing mouse motions.
        g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        // Polymorphically hands execution down to the active concrete child shape subclass implementation
        drawShape(g2d);
    }

    /**
     * ====================================================================
     * REQUIREMENT MET: Polymorphism (Abstract Deferred Method Execution)
     * ====================================================================
     * Forces child subclasses to implement their specific geometric vector plotting
     * algorithm while keeping global styling logic unified in this superclass method.
     *
     * @param g2d The pre-configured 2D rendering graphics pipeline interface link.
     */
    protected abstract void drawShape(Graphics2D g2d);
}