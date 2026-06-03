import java.awt.Color;
import java.awt.Graphics2D;

public class LineShape extends Shape {

    public LineShape(int startX, int startY, int endX, int endY, Color color, int thickness) {
        super(startX, startY, endX, endY, color, thickness);
    }

    @Override
    protected void drawShape(Graphics2D g2d) {
        g2d.drawLine(startX, startY, endX, endY);
    }
}