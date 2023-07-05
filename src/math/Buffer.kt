package math

data class Buffer(
    val relativeWeights: ArrayList<DoubleArray>,
    val finalRelativeWeights: DoubleArray
) {

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

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Relative Weights Of Each Candidate For Each Of Criteria: \n")
        relativeWeights.forEach { stringBuilder.append("${it.contentToString()}\n") }
        stringBuilder.append("Final Rating:\n${finalRelativeWeights.contentToString()}")
        return stringBuilder.toString()
    }
}
