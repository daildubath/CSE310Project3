import javax.swing.*;
import java.awt.*;

/**
 * ============================================================================
 * CLASS SUMMARY: PaintApp.java
 * ============================================================================
 * This class serves as the main entry point and user interface controller for
 * the application. It acts as the "View" and "Controller" in an MVC-style setup,
 * initializing the main window framework (JFrame), organizing the layout, and
 * constructing a dual-row tool management area.
 * -----------------------------------------------------------------------------
 * It bridges the gap between user inputs (button clicks, color choices, sliders)
 * and the rendering canvas (CanvasPanel) or persistent data layers (FileHandler).
 * ============================================================================
 */
class PaintApp {

    /**
     * The application entry point utilizing modern Java 26 implicitly declared
     * class structure (omitting public, static, and String[] args boilerplate).
     */
    void main() {
        // Safely schedules the GUI construction on the Swing Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new PaintApp().createAndShowGUI());
    }

    /**
     * Initializes, configures, and displays the graphical user interface.
     */
    private void createAndShowGUI() {
        // --- WINDOW SETUP ---
        JFrame frame = new JFrame("Simple Paint - Release Candidate");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout()); // Splits window into North, South, East, West, Center

        // Instantiates the primary custom rendering component
        CanvasPanel canvas = new CanvasPanel();

        // --- MULTI-ROW TOOLBAR LAYOUT OVERHAUL ---
        // Stack container to vertically arrange independent control rows
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Row 1: Dedicated to active drawing utilities and document file management
        JToolBar mainTools = new JToolBar();
        mainTools.setFloatable(false); // Locks toolbar position

        // Row 2: Dedicated to active stroke properties (color selection and thickness)
        JToolBar propertyTools = new JToolBar();
        propertyTools.setFloatable(false);

        // --- BUTTON & CONTROLLER INSTANTIATION ---
        JButton btnFreehand = new JButton("Freehand");
        JButton btnLine = new JButton("Line");
        JButton btnRect = new JButton("Rectangle");
        JButton btnOval = new JButton("Oval");

        JButton btnUndo = new JButton("Undo");
        JButton btnRedo = new JButton("Redo");

        JButton btnSave = new JButton("Save Project");
        JButton btnLoad = new JButton("Open Project");
        JButton btnExport = new JButton("Export to PNG");

        // Native operating system dialogue module for locating or naming project files
        JFileChooser fileChooser = new JFileChooser();

        // --- ACTION LISTENERS: ACTIVE STRATEGY UPDATES ---
        // Java 26 Unnamed Variables (_) ignore unused action event parameters, eliminating IDE warnings
        btnFreehand.addActionListener(_ -> canvas.setCurrentTool("Freehand"));
        btnLine.addActionListener(_ -> canvas.setCurrentTool("Line"));
        btnRect.addActionListener(_ -> canvas.setCurrentTool("Rectangle"));
        btnOval.addActionListener(_ -> canvas.setCurrentTool("Oval"));

        // Connects interface inputs directly to canvas history collections
        btnUndo.addActionListener(_ -> canvas.undo());
        btnRedo.addActionListener(_ -> canvas.redo());

