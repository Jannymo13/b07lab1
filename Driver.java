import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();

        Polynomial p1 = new Polynomial(new double[] {1,2,-3}, new int[]{4,2,1});
        Polynomial p2 = new Polynomial(new double[] {-2,-9, 4},new int[] {0,3,2});

        System.out.println("p1:");
        p1.printPolynomial();
        System.out.println("\np2:");
        p2.printPolynomial();

        Polynomial s = p1.add(p2);
        System.out.println();
        s.printPolynomial();
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        System.out.println();

        System.out.println("p1 * p2");
        p1.multiply(p2).printPolynomial();

        File file = new File("test.txt");
        try {
            Polynomial f = new Polynomial(file);
            System.out.println();
            f.printPolynomial();
            try {
                f.saveToFile("output.txt");
            } catch (IOException e){
                System.out.println("Couldn't write file");
            }
        } catch (FileNotFoundException e){
            System.out.println("File Not Found or Empty File");
        }


    }
}
