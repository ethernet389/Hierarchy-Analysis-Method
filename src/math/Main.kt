package math

import java.io.File
import java.io.FileNotFoundException
import java.util.*

val input = Scanner(System.`in`)

@Throws(FileNotFoundException::class)
fun main() {
    Locale.setDefault(Locale.FRANCE)
    println("Входной файл")
    val data = File(input.nextLine())
    val input1 = Scanner(data)

    val buffer = Matrix.MAI(input1)
    println("Relative Weights Of Each Candidate For Each Of Criteria: ")
    for (list in buffer.relativeWeights) println(list.contentToString())
    println()

    println("Rating:\n ${buffer.finalRelativeWeights.contentToString()}\n")


    val input2 = Scanner(data)

    val countCrits = input2.nextInt()
    for (i in 0 until countCrits*countCrits) input2.nextDouble()

    val countAlts = input2.nextInt()
    for (i in 0 until  countCrits){
        val inputMatrix = Matrix.input(input2, countAlts, countAlts)
        val str = """
            | Alt matrix #${i+1}
            | CI: ${Matrix.Square.Coefficients.CI(inputMatrix)}
            | RI: ${Matrix.Square.Coefficients.RI(inputMatrix)}
            | CR: ${Matrix.Square.Coefficients.CR(inputMatrix)}
        """.trimIndent()
        println("$str \n")
    }
}