        // ====================================================================
        // REQUIREMENT MET: File I/O (Save Operation)
        // ====================================================================
        // Launches file system browser to fetch an absolute location path, then
        // streams out the underlying array collection via serialization.
        btnSave.addActionListener(_ -> {
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                FileHandler.saveArtwork(canvas.getShapes(), fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        // ====================================================================
        // REQUIREMENT MET: File I/O (Read Operation)
        // ====================================================================
        // Reads serialized binary project state back into memory and explicitly
        // overwrites the data array layout inside the canvas panel.
        btnLoad.addActionListener(_ -> {
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                canvas.setShapes(FileHandler.loadArtwork(fileChooser.getSelectedFile().getAbsolutePath()));
            }
        });

        // ====================================================================
        // REQUIREMENT MET: File I/O (Image Export)
        // ====================================================================
        // Converts vector layout coordinates to an uncompressed raster bitmap PNG file
        btnExport.addActionListener(_ -> {
            if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                FileHandler.exportImage(canvas, fileChooser.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(frame, "Image exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // --- LAYOUT ROW 1 CONSTRUCTION ---
        mainTools.add(btnFreehand); mainTools.add(btnLine); mainTools.add(btnRect); mainTools.add(btnOval);
        mainTools.addSeparator();
        mainTools.add(btnUndo); mainTools.add(btnRedo);
        mainTools.addSeparator();
        mainTools.add(btnSave); mainTools.add(btnLoad); mainTools.add(btnExport);

        // --- LAYOUT ROW 2 CONSTRUCTION: COLOR PALETTE ---
        propertyTools.add(new JLabel(" Colors: "));

        // Built-in color sequence array powering the visual swatch selector palette
        Color[] paletteColors = {
                Color.BLACK, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.WHITE,
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN,
                Color.BLUE, Color.MAGENTA, Color.PINK, Color.CYAN
        };

        // ====================================================================
        // REQUIREMENT MET: Loops (UI Generation)
        // ====================================================================
        // Loops through the fixed array of color presets to programmatically
        // construct, format, and assign behavior to individual swatches.
        for (Color c : paletteColors) {
            JButton colorBtn = new JButton();
            colorBtn.setBackground(c);
            colorBtn.setPreferredSize(new Dimension(25, 25));
            colorBtn.setMaximumSize(new Dimension(25, 25));
            colorBtn.setOpaque(true);
            colorBtn.setBorderPainted(false);
            // Updates canvas drawing context to match this swatch button's color background
            colorBtn.addActionListener(_ -> canvas.setCurrentColor(c));
            propertyTools.add(colorBtn);
        }

        // ====================================================================
        // REQUIREMENT MET: Functions / Custom Methods (UI Delegation)
        // ====================================================================
        // Delegates isolated layout configuration logic to a dedicated internal
        // creator routine to keep structural composition clean.
        JButton btnHex = createHexButton(frame, canvas);

        propertyTools.add(Box.createHorizontalStrut(5)); // Adds layout padding
        propertyTools.add(btnHex);
        propertyTools.addSeparator();

        // --- STROKE THICKNESS CONTROL ---
        propertyTools.add(new JLabel(" Thickness: "));
        JSlider thicknessSlider = new JSlider(1, 20, 2); // Ranges 1px to 20px, defaults at 2px
        thicknessSlider.setMaximumSize(new Dimension(150, 30));
        // Real-time slider adjustment updates active drawing stroke state variable
        thicknessSlider.addChangeListener(_ -> canvas.setCurrentThickness(thicknessSlider.getValue()));
        propertyTools.add(thicknessSlider);

        // --- VIEW ASSEMBLY ---
        topPanel.add(mainTools);
        topPanel.add(propertyTools);

        frame.add(topPanel, BorderLayout.NORTH); // Pins tool controls cleanly at top edge
        frame.add(canvas, BorderLayout.CENTER);  // Canvas scales dynamically into remaining view region
        frame.setVisible(true);
    }

    /**
     * ====================================================================
     * REQUIREMENT MET: Functions / Custom Methods
     * ====================================================================
     * Helper routine isolated to extract hex text processor interface setup.
     * * @param frame  The parent application window boundary container.
     * @param canvas The targeted drawing area update listener.
     * @return Fully structured custom Hex button context instance.
     */
    private JButton createHexButton(JFrame frame, CanvasPanel canvas) {
        JButton btnHex = new JButton("Hex...");
        btnHex.addActionListener(_ -> {
            // Displays a pop-up input text collection entry dialogue box
            String hexInput = JOptionPane.showInputDialog(frame, "Enter Hex Color:");
            if (hexInput != null && !hexInput.trim().isEmpty()) {
                try {
                    // Normalizes missing standard hex format indicators
                    if (!hexInput.startsWith("#")) hexInput = "#" + hexInput;

                    // Decodes alphanumeric string hex representation directly into an active Color entity
                    canvas.setCurrentColor(Color.decode(hexInput));
                } catch (NumberFormatException ex) {
                    // Catches illegal color string exceptions safely without triggering crash conditions
                    JOptionPane.showMessageDialog(frame, "Invalid Hex format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return btnHex;
    }
}