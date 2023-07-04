package math

data class Buffer(
    var relativeWeights: ArrayList<DoubleArray>,
    var finalRelativeWeights: DoubleArray
) {

    constructor(): this(ArrayList<DoubleArray>(), doubleArrayOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Buffer

        if (relativeWeights != other.relativeWeights) return false
        return finalRelativeWeights.contentEquals(other.finalRelativeWeights)
    }

    override fun hashCode(): Int {
        var result = relativeWeights.hashCode()
        result = 31 * result + finalRelativeWeights.contentHashCode()
        return result
    }
}
