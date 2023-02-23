import Jama.Matrix;

public final class MatrixFunctions {
    static public double[] getSumOfColumns(Matrix matrix){
        double[] columnSum = new double[matrix.getColumnDimension()];

        for (int j = 0; j != matrix.getColumnDimension(); ++j){
            double sum = 0;
            for (int i = 0; i != matrix.getRowDimension(); ++i){
                sum += matrix.get(i, j);
            }
            columnSum[j] = sum;
        }

        return columnSum;
    }

    static public double[] getRelativeWeights(Matrix matrix){
        double[] ret = getSumOfRows(matrix);
        for (int i = 0; i < ret.length; ++i) ret[i] /= matrix.getColumnDimension();
        return ret;
    }

    static public double[] getSumOfRows(Matrix matrix){
        double[] linesSum = new double[matrix.getRowDimension()];

        for (int j = 0; j != matrix.getRowDimension(); ++j){
            double sum = 0;
            for (int i = 0; i != matrix.getColumnDimension(); ++i){
                sum += matrix.get(j , i);
            }
            linesSum[j] = sum;
        }

        return linesSum;
    }

    static public Matrix normalize(Matrix matrix){
        double[] columnSum = getSumOfColumns(matrix);

        for (int i = 0; i != matrix.getRowDimension(); ++i){
            for (int j = 0; j != matrix.getColumnDimension(); ++j){
                double newValue = matrix.get(j, i) / columnSum[i];
                matrix.set(j, i, newValue);
            }
        }
        return matrix;
    }

    static public Matrix getNormalized(Matrix matrix){
        return MatrixFunctions.normalize(matrix.copy());
    }

    static public String toString(Matrix matrix) {
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < matrix.getRowDimension(); ++i){
            buffer.append("[");
            int j = 0;
            for (; j < matrix.getColumnDimension() - 1; ++j) {
                buffer.append(matrix.get(i, j)).append(", ");
            }
            buffer.append(matrix.get(i, j)).append("]\n");
        }

        return buffer.toString();
    }
}
