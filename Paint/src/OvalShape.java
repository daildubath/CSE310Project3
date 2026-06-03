import java.awt.Color;
import java.awt.Graphics2D;

public class OvalShape extends Shape {

    public OvalShape(int startX, int startY, int endX, int endY, Color color, int thickness) {
        super(startX, startY, endX, endY, color, thickness);
    }

    @Override
    protected void drawShape(Graphics2D g2d) {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(startX - endX);
        int height = Math.abs(startY - endY);
        g2d.drawOval(x, y, width, height);
    }
}