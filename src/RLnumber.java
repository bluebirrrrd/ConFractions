import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RLnumber {

    private static final int K_MAX = 32;
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
        this.tail = new int[tail.length];
        for (int i = 0; i < tail.length; i++) {
            this.tail[i] = tail[i];
        }
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(sign);
        result.append(".");
        result.append(length);
        result.append(".");
        for (int i = 0; i < length; i++) {
            result.append(tail[i]);
            result.append(".");
        }

        return result.toString();
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
        length = 0;
        for (int i : tail) {
            if (i > EMPTY_VALUE) {
                length++;
            }
        }
    }

    private void cutRL() {
        this.countLength();
        if (length > MAX_LENGTH) {
            int[] newTail = new int[MAX_LENGTH];
            for (int i = 0; i < MAX_LENGTH; i++) {
                newTail[i] = tail[i];
            }
            this.setTail(newTail);
            this.countLength();
        } else if (length < MAX_LENGTH) {
            this.sort();
            int[] newTail = new int[length];
            for (int i = 0; i < length; i++) {
                newTail[i] = tail[i];
            }
            this.setTail(newTail);
        }
    }

    public boolean isGreaterThan(RLnumber number) {
        int length = (this.getLength() < number.getLength()) ? this.getLength() : number.getLength();
        int[] tail1 = number.getTail();
        for (int i = 0; i < length; i++) {
            if (tail[i] > tail1[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForZero() {
        return (length == 0);
    }

    private static double pow (int number, int power) {
        double result = 1;
        if (power >= 0) {
            for (int i = 0; i < power; i++) {
                result *= number;
            }
        } else {
            for (int i = power; i < 0; i++) {
                result /= number;
            }
        }

        return result;
    }
    public static RLnumber toRL(double number) {
        RLnumber result;
        int[] tail = new int[MAX_LENGTH];
        for (int i = 0; i < tail.length; i++) {
            tail[i] = EMPTY_VALUE;
        }

        int sign = (number >= 0) ? 0 : 1;
        number = Math.abs(number);
        double number1 = number;
        int index = 0;
        for (int k = K_MAX; k > K_MIN; k--) {
            double backup = number1;
            number1 = number1 - pow(2,k);
            System.out.println(number1);
            if (number1 < 0) {
                number1 = backup;
            } else if (number1 > 0){
                tail[index] = k;
                index++;
            } else {
                tail[index] = k;
                break;
            }
            if (index >= MAX_LENGTH) break;
        }

        result = new RLnumber(sign, tail.length, tail);
        result.cutRL();
        return result;
    }

// TODO find a mistake.
    public static RLnumber sort(RLnumber number1, RLnumber number2) {
        number1.cutRL();
        number2.cutRL();
        RLnumber result;

        int[] resultingTail = new int[number1.getLength() + number2.getLength()];
        Integer[] tempTail = new Integer[number1.getLength() + number2.getLength()];
        int[] tail1 = number1.getTail();
        int[] tail2 = number2.getTail();

/*        for (int i = 0; i < resultingTail.length; i++) {
            resultingTail[i] = EMPTY_VALUE;
        } */
        System.arraycopy(tail1,0,resultingTail,0,tail1.length);
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
        System.arraycopy(resultingTail, 0, resultingTail1, 0, resultingTail.length);

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
        for (int i = 0; i < tail.length-1; i++) {
            if (tail[i] == tail[i+1]) {
                tail[i] = tail[i] + 1;
                for (int k = i + 1; k < tail.length - 1; k++) {
                    tail[k] = tail[k + 1];
                    tail[tail.length - 1] = EMPTY_VALUE;
                }

            }
            if (tail[i] == EMPTY_VALUE) break;
        }
        this.countLength();
    }

    public static RLnumber sum(RLnumber number1, RLnumber number2) {
        RLnumber result;

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


    public static RLnumber substract(RLnumber number1, RLnumber number2) {
        System.out.println(number1.toString() + " " + number2.toString());

        RLnumber greaterNumber;
        RLnumber smallerNumber;
        if (number1.isGreaterThan(number2)) {
            greaterNumber = number1;
            smallerNumber = number2;
        } else {
            greaterNumber = number2;
            smallerNumber = number1;
        }

        int[] tail1 = greaterNumber.getTail();
        int[] tail2 = smallerNumber.getTail();
        RLnumber result;

        for (int i = 0; i < greaterNumber.getLength(); i++) {
            for (int k = 0; k < smallerNumber.getLength(); k++)
            if (tail1[i] == tail2[k]) {
                tail1[i] = EMPTY_VALUE;
                tail2[k] = EMPTY_VALUE;
                break;
            }
        }
        //setting new tails to our RL numbers
        greaterNumber.setTail(tail1);
        smallerNumber.setTail(tail2);
        //and making them look pretty
        greaterNumber.sort();
        smallerNumber.sort();
        greaterNumber.countLength();
        smallerNumber.countLength();

        if (greaterNumber.checkForZero() || smallerNumber.checkForZero()) {
            return greaterNumber;
        }

        tail1 = greaterNumber.getTail();
        tail2 = greaterNumber.getTail();
        int greatestBitOf1 = tail1[0];
        int smallestBitOf2 = tail2[smallerNumber.getLength()-1];
        //це List, у якому міститься розклад типу Ni - Nk = N(i-1) N(i-2) ... Nk
        LinkedList<Integer> noIdeaHowToNameThisList = new LinkedList<>();
        for (int i = greatestBitOf1 - 1; i >= smallestBitOf2; i--) {
            noIdeaHowToNameThisList.add(i);
        }

        //this array will contain noIdeaHowToNameThisList and tail1 without its greatest bit
        int[] tempArray = new int[noIdeaHowToNameThisList.size() + greaterNumber.getLength() - 1];
        for (int i = 0; i < noIdeaHowToNameThisList.size(); i++) {
            tempArray[i] = (int)noIdeaHowToNameThisList.get(i);
        }

        System.arraycopy(tail1,1,tempArray,noIdeaHowToNameThisList.size(),greaterNumber.getLength() - 1);
        result = new RLnumber(greaterNumber.getSign(),1,tempArray);
        result.sort();
        result.mergeSimilar();
        result.countLength();

        return result;
    }

    public static RLnumber multiply(RLnumber number1, RLnumber number2) {

        RLnumber result;
        int signOfResult = number1.getSign() * number2.getSign();
        LinkedList<Integer> partialProductList = new LinkedList<>();
        int[] tail1 = number1.getTail();
        int[] tail2 = number2.getTail();
        int[] partialProductArray;
        for (int i = 0; i < number1.getLength(); i++) {
            for (int j = 0; j < number2.getLength(); j++) {
                int temp = tail1[i] + tail2[j];
                partialProductList.add(temp);
                System.out.print(temp + ".");
            }
        }
        System.out.println("End of partials");

        partialProductArray = new int[partialProductList.size()];
        for (int i = 0; i < partialProductArray.length; i++) {
            partialProductArray[i] = partialProductList.get(i);
        }
        result = new RLnumber(signOfResult, partialProductArray.length, partialProductArray);
        result.sort();
        result.mergeSimilar();
        result.countLength();
        System.out.println("After merge we have this: " + result.toString());
        result.cutRL();

        return result;

    }

    //TODO add skipping EMPTY_VALUEs in all operations
    public static RLnumber divide(RLnumber dividend, RLnumber divisor) {
        RLnumber result;
        if (divisor.checkForZero()) {
            result = null;
        } else {
            RLnumber tempNumber = dividend;
            int divisorHeadBit = divisor.getTail()[0];
            int signOfResult = dividend.getSign() * divisor.getSign();
            int[] tailOfResult = new int[MAX_LENGTH];
            for (int i = 0; i < MAX_LENGTH; i++) {
                tailOfResult[i] = tempNumber.getTail()[0] - divisorHeadBit;
                RLnumber temp = new RLnumber(signOfResult,i+1,tailOfResult);
                RLnumber product = multiply(temp,divisor);
                RLnumber remainder;

                if (product.isGreaterThan(tempNumber)) {
                    tailOfResult[i] -= 1;
                    product = multiply(temp, divisor);
                }

                remainder = substract(tempNumber, product);
                tempNumber = remainder;
            }
            result = new RLnumber(signOfResult,MAX_LENGTH,tailOfResult);
        }
        return result;
    }
}
