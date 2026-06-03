import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class FreehandShape extends Shape {

    private final ArrayList<Point> points;

    public FreehandShape(int startX, int startY, Color color, int thickness) {
        super(startX, startY, startX, startY, color, thickness);
        points = new ArrayList<>();
        points.add(new Point(startX, startY));
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    @Override
    protected void drawShape(Graphics2D g2d) {
        // If it's just a single click, draw a dot
        if (points.size() < 2) {
            g2d.drawLine(startX, startY, startX, startY);
            return;
        }

        // Draw continuous lines between all recorded mouse coordinates
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}