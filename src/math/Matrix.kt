package math

import java.util.*
import Jama.Matrix as JMatrix

class Matrix(array: Array<DoubleArray>): JMatrix(array.copyOf()){
    constructor(matrix: JMatrix): this(matrix.array.copyOf())
    constructor(matrix: Matrix): this(matrix.array.copyOf())

    val sumOfColumns: DoubleArray
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

    val sumOfRows: DoubleArray
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

    val relativeWeights: DoubleArray
        get() {
            val ret = sumOfRows
            for (i in ret.indices) ret[i] /= columnDimension.toDouble()
            return ret
        }

    fun normalize(): Matrix {
        val columnSum = sumOfColumns

        for (i in 0 until rowDimension) {
            for (j in 0 until columnDimension) {
                val newValue: Double = this[j, i] / columnSum[i]
                this[j, i] = newValue
            }
        }
        return this
    }

    fun normalized(): Matrix = copy().normalize()

    override fun toString(): String {
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

    override fun copy(): Matrix = Matrix(super.copy())

    override fun equals(other: Any?): Boolean = when(other) {
        is Matrix -> array.equals(other.array)
        is JMatrix -> array.equals(other.array)
        else -> false
    }

    override fun hashCode(): Int = super.hashCode()

    companion object Square{
        fun input(input: Scanner, rows: Int, columns: Int): Matrix {
            val m = Array(rows) { DoubleArray(columns) }
            for (i in 0 until rows) {
                for (j in 0 until columns) {
                    m[i][j] = input.nextDouble()
                }
            }
            return Matrix(m)
        }

        object Coefficients {
            @JvmStatic
            fun CI(matrix: Matrix): Double {
                val normalizedMatrixWeights =
                        matrix.normalized().relativeWeights
                val weights = JMatrix(normalizedMatrixWeights.size, 1)
                for (i in 0 until weights.rowDimension) weights[i, 0] = normalizedMatrixWeights[i]
                val resultMatrix = Matrix(matrix * weights)
                return (resultMatrix.sumOfColumns.first() - matrix.columnDimension) / (matrix.columnDimension - 1)
            }

            @JvmStatic
            fun RI(matrix: Matrix): Double = (1.98 * (matrix.columnDimension - 2)) / matrix.columnDimension

            @JvmStatic
            fun CR(matrix: Matrix): Double = CI(matrix) / RI(matrix)
        }

        @JvmStatic
        fun MAI(data: Scanner): Buffer {
            val buffer = Buffer()

            val numPar = data.nextInt()
            val relPar = input(data, numPar, numPar).normalize()
            val numAlt = data.nextInt()

            val listOfRelAlts = ArrayList<Matrix>(numAlt)
            for (i in 0 until numPar) {
                val matrix = input(data, numAlt, numAlt).normalize()
                listOfRelAlts.add(matrix)
            }

            val listOfWeights = ArrayList<DoubleArray>()
            for (e in listOfRelAlts) listOfWeights.add(e.relativeWeights)

            val perWeights = relPar.relativeWeights
            for (i in perWeights.indices) {
                for (j in 0 until numAlt) listOfWeights[i][j] *= perWeights[i]
            }
            buffer.relativeWeights = listOfWeights

            val result = DoubleArray(numAlt)
            for (i in perWeights.indices) {
                for (j in 0 until numAlt) result[j] += listOfWeights[i][j]
            }
            buffer.finalRelativeWeights = result

            return buffer
        }
    }
}