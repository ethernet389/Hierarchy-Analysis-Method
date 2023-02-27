import Jama.Matrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class Main {
    static Scanner in = new Scanner(System.in);

    static PrintStream out = new PrintStream(System.out);

    static public Matrix inputMatrix(Scanner in, int rows, int columns){
        double[][] m = new double[rows][columns];
        for (int i = 0; i != rows; ++i){
            for (int j = 0; j != columns; ++j){
                m[i][j] = in.nextDouble();
            }
        }
        return new Matrix(m);
    }

    static public String ArrayToString(double[] m){
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < m.length - 1; ++i) sb.append(m[i]).append(", ");
        sb.append(m[m.length - 1]).append("]");
        return sb.toString();
    }

    //Решение задачи для одного человека (один уровень критериев)
    static public double[] completeTask(Scanner data){
        //Кол-во личных критериев
        int numPar = data.nextInt();

        //Матрица отношений личных критериев
        Matrix relPar = inputMatrix(data, numPar, numPar);
        MatrixFunctions.normalize(relPar);

        //Кол-во альтернатив
        int numAlt = data.nextInt();

        //Относительная оценка альтернатив по каждому из критериев
        ArrayList<Matrix> listOfRelAlts = new ArrayList<>(numAlt);
        for (int i = 0; i < numPar; ++i){
            Matrix matrix = inputMatrix(data, numAlt, numAlt);
            MatrixFunctions.normalize(matrix);
            listOfRelAlts.add(matrix);
        }
        //Относительные веса между альтернативами по каждому критерию
        ArrayList<double[]> listOfWeights = new ArrayList<>();
        for (Matrix listOfRelAlt : listOfRelAlts) {
            listOfWeights.add(
                    MatrixFunctions.getRelativeWeights(listOfRelAlt)
            );
        }

        //Обход дерева иерархий
        double[] result = new double[numAlt];
        double[] perWeights = MatrixFunctions.getRelativeWeights(relPar);
        for (int i = 0; i < perWeights.length; ++i) {
            for (int j = 0; j < numAlt; ++j) {
                result[j] += perWeights[i] * listOfWeights.get(i)[j];
            }
        }

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        out.println("Входной файл: "); //README.md
        File data = new File(in.nextLine());
        Scanner input0 = new Scanner(data);

        //Вычисление рейтинга
        String result = ArrayToString(completeTask(input0));
        out.println("Rating: ");
        out.println(result);
        out.println();

        //Вычисление CI, RI, CR
        Scanner input1 = new Scanner(data);

        int countCrits = input1.nextInt();
        for (int i = 0; i < countCrits*countCrits; ++i) input1.nextDouble();

        int countAlts = input1.nextInt();
        for (int i = 0; i < countCrits; ++i){
            Matrix inputMatrix = inputMatrix(input1, countAlts, countAlts);
            out.println("Alt matrix #" + (i + 1));
            out.printf("CI: %.16f\n", MatrixFunctions.getCI(inputMatrix));
            out.printf("RI: %.16f\n", MatrixFunctions.getRI(inputMatrix));
            out.printf("CR: %.16f\n", MatrixFunctions.getCR(inputMatrix));
            out.println();
        }
    }
}