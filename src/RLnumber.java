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
        for (int i = 0; i < MAX_LENGTH; i++) {
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
     *
     * @param sign   - sign of a number
     * @param length - length of RL tail
     * @param tail   - significant 1s in binary presentation of a given number
     */
    public RLnumber(int sign, int length, List<Integer> tail) {
        this.sign = sign;
        this.length = length;
        for (int i = 0; i < tail.size(); i++) {
            this.tail[i] = (int) tail.get(i);
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();


        result.append(sign);
        result.append(".");
        result.append(length);
        result.append(".");
        if (length == 0) result.append("0");
        else {
            for (int i = 0; i < length; i++) {
                result.append(tail[i]);
                result.append(".");
            }
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

    /*public boolean isGreaterThan(RLnumber number) {

       /* boolean result = false;

        int length;
        length = (this.length < number.getLength()) ? this.length : number.getLength();

        int[] tail1 = number.getTail();
        int i;
        for (i = 0; i < length; i++) {
            if (this.tail[i] != tail1[i]) {
                result = this.tail[i] > tail1[i];
                break;
            } else { continue; }
        }

        if ((i == length - 1) && (this.getLength() > length)) {
            result = true;
        } else { result = false; }
        return result;*/

      /*  int length = (this.getLength() < number.getLength()) ? this.getLength() : number.getLength();
        int[] tail1 = number.getTail();
        for (int i = 0; i < length; i++) {
            if (tail[i] > tail1[i]) {
                return true;
            }
        }
        return false;
    } */

    public boolean checkForZero() {
        return (length == 0);
    }

    private static double pow(int number, int power) {
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

    /* works right*/
    public static RLnumber toRL(double number) {
        RLnumber result;
        int[] tail = new int[MAX_LENGTH];
        for (int i = 0; i < tail.length; i++) {
            tail[i] = EMPTY_VALUE;
        }

        int sign = (number >= 0) ? 0 : 1;
        if (number == 0) {
            result = new RLnumber(0, 0, new int[]{-18});
        } else {
            number = Math.abs(number);
            double number1 = number;
            int index = 0;
            for (int k = K_MAX; k > K_MIN; k--) {
                double backup = number1;
                number1 = number1 - pow(2, k);
                if (number1 < 0) {
                    number1 = backup;
                } else if (number1 > 0) {
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
        }
        return result;
    }

    /*works right*/
    public static RLnumber sort(RLnumber number1, RLnumber number2) {
        /*number1.cutRL();
        number2.cutRL();*/
        RLnumber result;

        int[] resultingTail = new int[number1.getLength() + number2.getLength()];
        Integer[] tempTail = new Integer[number1.getLength() + number2.getLength()];
        int[] tail1 = number1.getTail();
        int[] tail2 = number2.getTail();

        System.arraycopy(tail1, 0, resultingTail, 0, tail1.length);
        System.arraycopy(tail2, 0, resultingTail, number1.getLength(), number2.getLength());

        /* copying content of resultingTail to tempTail */
        for (int j = 0; j < tempTail.length; j++) {
            tempTail[j] = resultingTail[j];
        }

        Arrays.sort(tempTail, Collections.reverseOrder());

        for (int k = 0; k < tempTail.length; k++) {
            resultingTail[k] = (int) tempTail[k];
        }

        result = new RLnumber(number1.getSign(), resultingTail.length, resultingTail);
        return result;
    }

    /*works right */
    public void sort() {
        Integer[] tempTail = new Integer[tail.length];
        for (int i = 0; i < tail.length; i++) {
            tempTail[i] = tail[i];
        }

        Arrays.sort(tempTail, Collections.reverseOrder());

        for (int k = 0; k < tempTail.length; k++) {
            tail[k] = (int) tempTail[k];
        }
    }

    /* works right */
    public void mergeSimilar() {

        this.sort();

        for (int i = 0; i < length - 1; i++) {
            if (tail[i] == tail[i + 1]) {
                tail[i] = tail[i] + 1;
                for (int k = i + 1; k < tail.length - 1; k++) {
                    tail[k] = tail[k + 1];
                }
                tail[tail.length - 1] = EMPTY_VALUE;

            }
            if (tail[i] == EMPTY_VALUE) break;
        }
        this.countLength();
    }

    /*works right */
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

    public void deleteIdentical(RLnumber rl) {
        int[] tail1 = this.getTail();
        int[] tail2 = rl.getTail();
        for (int i = 0; i < this.getLength(); i++) {
            for (int k = 0; k < rl.getLength(); k++)
                if (tail1[i] == tail2[k]) {
                    tail1[i] = EMPTY_VALUE;
                    tail2[k] = EMPTY_VALUE;
                    break;
                }
        }
        this.setTail(tail1);
        rl.setTail(tail2);

        this.sort();
        this.countLength();
        this.mergeSimilar();
        this.countLength();
        this.cutRL();
        rl.sort();
        rl.countLength();
        rl.cutRL();

}
    /* works right */
    public static RLnumber substract(RLnumber number1, RLnumber number2) {

        System.out.println(number1 + " minus " + number2);

         if (number1.checkForZero()) {
             if (number2.getSign() == 0) {
                 number2.setSign(1);
             } else {
                 number2.setSign(0);
             }
             return number2;
         }
        if (number2.checkForZero()) {
            return number1;
        }
        RLnumber result = new RLnumber();
        if ((number1.getSign() == 1) && (number2.getSign() == 0)) {
            number1.setSign(0);
            result = sum(number1, number2);
            result.setSign(0);
            return result;
        }

        if ((number1.getSign() == 0) && (number2.getSign() == 1)) {
            number2.setSign(0);
            result = sum(number1, number2);
        }

        if ((number1.getLength() == 1) && (number2.getSign() == 0)) {
            number1.setSign(0);
            result = sum (number1,number2);
            return result;
        }
           number1.deleteIdentical(number2);

            if (number1.checkForZero()) {
                if (number2.getSign() == 0) {
                    number2.setSign(1);
                } else {
                    number2.setSign(0);
                }
                result = number2;
                return result;
            }
            if (number2.checkForZero()) {
                result = number1;
                return result;
            }

                //определяем, какое из двух чисел больше, раскладываем максимальное значение из мантисы
                int maxValue;
                int minValue;


                if (number1.getTail()[0] > number2.getTail()[0]) {
                   result.setSign(0);
                } else {
                    result = substract(number2,number1);
                }

                maxValue = number1.getTail()[0];
                minValue = number2.getTail()[number2.getTail().length-1];
                LinkedList<Integer> noIdeaHowToNameThisList = new LinkedList<>();
                for (int i = maxValue - 1; i >= minValue; i--) {
                    noIdeaHowToNameThisList.add(i);
                }
                int[] newBiggerTail = new int[noIdeaHowToNameThisList.size() + number1.length];
                for (int i = 0; i < noIdeaHowToNameThisList.size(); i++) {
                    newBiggerTail[i] = noIdeaHowToNameThisList.get(i);
                }
                System.arraycopy(number1.getTail(), 1, newBiggerTail, noIdeaHowToNameThisList.size(), number1.length - 1);

                number1.setTail(newBiggerTail);

                number1.deleteIdentical(number2);
                number1.mergeSimilar();
                number1.countLength();
                number1.cutRL();

                result = number1;
                return result;
            /* дописать до момента, когда в меньшем числе останется нулевое значение. Цикл из разложения и убирания подобных*/
    }

    /*works right*/
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
            }
        }


        partialProductArray = new int[partialProductList.size()];
        for (int i = 0; i < partialProductArray.length; i++) {
            partialProductArray[i] = partialProductList.get(i);
        }
        result = new RLnumber(signOfResult, partialProductArray.length, partialProductArray);
        result.sort();
        result.mergeSimilar();
        result.countLength();
        result.cutRL();

        return result;

    }

    //TODO add skipping EMPTY_VALUEs in all operations
    public static RLnumber divide(RLnumber dividend, RLnumber divisor) {
        RLnumber result;
        if (divisor.checkForZero()) {
            result = divisor;
        } else if (dividend.checkForZero()) {
            result = dividend;
        } else {
            RLnumber tempNumber = dividend;
            int divisorHeadBit = divisor.getTail()[0];
            int signOfResult = dividend.getSign() * divisor.getSign();
            int[] tailOfResult = new int[MAX_LENGTH];
            for (int j = 0; j < MAX_LENGTH; j++) {
                tailOfResult[j] = EMPTY_VALUE;
            }
            for (int i = 0; i < MAX_LENGTH; i++) {
                System.out.println("Loop #" + i);
                System.out.println("tempNumber is " + tempNumber);
                tailOfResult[i] = tempNumber.getTail()[0] - divisorHeadBit;

                RLnumber temp = new RLnumber(signOfResult, 1, new int[] {tailOfResult[i]}); //temporal part of fraction (the one that we are checking)
                RLnumber product = multiply(temp, divisor);
                System.out.println("Multiplied " + temp + " with " + divisor + ", and we have " + product);
                RLnumber remainder;
/*
                if (product.isGreaterThan(tempNumber)) {
                    System.out.println("product is greater than tempNumber");
                    tailOfResult[i] = tailOfResult[i] - 1;
                    product = multiply(temp, divisor);
                    System.out.println("Multiplying " + temp + " with " + divisor);
                }*/

                remainder = substract(tempNumber, product);
                tempNumber = remainder;
                if (remainder.checkForZero()) break;
            }
            result = new RLnumber(signOfResult, MAX_LENGTH, tailOfResult);

            result.cutRL();
        }
        return result;
    }


}

