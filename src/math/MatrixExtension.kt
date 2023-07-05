package math

import java.util.*
import Jama.Matrix as Matrix

val Matrix.sumOfColumns: DoubleArray
    get() {
        val columnSum = DoubleArray(columnDimension)
        for (j in 0 until columnDimension) {
            var sum = 0.0
            for (i in 0 until rowDimension) {
                sum += this[i, j]
            }
            columnSum[j] = sum
        }
        return columnSum
    }

val Matrix.sumOfRows: DoubleArray
    get() {
        val linesSum = DoubleArray(rowDimension)
        for (j in 0 until rowDimension) {
            var sum = 0.0
            for (i in 0 until columnDimension) {
                sum += this[j, i]
            }
            linesSum[j] = sum
        }
        return linesSum
    }

val Matrix.relativeWeights: DoubleArray
    get() {
        val ret = sumOfRows
        for (i in ret.indices) ret[i] /= columnDimension.toDouble()
        return ret
    }

fun Matrix.normalize(): Matrix {
    val columnSum = sumOfColumns

    for (i in 0 until rowDimension) {
        for (j in 0 until columnDimension) {
            val newValue: Double = this[j, i] / columnSum[i]
            this[j, i] = newValue
        }
    }
    return this
}

fun Matrix.normalized(): Matrix = copy().normalize()

fun Matrix.contentToString(): String {
    val buffer = StringBuilder()
    for (i in 0 until rowDimension) {
        buffer.append("[")
        var j = 0
        while (j < columnDimension - 1) {
            buffer.append(this[i, j]).append(", ")
            ++j
        }
        buffer.append(this[i, j]).append("]\n")
    }
    return buffer.toString()
}

object SquareMatrix {
    fun CI(matrix: Matrix): Double {
        val normalizedMatrixWeights =
                matrix.normalized().relativeWeights
        val weights = Matrix(normalizedMatrixWeights.size, 1)
        for (i in 0 until weights.rowDimension) weights[i, 0] = normalizedMatrixWeights[i]
        val resultMatrix = matrix * weights
        return (resultMatrix.sumOfColumns.first() - matrix.columnDimension) / (matrix.columnDimension - 1)
    }

    fun RI(matrix: Matrix): Double = (1.98 * (matrix.columnDimension - 2)) / matrix.columnDimension

    fun CR(matrix: Matrix): Double = CI(matrix) / RI(matrix)
}

fun Scanner.nextMAI(): Buffer {
    val numPar = this.nextInt()
    val relPar = this.nextMatrix(numPar, numPar).normalize()
    val numAlt = this.nextInt()

    val listOfRelAlts = ArrayList<Matrix>(numAlt)
    for (i in 0 until numPar) {
        val matrix = this.nextMatrix(numAlt, numAlt).normalize()
        listOfRelAlts.add(matrix)
    }

    val listOfWeights = ArrayList<DoubleArray>()
    for (e in listOfRelAlts) listOfWeights.add(e.relativeWeights)

    val perWeights = relPar.relativeWeights
    for (i in perWeights.indices) {
        for (j in 0 until numAlt) listOfWeights[i][j] *= perWeights[i]
    }

    val result = DoubleArray(numAlt)
    for (i in perWeights.indices) {
        for (j in 0 until numAlt) result[j] += listOfWeights[i][j]
    }

    return Buffer(relativeWeights = listOfWeights, finalRelativeWeights = result)
}

fun Scanner.nextMatrix(rows: Int, columns: Int): Matrix {
    val m = Array(rows) { DoubleArray(columns) }
    for (i in 0 until rows) {
        for (j in 0 until columns) {
            m[i][j] = this.nextDouble()
        }
    }
    return Matrix(m)
}