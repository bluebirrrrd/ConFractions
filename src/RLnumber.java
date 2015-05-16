
public class RLnumber {

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


}
