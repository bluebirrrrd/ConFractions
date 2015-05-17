import java.util.LinkedList;
import java.util.List;

public class RLnumber {

    private static final int K_MAX = 53;
    private static final int K_MIN = -11;
    private int sign;
    private int length;
    private LinkedList<Integer> tail;

    public RLnumber() {
        sign = 0;
        length = 0;
        tail = new LinkedList<>();
    }

    public RLnumber(int sign, int length, int[] tail) {
        this.sign = sign;
        this.length = length;
        for (int i : tail) this.tail.add(i);
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
        for (Integer i : tail) {
            this.tail.add(i);
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

    public LinkedList<Integer> getTail() {
        return tail;
    }
//    TODO create a setTail method that converts int[] to LinkedList<Integer>
    public void setTail(LinkedList<Integer> tail) {
        this.tail = tail;
    }

    public void countLength() {
        length = tail.size();
    }

    public static RLnumber toRL(double number) {
        RLnumber result = null;
        List<Integer> tail = new LinkedList<>();
        /*
        double intPart = Math.floor(number);
        double fraction = number - intPart;
        */
        int sign = (number >= 0) ? 0 : 1;
        number = Math.abs(number);
        for (int k = K_MAX; k > K_MIN; k--) {
            double number1 = number;
            number1 = number1 - Math.scalb(1,k);
            if (number1 < 0) {
                number1 = number;
            } else {
                tail.add(k);
            }
        }

        result = new RLnumber(sign, tail.size(), tail);
        return result;
    }
}
