package sersystem;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class RoundedImageLabel extends JLabel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shape shape;
    private int cornerRadius = 25; // You can adjust this value

    public RoundedImageLabel(ImageIcon image) {
        super(image);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(shape);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        shape = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);
    }
}