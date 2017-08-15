package pointOnCircle;

import paints.FitInCircle;

import java.awt.*;

public class PointOnCircleLabel implements FitInCircle {

    public void draw(Graphics2D g2d, int x, int y, int radius) {
        g2d.setColor(Color.lightGray);
        g2d.drawLine(x, y - radius, x, y + radius);
        g2d.drawLine(x, y - radius, x + radius, y);
        g2d.drawLine(x, y + radius, x + radius, y);
    }
}
