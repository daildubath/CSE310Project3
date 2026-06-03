import javax.swing.*;
import java.awt.*;

class PaintApp {

    void main() {
        SwingUtilities.invokeLater(() -> new PaintApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Simple Paint - Release Candidate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        CanvasPanel canvas = new CanvasPanel();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JToolBar mainTools = new JToolBar();
        mainTools.setFloatable(false);
        JToolBar propertyTools = new JToolBar();
        propertyTools.setFloatable(false);

        JButton btnFreehand = new JButton("Freehand");
        JButton btnLine = new JButton("Line");
        JButton btnRect = new JButton("Rectangle");
        JButton btnOval = new JButton("Oval");

        JButton btnUndo = new JButton("Undo");
        JButton btnRedo = new JButton("Redo");

        JButton btnSave = new JButton("Save Project");
        JButton btnLoad = new JButton("Open Project");
        JButton btnExport = new JButton("Export to PNG");

        JFileChooser fileChooser = new JFileChooser();

        btnFreehand.addActionListener(_ -> canvas.setCurrentTool("Freehand"));
        btnLine.addActionListener(_ -> canvas.setCurrentTool("Line"));
        btnRect.addActionListener(_ -> canvas.setCurrentTool("Rectangle"));
        btnOval.addActionListener(_ -> canvas.setCurrentTool("Oval"));

        btnUndo.addActionListener(_ -> canvas.undo());
        btnRedo.addActionListener(_ -> canvas.redo());

        btnSave.addActionListener(_ -> {
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                FileHandler.saveArtwork(canvas.getShapes(), fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        btnLoad.addActionListener(_ -> {
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                canvas.setShapes(FileHandler.loadArtwork(fileChooser.getSelectedFile().getAbsolutePath()));
            }
        });

        btnExport.addActionListener(_ -> {
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                FileHandler.exportImage(canvas, fileChooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(frame, "Image exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        mainTools.add(btnFreehand); mainTools.add(btnLine); mainTools.add(btnRect); mainTools.add(btnOval);
        mainTools.addSeparator();
        mainTools.add(btnUndo); mainTools.add(btnRedo);
        mainTools.addSeparator();
        mainTools.add(btnSave); mainTools.add(btnLoad); mainTools.add(btnExport);

        propertyTools.add(new JLabel(" Colors: "));

        Color[] paletteColors = {
                Color.BLACK, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.WHITE,
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
                Color.BLUE, Color.MAGENTA, Color.PINK, Color.CYAN
        };

        for (Color c : paletteColors) {
            JButton colorBtn = new JButton();
            colorBtn.setBackground(c);
            colorBtn.setPreferredSize(new Dimension(25, 25));
            colorBtn.setMaximumSize(new Dimension(25, 25));
            colorBtn.setOpaque(true);
            colorBtn.setBorderPainted(false);
            colorBtn.addActionListener(_ -> canvas.setCurrentColor(c));
            propertyTools.add(colorBtn);
        }

        // WARNING FIX: Extracted Hex Button logic to a separate method
        JButton btnHex = createHexButton(frame, canvas);

        propertyTools.add(Box.createHorizontalStrut(5));
        propertyTools.add(btnHex);
        propertyTools.addSeparator();

        propertyTools.add(new JLabel(" Thickness: "));
        JSlider thicknessSlider = new JSlider(1, 20, 2);
        thicknessSlider.setMaximumSize(new Dimension(150, 30));
        thicknessSlider.addChangeListener(_ -> canvas.setCurrentThickness(thicknessSlider.getValue()));
        propertyTools.add(thicknessSlider);

        topPanel.add(mainTools);
        topPanel.add(propertyTools);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // ==========================================
    // EXTRACTED METHOD
    // ==========================================
    private JButton createHexButton(JFrame frame, CanvasPanel canvas) {
        JButton btnHex = new JButton("Hex...");
        btnHex.addActionListener(_ -> {
            String hexInput = JOptionPane.showInputDialog(frame, "Enter Hex Color:");
            if (hexInput != null && !hexInput.trim().isEmpty()) {
                try {
                    if (!hexInput.startsWith("#")) hexInput = "#" + hexInput;
                    canvas.setCurrentColor(Color.decode(hexInput));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Hex format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return btnHex;
    }
}