import java.util.Dictionary;
import java.util.Hashtable;

public class Polynomial {
    double[] coefficients;
    int[] exponents; // an array containing the non-zero exponents in the polynomial (must be sorted)

    public Polynomial() {

        this.coefficients = new double[]{};
        this.exponents = new int[]{};
    }

    public Polynomial(double[] coefficients, int[] exponents) {

        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    private void addToDict(Dictionary<Integer,Double> dict, int i, double d){
        if (dict.get(i) == null){
            dict.put(i,d);
        } else {
            dict.put(i,dict.get(i) + d);
        }
    }

    public Polynomial add(Polynomial poly2) {
        Dictionary<Integer,Double> coeff = new Hashtable<>();


        int i1 = 0;
        int i2 = 0;

        int len1 = this.exponents.length;
        int len2 = poly2.exponents.length;

        while (i1 < len1 && i2 < len2){
            if (this.exponents[i1] < poly2.exponents[i2]){
                this.addToDict(coeff, i1, this.exponents[i1]);
                i1++;
            } else {
                this.addToDict(coeff, i1, this.exponents[i1]);
                i2++;
            }
        }
        while (i1 < len1){
            System.out.println("i1!");
            i1++;
        }
        while (i2 < len2){
            System.out.println("i2!");
            i2++;
        }
        return null;
    }

    public double evaluate(double value) {
        double total = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            total += this.coefficients[i] * (Math.pow(value, i));
        }
        return total;
    }

    public boolean hasRoot(double root) {

        return this.evaluate(root) == 0;
    }
}


