package pointOnCircle;

import paints.FitInCircle;

import java.awt.*;

public class PointOnCircle1 implements FitInCircle {

    public void draw(Graphics2D g2d, int x, int y, int radius) {
        g2d.setColor(Color.lightGray);
        g2d.drawLine(x, y - radius, x + radius, y);
        g2d.drawLine(x + radius, y, x, y + radius);
        g2d.drawLine(x, y + radius, x - radius, y);
        g2d.drawLine(x - radius, y, x, y - radius);
    }
}
