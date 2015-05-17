import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RLnumber {

    private static final int K_MAX = 53;
    private static final int K_MIN = -11;
    private static final int MAX_LENGTH = 8;
    private static final int EMPTY_VALUE = -12; //cells that are equal to this value should be taken as empty
    private int sign;
    private int length;
    private int[] tail;

    public RLnumber() {
        sign = 0;
        length = 0;
        tail = new int[MAX_LENGTH];
        for (int i = 0; i<MAX_LENGTH; i++) {
            tail[i] = -12;
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
    /*  TODO write the right counter of length (it should skip -12 in content of tail)*/
    public void countLength() {
        length = tail.length;
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
            resultingTail[i] = -12;
        }

        System.arraycopy(tail1, 0, resultingTail, 0, tail1.length);
        System.arraycopy(tail2, 0, resultingTail, tail1.length, tail2.length);

        /* copying content of resultingTail to tempTail */
        for (int j = 0; j < tempTail.length; j++) {
            tempTail[j] = resultingTail[j];
        }

        Arrays.sort(tempTail, Collections.reverseOrder());

        for (int k = 0; k < tempTail.length; k++) {
            resultingTail[k] = (int)tempTail[k];
        }

        result = new RLnumber(number1.getSign(),tempTail.length, resultingTail);
        return result;
    }


}
