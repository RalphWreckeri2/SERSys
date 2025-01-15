package sersystem;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

class RoundedPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cornerRadius = 20;

    public RoundedPanel(LayoutManager layout, int radius) {
        super(layout);
        cornerRadius = radius;
        setOpaque(false);
    }

    public RoundedPanel(LayoutManager layout) {
        this(layout, 20);
    }

    public RoundedPanel(int radius) {
        this(new FlowLayout(), radius);
    }

    public RoundedPanel() {
        this(new FlowLayout(), 20);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the rounded panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
    }
}