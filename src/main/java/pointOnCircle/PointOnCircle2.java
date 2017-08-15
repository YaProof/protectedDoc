package pointOnCircle;

import paints.FitInCircle;

import java.awt.*;

public class PointOnCircle2 implements FitInCircle {
    public void draw(Graphics2D g2d, int x, int y, int radius) {
        g2d.setColor(Color.lightGray);
        int a = (int)((double)radius / Math.sqrt(2));
        g2d.drawRect(x - a, y - a, 2 * a, 2 * a);
    }
}
