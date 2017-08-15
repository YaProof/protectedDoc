import calculate.Combination;
import paints.FitInCircle;
import paints.PaintDoc;

import java.util.HashMap;

public class ProtectedDoc {
    private byte digit;
    private byte column;
    private byte row;
    private int radius;
    private Combination combination;
    protected byte[] matrix;
    private PaintDoc paintDoc;

    private void setMatrixRand() {
        for (byte i = 1; i < matrix.length; i++) {
            matrix[i] = (byte) (Math.random() * (digit - 1));
        }
        matrix[0] = -1;
    }

    public ProtectedDoc(byte column, byte row, byte activeElementCount, byte digit,int radius) throws Exception {
        byte size = (byte)(column * row - 1);

        if (size >= Byte.MAX_VALUE)
            throw new Exception("ProtectedDoc. Количество элементов должно быть меньше " + Byte.MAX_VALUE + ", сейчас " + size);

        if (column < 2 || row < 2)
            throw new Exception("ProtectedDoc. Количество строк и столбцов не должно быть меньше 2");

        combination = new Combination(size, activeElementCount, digit);

        this.digit = digit;
        this.column = column;
        this.row = row;

        matrix = new byte[size + 1];
        this.radius = radius;
        paintDoc = new PaintDoc(radius, column, row, digit);
    }

    // Везвращает данные для построения матрицы, нулевой индекс = -1, это метка начала
    public byte[] getMatrix(long value) throws Exception {
        setMatrixRand();
        for (HashMap.Entry<Byte, Byte> mp: combination.getMapPossitionWithValue(value).entrySet())
            matrix[mp.getKey()] = mp.getValue();
        return matrix;
    }

    // Проверяем корректность переданных данных. передаем проверяемое значение и матрицу без метки начала
    public boolean isValidete(long value, byte[] matrix) {
        byte[] zn, position;
        if ((column * row - 1) != matrix.length) return false;
        if (value > combination.getMaxValue()) return false;

        try {
            zn = combination.getCombinationByIndex(combination.getIndexByValue(value));
            position = new byte[zn.length];

            for (byte i = 0; i < zn.length; i++) {
                if (zn[i] < 0 || zn[i] >= matrix.length) {
                    throw new Exception("isValidete. Выход за пределы индекса");
                }
                position[i] = matrix[zn[i] - 1];
            }

            return combination.getTransformToDec(position) == combination.truncNum(value);
        } catch (Exception e) {
            return false;
        }
    }

    public void resetFit(byte num, FitInCircle fitCirc) {
        paintDoc.resetFit(num, fitCirc);
    }

    public void draw(long value, boolean showCell) throws Exception {
        paintDoc.draw(getMatrix(value), showCell);
    }

    public void multyDraw(int height, int width, long value, boolean showCell) throws Exception {
        paintDoc.multyDraw(height, width, getMatrix(value), showCell);
    }
}
