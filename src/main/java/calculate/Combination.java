package calculate;

import java.util.HashMap;
import java.util.Map;

public class Combination {
    private byte size; // Количество всех элементов
    private byte count; // Количество активных элементов
    private byte digit; //Ричность системы. 2 - двоичная, 8 - восмиричная
    private long maxValueInOneCombination; // Максимально возможное значение для одной комбинации
    private long maxCountCombination; // Максимальное количество сочетаний
    private long maxValue; // Максимальное значение которое можно записать
    private Map<Byte, Long> factorialMap = new HashMap<Byte, Long>();

    private long getFactorial(byte value) {
        Long result = factorialMap.get(value);
        if (!(result instanceof Long)) {
            result = 1L;
            for ( ; value > 0; result *= value--);
        }
        return result;
    }

    private long calcCombination(byte size, byte count) {
        if (size == 0 || count == 0)
            return 0L;

        return getFactorial(size) / (getFactorial((byte)(size - count)) * getFactorial(count));
    }

    private void setMaxValueInOneCombination() {
        this.maxValueInOneCombination =  (long)(Math.pow(this.digit, this.count));
    }

    private boolean isPositionSort(byte[] position) {
        for (byte i = 1; i < position.length; i++) {
            if (position[i] < position[i - 1]) {
                return false;
            }
        }
        return true;
    }

    private void setMaxCountCombination() {
        this.maxCountCombination = this.calcCombination(this.size, this.count);
    }

    private void setMaxValue() {
        this.maxValue = this.maxValueInOneCombination * this.maxCountCombination;
    }

    public byte getSize() {
        return size;
    }
    public byte getCount() {
        return count;
    }
    public byte getDigit() {
        return digit;
    }

    public long getMaxValueInOneCombination() {
        return maxValueInOneCombination;
    }
    public long getMaxCountCombination() {
        return maxCountCombination;
    }
    public long getMaxValue() {
        return maxValue;
    }

    public Combination(byte size, byte count, byte digit) throws Exception {
        if (size < 2 || count < 2 || digit < 2)
            throw new Exception("Combination. Значение параметров должно быть больше 1");

        if (count > size)
            throw new Exception("Combination. Параметр size не может быть меньше параметра count");

        this.size = size;
        this.count = count;
        this.digit = digit;

        setMaxValueInOneCombination();
        setMaxCountCombination();
        setMaxValue();
    }

    // Возвращает индекс сочетания по позициям в массиве (нумерация начинается с 1)
    public long getIndexCombination(byte[] position) throws Exception  {
        long result = 1;
        byte i, j, n, k;
        byte to;

        if (position.length != this.count)
            throw new Exception("getIndexCombination. Количество элементов в массиве должно совпадать с количеством активных элементов заданных в конструкторе");

        if (!isPositionSort(position))
            throw new Exception("getIndexCombination. Значения позиций должны быть отсортированые по возрастанию");

        for (i = 1; i <= count; i++) {
            if (i == 1) {
                j = 1;
            } else {
                j = (byte) (position[i - 2] + 1);
            }
            to = (byte) (position[i - 1] - 1);

            while (j <= to) {
                n = (byte)(count - i);
                k = (byte)(size - j);

                if (n == 0) {
                    result++;
                } else {
                    result += calcCombination(k, n);
                }

                j++;
            }
        }

        return result;
    }

    // Возвращает индексы (нумерация индексов с 1) позиций по номеру сочетания
    public byte[] getCombinationByIndex(long position) throws Exception {
        byte[] result = new byte[this.count];
        long v, n = 0;
        byte i, j, k;

        if (position > maxCountCombination)
            throw new Exception("getCombinationByIndex. Переданный индекс превышает максимально возможное значение индекса " + maxCountCombination);

        j = 1;
        n = 0;
        for (i = 1; i <= this.count; i++) {
            if (i == 1) { k = 1; }
            else { k = (byte)(result[i - 2] + 1); }

            v = 0;
            while (n < position) {
                v = 0;
                while (k <= j) {
                    if ((byte)(count - i) == 0) {
                        v = 1;
                    } else {
                        v += calcCombination((byte) (size - k), (byte) (count - i));
                    }
                    k++;
                }
                j++;
                n += v;
            }
            n -= v;
            result[i - 1] = (byte)(j - 1);
        }

        return result;
    }

    // Возвращаем индекс сочетания по значению
    public long getIndexByValue(long value) throws Exception  {
        long result;
        if (value > maxValue)
            throw new Exception("getIndexByValue. Переданное значение превышает максимально возможное, которое равно: " + maxValue);

        if (value % maxValueInOneCombination == 0) {
            result = (long)(value / maxValueInOneCombination);
        } else {
            result = (long) Math.ceil((double)value / (double)maxValueInOneCombination);
        }

        return result;
    }

    // уменьшаем число, что бы оно не превышало максмально возможное значение в группе
    public long truncNum(long value) {
        long result;
        if (value % maxValueInOneCombination == 0) {
            result = maxValueInOneCombination - 1;
        } else {
            result = (value % maxValueInOneCombination);// - 1;
        }
        return result;
    }

    // Конвертируем число из 10 системы в указанную при создании
    public byte[] getTransformByDec(long value) throws Exception {
        byte[] result = new byte[count];
        byte p = (byte)(result.length - 1);
        long v, m;

        if (value > maxValue)
            throw new Exception("getTransformByDec. Переданное значение превышает максимально возможное, которое равно: " + maxValue);

       v = truncNum(value);

        while (v > digit) {
            m = v / digit;
            result[p] = (byte)(v - m * digit);
            v = m;
            p--;
        }
        result[p] = (byte)v;

        return result;
    }

    // Конвертируем число из указанной при создании системы счисления в десятичную
    public long getTransformToDec(byte[] value) throws Exception {
        long v, result = 0L;
        if (value.length !=  count)
            throw new Exception("getTransformToDec. Количество элементов в массиве не корректно, должно быть " + count);

        v = value.length - 1;

        for (byte i = 0; i < value.length; i++) {
            result += Math.pow(digit, v) * value[i];
            v--;
        }

        return result;
    }

    // Возвращается позиция в массиве и ее значение
    public Map<Byte, Byte> getMapPossitionWithValue(long value) throws Exception {
        Map<Byte, Byte> result = new HashMap<Byte, Byte>();
        byte[] position, transform;

        if (value > maxValue)
            throw new Exception("getMapPossitionWithValue. Переданное значение превышает максимально возможное, которое равно: " + maxValue);

        position = getCombinationByIndex(getIndexByValue(value));
        transform = getTransformByDec(value);

        if (position.length != transform.length)
            throw new Exception("getMapPossitionWithValue. Ошибка при рассчетах, сообщите автору с указанием введеных параметров");

        for (byte i = 0; i < position.length; i++) {
            result.put(position[i], transform[i]);
        }

        return result;
    }
}
