import pointOnCircle.PointOnCircle0;
import pointOnCircle.PointOnCircle1;
import pointOnCircle.PointOnCircle2;
import pointOnCircle.PointOnCircleLabel;

public class Main {

    public static void main(String[] args)  {
        try {
            byte column = 3;
            byte row = 2;
            byte activeElementCount = 3;
            byte digit = 3;
            int radius = 10;
            long value = 100L;

            ProtectedDoc protectedDoc = new ProtectedDoc(column, row, activeElementCount, digit, radius);

            protectedDoc.resetFit((byte)(-1), new PointOnCircleLabel()); // Треугольник (метка)
            protectedDoc.resetFit((byte)(0), new PointOnCircle0()); // Окружность
            protectedDoc.resetFit((byte)(1), new PointOnCircle1()); // Ромб
            protectedDoc.resetFit((byte)(2), new PointOnCircle2()); // Квадрат

            protectedDoc.draw(value, true);
            protectedDoc.multyDraw(1126, 796, value, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
