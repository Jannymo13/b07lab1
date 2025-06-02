import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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

    public Polynomial(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);

        if (!s.hasNext()){
            this.coefficients = new double[]{};
            this.exponents = new int[]{};
            return;
        }

        String data = s.nextLine();
        String[] terms;

        data = data.replaceAll("-"," -");
        data = data.replaceAll("\\+"," +").strip();
        if (data.charAt(0) != '-' && data.charAt(0) != '+'){
            data = String.join("", "+", data);
        }

        terms = data.split(" ");

        this.coefficients = new double[terms.length];
        this.exponents = new int[terms.length];
        int index = 0;

        for (String term : terms) {
            if (term.isEmpty()) continue;

            // constant value
            if (!term.contains("x")) {
                this.coefficients[index] = Double.parseDouble(term);
                this.exponents[index++] = 0;
                continue;
            }

            String[] splitTerm = term.split("x"); //split term into coefficient and exponent

            //coefficient
            if (Objects.equals(splitTerm[0], "+")){
                this.coefficients[index] = 1;
            } else if (Objects.equals(splitTerm[0], "-")) {
                this.coefficients[index] = -1;
            } else {
                this.coefficients[index] = Double.parseDouble(splitTerm[0]);
            }

            //exponent
            if (splitTerm.length == 1){
                this.exponents[index] = 1;
            } else {
                this.exponents[index] = Integer.parseInt(splitTerm[1]);
            }

            index++;
        }
    }

    public Polynomial add(Polynomial poly) {
        Map<Integer,Double> coeff = new HashMap<>();
        int index = 0;

        // get coefficients and exponents and add them to the map
        for (index = 0; index < this.exponents.length; index++){
            coeff.merge(this.exponents[index], this.coefficients[index], Double::sum);
        }
        for (index = 0; index < poly.exponents.length; index++){
            coeff.merge(poly.exponents[index], poly.coefficients[index], Double::sum);
        }

        // turn the hash map into paired arrays of keys and values
        int[] newExponents = new int[coeff.size()];
        double[] newCoefficients = new double[coeff.size()];
        index = 0;

        for (int i : coeff.keySet()){
            newExponents[index++] = i;
        }

        for (index = 0; index < newExponents.length; index++){
            newCoefficients[index] = coeff.get(newExponents[index]);
        }

        return new Polynomial(newCoefficients, newExponents);
    }

    public double evaluate(double value) {
        double total = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            total += this.coefficients[i] * (Math.pow(value, this.exponents[i]));
        }
        return total;
    }

    public boolean hasRoot(double root) {

        return this.evaluate(root) == 0;
    }

    public void printPolynomial(){
        for (int i = 0; i < this.exponents.length; i++){
            System.out.println(this.coefficients[i] + "x^"+ this.exponents[i]);
        }
    }

    public Polynomial multiply(Polynomial poly){
        Polynomial result = new Polynomial();

        int currentExp;
        double currentCoef;

        for (int i = 0; i < this.exponents.length; i++){
            currentExp = this.exponents[i];
            currentCoef = this.coefficients[i];
            int[] newExp = poly.exponents.clone();
            double[] newCoef = poly.coefficients.clone();

            for (int j = 0; j < newExp.length; j++){
                newExp[j] += currentExp;
                newCoef[j] *= currentCoef;
            }

            Polynomial newPoly = new Polynomial(newCoef, newExp);

            result = result.add(newPoly);
        }
        return result;
    }

    public void saveToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        String result;

        if (this.coefficients.length == 0) {
            writer.write("0");
            writer.close();
        }

        if (this.coefficients[0] == (int) this.coefficients[0]) {
            result = Integer.toString((int) this.coefficients[0]);
        } else {
            result = Double.toString(this.coefficients[0]);
        }

        if (this.exponents[0] != 0){
            result = String.join("",result,"x");
        }
        if (this.exponents[0] != 1){
            result = String.join("", result,Integer.toString(this.exponents[0]));
        }

        for (int i = 1; i < this.exponents.length; i++){
            if (this.coefficients[i] > 0){
                result = String.join("",result,"+");
            }

            if (this.coefficients[i] == (int) this.coefficients[i]) {
                result = String.join("", result, Integer.toString((int) this.coefficients[i]));
            } else {
                result = String.join("", result, Double.toString(this.coefficients[i]));
            }


            if (this.exponents[i] != 0){
                result = String.join("",result,"x");
                if (this.exponents[i] != 1){
                    result = String.join("", result,Integer.toString(this.exponents[i]));
                }
            }

        }

        writer.write(result);
        writer.close();


    }
}




