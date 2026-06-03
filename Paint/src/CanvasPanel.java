import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CanvasPanel extends JPanel {

    private ArrayList<Shape> shapes;
    // WARNING FIX: 'redoStack' may be 'final'
    private final ArrayList<Shape> redoStack;

    private Shape currentShape;
    private Color currentColor;
    private String currentTool;
    private int currentThickness = 2;

    public CanvasPanel() {
        setBackground(Color.WHITE);
        shapes = new ArrayList<>();
        redoStack = new ArrayList<>();
        currentColor = Color.BLACK;
        currentTool = "Freehand";

        setupMouseListeners();
    }

    public void undo() {
        if (!shapes.isEmpty()) {
            // WARNING FIX: Replaced with removeLast() call
            Shape lastShape = shapes.removeLast();
            redoStack.add(lastShape);
            repaint();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            // WARNING FIX: Replaced with removeLast() call
            Shape nextShape = redoStack.removeLast();
            shapes.add(nextShape);
            repaint();
        }
    }

    public void setCurrentTool(String tool) { this.currentTool = tool; }
    public void setCurrentColor(Color color) { this.currentColor = color; }
    public void setCurrentThickness(int thickness) { this.currentThickness = thickness; }
    public ArrayList<Shape> getShapes() { return shapes; }

    public void setShapes(ArrayList<Shape> loadedShapes) {
        this.shapes = loadedShapes;
        this.redoStack.clear();
        repaint();
    }

    private void setupMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                // WARNING & ERROR FIX: Replaced 'if' with 'switch' and fixed constructor arguments
                switch (currentTool) {
                    case "Line" -> currentShape = new LineShape(x, y, x, y, currentColor, currentThickness);
                    case "Rectangle" -> currentShape = new RectangleShape(x, y, x, y, currentColor, currentThickness);
                    case "Oval" -> currentShape = new OvalShape(x, y, x, y, currentColor, currentThickness);
                    case "Freehand" -> currentShape = new FreehandShape(x, y, currentColor, currentThickness);
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentShape != null) {
                    if (currentShape instanceof FreehandShape) {
                        ((FreehandShape) currentShape).addPoint(e.getX(), e.getY());
                    } else {
                        currentShape.endX = e.getX();
                        currentShape.endY = e.getY();
                    }
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    if (!(currentShape instanceof FreehandShape)) {
                        currentShape.endX = e.getX();
                        currentShape.endY = e.getY();
                    }
                    shapes.add(currentShape);
                    currentShape = null;
                    redoStack.clear();
                    repaint();
                }
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        if (currentShape != null) {
            currentShape.draw(g);
        }
    }
}