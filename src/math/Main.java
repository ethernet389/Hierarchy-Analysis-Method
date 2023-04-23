package math;

import Jama.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);

    static PrintStream out = new PrintStream(System.out);

    static public String ArrayToString(double[] m){
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < m.length - 1; ++i) sb.append(m[i]).append(", ");
        sb.append(m[m.length - 1]).append("]");
        return sb.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        out.println("Входной файл: "); //README.md
        File data = new File(in.nextLine());
        Scanner input1 = new Scanner(data);

        Buffer buffer = CalculatingClass.completeTask(input1);

        //Каждой i-ой строке соответсвует i-ый критерий, каждому j-ому столбцу соответсвует j-ая альтернатива
        out.println("Relative Weights Of Each Candidate For Each Of Criteria: ");
        for (double[] list : buffer.relativeWeightsOfEachCandidateForEachOfCriteria){
            out.println(ArrayToString(list));
        }
        out.println();

        //Вычисление рейтинга
        out.println("Rating: ");
        out.println(ArrayToString(buffer.finalRatingEachOfCandidate));
        out.println();

        //Вычисление CI, RI, CR
        Scanner input2 = new Scanner(data);

        int countCrits = input2.nextInt();
        for (int i = 0; i < countCrits*countCrits; ++i) input2.nextDouble();

        int countAlts = input2.nextInt();
        for (int i = 0; i < countCrits; ++i){
            Matrix inputMatrix = MatrixFunctions.inputMatrix(input2, countAlts, countAlts);
            out.println("Alt matrix #" + (i + 1));
            out.printf("CI: %.16f\n", MatrixFunctions.getCI(inputMatrix));
            out.printf("RI: %.16f\n", MatrixFunctions.getRI(inputMatrix));
            out.printf("CR: %.16f\n", MatrixFunctions.getCR(inputMatrix));
            out.println();
        }
    }
}