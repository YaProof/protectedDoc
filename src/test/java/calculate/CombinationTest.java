package calculate;

import org.junit.Test;
import static junit.framework.Assert.*;

public class CombinationTest {
    @Test
    public void getMaxValueInOneCombination() throws Exception {
        byte size = 5, count = 3, digit = 8;
        Combination combination = new Combination(size, count, digit);
        long v = combination.getMaxValueInOneCombination();
        assertEquals("getMaxValueInOneCombination воззвращает не корректное значение", v, 512);
    }

    @Test
    public void getMaxCountCombination() throws Exception {
        byte size = 5, count = 3, digit = 8;
        Combination combination = new Combination(size, count, digit);
        long v = combination.getMaxCountCombination();
        assertEquals("getMaxCountCombination воззвращает не корректное значение", v, 10);
    }

    @Test
    public void getMaxCountCombination1() throws Exception {
        byte size = 16, count = 8, digit = 8;
        Combination combination = new Combination(size, count, digit);
        long v = combination.getMaxCountCombination();
        assertEquals("getMaxCountCombination воззвращает не корректное значение", v, 12870);
    }

    @Test
    public void getMaxValue() throws Exception {
        byte size = 5, count = 3, digit = 8;
        Combination combination = new Combination(size, count, digit);
        long v = combination.getMaxValue();
        assertEquals("getMaxValue воззвращает не корректное значение", v, 5120);
    }

    @Test
    public void getIndexCombinationFirst() throws Exception {
        byte size = 5, count = 3, digit = 8;
        byte[] p = {1, 2, 3};
        Combination combination = new Combination(size, count, digit);
        long v = combination.getIndexCombination(p);
        assertEquals("getIndexCombinationFirst воззвращает не корректное значение ", v, 1);
    }

    @Test
    public void getIndexCombination() throws Exception {
        byte size = 5, count = 3, digit = 8;
        byte[] p = {2, 4, 5};
        Combination combination = new Combination(size, count, digit);
        long v = combination.getIndexCombination(p);
        assertEquals("getIndexCombination воззвращает не корректное значение ", v, 9);
    }

    @Test
    public void getIndexCombinationLast() throws Exception {
        byte size = 5, count = 3, digit = 8;
        byte[] p = {3, 4, 5};
        Combination combination = new Combination(size, count, digit);
        long v = combination.getIndexCombination(p);
        assertEquals("getIndexCombinationLast воззвращает не корректное значение ", v, 10);
    }

    @Test
    public void getCombinationByIndexFirst() throws Exception {
        byte size = 5, count = 3, digit = 8, m = 1;
        byte[] p = {1, 2, 3};
        boolean v = true;
        Combination combination = new Combination(size, count, digit);
        byte[] ret = combination.getCombinationByIndex(m);

        for (byte i = 0; i < p.length; i++) {
            if (p[i] != ret[i]) {
                v = false;
                break;
            }
        }

        assertTrue("getCombinationByIndexFirst воззвращает не корректное значение ", v);
    }

    @Test
    public void getCombinationByIndex() throws Exception {
        byte size = 5, count = 3, digit = 8, m = 9;
        byte[] p = {2, 4, 5};
        boolean v = true;
        Combination combination = new Combination(size, count, digit);
        byte[] ret = combination.getCombinationByIndex(m);

        for (byte i = 0; i < p.length; i++) {
            if (p[i] != ret[i]) {
                v = false;
                break;
            }
        }

        assertTrue("getCombinationByIndex воззвращает не корректное значение ", v);
    }

    @Test
    public void getCombinationByIndexLast() throws Exception {
        byte size = 5, count = 3, digit = 8, m = 10;
        byte[] p = {3, 4, 5};
        boolean v = true;
        Combination combination = new Combination(size, count, digit);
        byte[] ret = combination.getCombinationByIndex(m);

        for (byte i = 0; i < p.length; i++) {
            if (p[i] != ret[i]) {
                v = false;
                break;
            }
        }

        assertTrue("getCombinationByIndex воззвращает не корректное значение ", v);
    }

    @Test
    public void getIndexByValue() throws Exception {
        byte size = 5, count = 3, digit = 8;
        Combination combination = new Combination(size, count, digit);
        long v = combination.getIndexByValue(1000L);
        assertEquals("getIndexByValue воззвращает не корректное значение", v, 2);
    }

    @Test
    public void truncNum() throws Exception {
        byte size = 5, count = 3, digit = 8;
        Combination combination = new Combination(size, count, digit);
        long v = combination.truncNum(520L);
        assertEquals("truncNum воззвращает не корректное значение", v, 8);
    }


    @Test
    public void getTransformByDec() throws Exception {
        byte size = 5, count = 3, digit = 8;
        long m = 503;
        byte[] p = {7, 6, 7};
        boolean v = true;
        Combination combination = new Combination(size, count, digit);
        byte[] ret = combination.getTransformByDec(m);

        for (byte i = 0; i < p.length; i++) {
            if (p[i] != ret[i]) {
                v = false;
                break;
            }
        }

        assertTrue("getTransformByDec воззвращает не корректное значение ", v);
    }

    @Test
    public void getTransformToDec() throws Exception {
        byte size = 5, count = 3, digit = 8;
        long m = 503;
        byte[] p = {7, 6, 7};
        long v;
        Combination combination = new Combination(size, count, digit);
        v = combination.getTransformToDec(p);

        assertEquals("truncNum воззвращает не корректное значение", v, m);
    }
}