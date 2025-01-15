package sersystem;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class RoundedButton extends JButton {
    private static final long serialVersionUID = 1L;
    private int cornerRadius = 15;

    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(getBackground().darker());
        } else {
            g.setColor(getBackground());
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
        g2.fill(r2d);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, cornerRadius, cornerRadius);
        g2.draw(r2d);
    }
}