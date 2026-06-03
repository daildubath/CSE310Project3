import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    protected int startX, startY, endX, endY;
    protected Color color;
    protected int thickness; // NEW: Tracks the width of the stroke

    public Shape(int startX, int startY, int endX, int endY, Color color, int thickness) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.thickness = thickness;
    }

    // A unified draw method that sets the context before delegating to the child
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        // Set the stroke with rounded caps and joins for smoother freehand drawing
        g2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        drawShape(g2d);
    }

    // Abstract method for children to implement their specific geometry
    protected abstract void drawShape(Graphics2D g2d);
}