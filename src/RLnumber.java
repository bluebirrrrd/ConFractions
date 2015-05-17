import java.util.LinkedList;
import java.util.List;

public class RLnumber {

    private static final int K_MAX = 53;
    private static final int K_MIN = -11;
    private static final int MAX_LENGTH = 8;
    private int sign;
    private int length;
    private int[] tail;

    public RLnumber() {
        sign = 0;
        length = 0;
        tail = new int[] {0};
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


}
