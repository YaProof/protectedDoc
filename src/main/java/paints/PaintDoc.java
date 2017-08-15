package paints;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PaintDoc {
    private int canvasWidth; // Расчетная ширина полотна на основании радиуса
    private int canvasHeight; // Расчетная высота полотна на основании радиуса
    private int radius; // Радиус окружности, в которую вписываем значения
    private byte column; // Количество колонок в матрице
    private byte row; // количество строк в матрице
    private byte digit; // Ричность значений
    private byte[] matrix; // Значения которые вписываем
    private HashMap<Byte, FitInCircle> fit = new HashMap<Byte, FitInCircle>(); // Хранится реализация вписывания в окружность числа [-1; digit - 1]

    private void calculateSizeCanvas() {
        canvasWidth = (column * 2 + column - 1) * radius;
        canvasHeight = (row * 2 + row - 1) * radius;
    }

    private void drawCell(Graphics2D g2d) {
        int x1, y1, x2, y2;
        g2d.setColor(Color.red);

        x2 = 2 * radius;
        y2 = x2;
        for (int i = 0; i < row; i++) {
            y1 = i * 3 * radius;
            for (int j = 0; j < column; j++) {
                x1 = j * 3 * radius;
                g2d.drawOval(x1, y1, x2, y2);
            }
            g2d.drawLine(0, y1 + radius, canvasWidth, y1 + radius);
        }

        for (int i = 0; i < column; i++) {
            x1 = i * 3 * radius + radius;
            g2d.drawLine(x1, 0, x1, canvasHeight);
        }
    }

    private void drawValue(Graphics2D g2d){
        int i = 0, j = 0;
        int x, y;
        for (byte k = 0; k < matrix.length; k++) {
            if (i == column) {
                i = 0;
                j++;
            }

            x = i * 3 * radius + radius;
            y = j * 3 * radius + radius;
            fit.get(matrix[k]).draw(g2d, x,  y, radius);

            i++;
        }
    }

    private void savePNG(Graphics2D g2d,  BufferedImage bufferedImage, String filepath) {
        //g2d.dispose();
        RenderedImage rendImage = bufferedImage;

        File file = new File(filepath);
        try {
            ImageIO.write(rendImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Проверка корректности и достаточности необходимых данных
    private void isValidate() throws Exception {
        if ((column * row) != matrix.length)
            throw new Exception("isValidate. Не совпадает количество в матрице значений и произведение количества колонок и строк");

        for (byte i = -1; i < digit; i++) {
            if (!(fit.get(i) instanceof FitInCircle))
                throw new Exception("isValidate. Не задано отрисовка для числа: " + i);
        }
    }

    // Значения задаются в пикселях
    public PaintDoc(int radius, byte column, byte row, byte digit) throws Exception {
        if (radius < 2 || column < 2 || row < 2 || digit < 2)
            throw new Exception("paints.PaintDoc. Значение параметров должна быть больше 2");

        this.radius = radius;
        this.column = column;
        this.row = row;

        calculateSizeCanvas();

        this.digit = digit;
    }

    public void resetFit(byte num, FitInCircle fitCirc) {
        fit.put(num, fitCirc);
    }

    public void draw(byte[] matrix, boolean showCell) throws Exception {
        this.matrix = matrix;
        BufferedImage bufferedImage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        isValidate();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, canvasWidth, canvasHeight);

        if (showCell) {
            drawCell(g2d);
            savePNG(g2d, bufferedImage, "result//cell.png");
        }

        drawValue(g2d);
        savePNG(g2d, bufferedImage, "result//value.png");
    }

    public void multyDraw(int height, int width, byte[] matrix, boolean showCell) throws Exception {
        this.matrix = matrix;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int h = 0, w = 0;

        Graphics2D g2d = bufferedImage.createGraphics();

        isValidate();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        if (showCell)
            drawCell(g2d);

        drawValue(g2d);

        while (h < height) {
            w = 0;
            while (w < width) {
                g2d.drawImage(bufferedImage, null, w, h);
                w += canvasWidth + radius;
            }
            h += canvasHeight + radius;
        }

        savePNG(g2d, bufferedImage, "result//multy.png");
    }
}
