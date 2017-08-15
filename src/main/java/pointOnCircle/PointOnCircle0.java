package pointOnCircle;

import paints.FitInCircle;

import java.awt.*;

public class PointOnCircle0 implements FitInCircle {

    public void draw(Graphics2D g2d, int x, int y, int radius) {
        int d = 2 * radius;
        g2d.setColor(Color.lightGray);
        g2d.drawOval(x - radius, y - radius, d, d);
    }
}
