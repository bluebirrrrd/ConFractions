import java.util.Scanner;

public class Application {
    private static final int CONVERGENCE = 8;

    /*
    * Sorry for horrible variables like r, s, t. I have almost no idea what they mean, 'cause this is
    * a Java adaptation of this code:
    * https://www.daniweb.com/software-development/c/code/216328/continued-fraction-expansion
    * If you know what they are, add a comment. Thanks.
    */

    public static RLnumber countR(double value) {
        double antiValue = - value;
        RLnumber rlValue = RLnumber.toRL(value);
        RLnumber rlAntiValue = RLnumber.toRL(antiValue);
        RLnumber r = RLnumber.multiply(rlValue, rlAntiValue);

        return r;
    }

    public static RLnumber countS(RLnumber r) {
        RLnumber s = RLnumber.sum(RLnumber.multiply(RLnumber.toRL(4), r), RLnumber.toRL(2));

        for (int i = CONVERGENCE; i > 0; i--) {
            s = RLnumber.sum(
                    RLnumber.substract(RLnumber.multiply(RLnumber.toRL(i), RLnumber.toRL(4)), RLnumber.toRL(2)),
                    RLnumber.divide(r, s)
            );
        }
        return s;
    }

    public static RLnumber countDivisor1(RLnumber r, RLnumber s) {
        return RLnumber.substract(RLnumber.multiply(s, s), r);
    }

    public static RLnumber countDivisor2(RLnumber r, RLnumber s) {
        return RLnumber.sum(RLnumber.multiply(s, s), r);
    }

    public static RLnumber sin(double value) {
        RLnumber rlValue = RLnumber.toRL(value);
        RLnumber r, s, result;
        r = countR(value);
        s = countS(r);
        result = RLnumber.divide(
                RLnumber.multiply(RLnumber.multiply(rlValue, s), RLnumber.toRL(2)),
                countDivisor1(r,s)
                );

        return result;
    }

    public static double sinn(double value) {
        double r = -value * value;
        double s = 4 * CONVERGENCE + 2;
        for (int k = CONVERGENCE; k > 0; k--) {
            s = 4 * k - 2 + r/s;
        }
        double t = 2 * value * s/(s*s - r);
        return t;
    }

    public static RLnumber cos(double value) {
        RLnumber r, s, result;
        r = countR(value);
        s = countS(r);
        result = RLnumber.divide(
                RLnumber.sum(RLnumber.multiply(s, s), r),
                countDivisor1(r,s)
        );

        return result;
    }

    public static double coss(double value) {
        double r = -value * value;
        double s = 4 * CONVERGENCE + 2;
        for (int k = CONVERGENCE; k > 0; k--) {
            s = 4 * k - 2 + r/s;
        }
        double t = (s * s + r)/(s*s - r);
        return t;

    }
    public static RLnumber tan(double value) {
        RLnumber rlValue = RLnumber.toRL(value);
        RLnumber r, s, result;
        r = countR(value);
        s = countS(r);
        result = RLnumber.divide(
                RLnumber.multiply(RLnumber.multiply(rlValue, s), RLnumber.toRL(2)),
                countDivisor2(r, s)
        );

        return result;
    }

    public static double tann(double value) {
        double r = -value * value;
        double s = 4 * CONVERGENCE + 2;
        for (int k = CONVERGENCE; k > 0; k--) {
            s = 4 * k - 2 + r/s;
        }
        double t = 2 * value * s/(s*s + r);
        return t;
    }
    public static RLnumber sec(double value) {
        return RLnumber.divide(RLnumber.toRL(1), cos(value));
    }

    public static double secc(double value) {
        return (1/coss(value));
    }

    public static void main(String[] args) {
        java.util.Scanner scan = new Scanner(System.in);
        double value;

       System.out.print("Enter the value: ");
        value = scan.nextDouble();

       // System.out.println(RLnumber.toRL(value).toString());
      //  System.out.println(RLnumber.substract(RLnumber.toRL(45), RLnumber.toRL(7)));

        /*System.out.println(RLnumber.substract(RLnumber.toRL(0), RLnumber.toRL(45)));
        System.out.println(RLnumber.substract(RLnumber.toRL(45),RLnumber.toRL(0)));*/

       // System.out.println(RLnumber.substract(RLnumber.toRL(45), RLnumber.toRL(5)).toString());

    /*   System.out.println("sin(" + value + ") = " + sin(value).toString() + " = " + sin(value).toDouble());
        System.out.println("cos(" + value + ") = " + cos(value).toString());
        System.out.println("tan(" + value + ") = " + tan(value).toString());
        System.out.println("sec(" + value + ") = " + sec(value).toString());
*/

        System.out.println("sin(" + value + ") = " + RLnumber.toRL(sinn(value)) + " = " + sinn(value));
        System.out.println("cos(" + value + ") = " + RLnumber.toRL(coss(value)) + " = " + coss(value));
        System.out.println("tan(" + value + ") = " + RLnumber.toRL(tann(value)) + " = " + tann(value));
        System.out.println("sec(" + value + ") = " + RLnumber.toRL(secc(value)) + " = " + secc(value));
    }
}
