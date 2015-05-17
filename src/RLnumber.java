import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RLnumber {

    private static final int K_MAX = 53;
    private static final int K_MIN = -17;
    private static final int MAX_LENGTH = 8;
    private static final int EMPTY_VALUE = -18; //cells that are equal to this value should be taken as empty
    private int sign;
    private int length;
    private int[] tail;

    public RLnumber() {
        sign = 0;
        length = 0;
        tail = new int[MAX_LENGTH];
        for (int i = 0; i<MAX_LENGTH; i++) {
            tail[i] = -EMPTY_VALUE;
        }
    }

    public RLnumber(int sign, int length, int[] tail) {
        this.sign = sign;
        this.length = length;
        this.tail = tail.clone();
    }

    /**
     * creates an RLnumber from LinkedList of Integers by making a hard copy of this LinkedList
     * (using get() to retrieve the elements from incoming LinkedList)
     * @param sign - sign of a number
     * @param length - length of RL tail
     * @param tail - significant 1s in binary presentation of a given number
     */
    public RLnumber(int sign, int length, List<Integer> tail) {
        this.sign = sign;
        this.length = length;
        for (int i = 0; i<tail.size(); i++) {
            this.tail[i] = (int)tail.get(i);
        }
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getTail() {
        return tail;
    }

    public void setTail(int[] tail) {
        this.tail = tail;
    }
    /*  TODO write the right counter of length (it should skip -18 in content of tail)*/
    public void countLength() {
        length = tail.length;
    }

    public boolean checkForZero() {
        return (length == 0);
    }
    public static RLnumber toRL(double number) {
        RLnumber result = null;
        int[] tail = new int[MAX_LENGTH];
        /*
        double intPart = Math.floor(number);
        double fraction = number - intPart;
        */
        int sign = (number >= 0) ? 0 : 1;
        number = Math.abs(number);
        for (int k = K_MAX; k > K_MIN; k--) {
            int index = 0;
            double number1 = number;
            number1 = number1 - Math.scalb(1,k);
            if (number1 < 0) {
                number1 = number;
            } else {
                tail[index] = k;
                index++;
            }
            if (index > MAX_LENGTH) break;
        }

        result = new RLnumber(sign, tail.length, tail);
        return result;
    }

    public static RLnumber sort(RLnumber number1, RLnumber number2) {
        RLnumber result = null;
        int[] resultingTail = new int[2*MAX_LENGTH];
        Integer[] tempTail = new Integer[2*MAX_LENGTH];
        int[] tail1 = number1.getTail();
        int[] tail2 = number2.getTail();

        for (int i = 0; i < resultingTail.length; i++) {
            resultingTail[i] = EMPTY_VALUE;
        }

        System.arraycopy(tail1, 0, resultingTail, 0, number1.getLength());
        System.arraycopy(tail2, 0, resultingTail, number1.getLength(), number2.getLength());

        /* copying content of resultingTail to tempTail */
        for (int j = 0; j < tempTail.length; j++) {
            tempTail[j] = resultingTail[j];
        }

        Arrays.sort(tempTail, Collections.reverseOrder());

        for (int k = 0; k < tempTail.length; k++) {
            resultingTail[k] = (int)tempTail[k];
        }

        int[] resultingTail1 = new int[MAX_LENGTH];
        for (int l = 0; l < resultingTail1.length; l++) {
            resultingTail1[l] = resultingTail[l];
        }

        result = new RLnumber(number1.getSign(), resultingTail1.length, resultingTail1);
        return result;
    }

    public void sort() {
        Integer[] tempTail = new Integer[tail.length];
        for (int i = 0; i < tail.length; i++) {
            tempTail[i] = tail[i];
        }

        Arrays.sort(tempTail, Collections.reverseOrder());

        for (int k = 0; k < tempTail.length; k++) {
            tail[k] = (int)tempTail[k];
        }
    }

    /* TODO check whether the method is working right */
    public void mergeSimilar() {
        this.sort();
        for (int i = 0; i < tail.length - 1; i++) {
            if (tail[i] == tail[i+1]) {
                tail[i+1] = EMPTY_VALUE;
                i++;
            }
            if (tail[i] == EMPTY_VALUE) break;
        }
        this.countLength();
    }

    public RLnumber sum(RLnumber number1, RLnumber number2) {
        RLnumber result = null;
        if ((!number1.checkForZero()) && (!number2.checkForZero())) {
            result = sort(number1, number2);
            result.mergeSimilar();
        } else {
            if (number1.checkForZero()) {
                result = number2;
            } else {
                result = number1;
            }
        }
        return result;
    }


}
