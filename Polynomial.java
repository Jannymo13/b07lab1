public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial poly2) {
        Polynomial newPoly = new Polynomial(poly2.coefficients);

        for (int i = 0; i < this.coefficients.length; i++) {
            newPoly.coefficients[i] += this.coefficients[i];
        }

        return newPoly;
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


