package math

import java.io.File
import java.util.*

val input = Scanner(System.`in`)

fun main() {
    Locale.setDefault(Locale.FRANCE)
    println("Входной файл")
    val data = File(input.nextLine())
    val input1 = Scanner(data)

    val buffer = input1.nextMAI()
    println(buffer)

    val input2 = Scanner(data)

    val numPer = input2.nextInt()
    for (i in 0 until numPer*numPer) input2.nextDouble()

    val countAlts = input2.nextInt()
    for (i in 0 until  numPer){
        val inputMatrix = input2.nextMatrix(countAlts, countAlts)
        val str = """
            | Alt matrix #${i+1}
            | CI: ${SquareMatrix.CI(inputMatrix)}
            | RI: ${SquareMatrix.RI(inputMatrix)}
            | CR: ${SquareMatrix.CR(inputMatrix)}
        """.trimIndent()
        println("$str \n")
    }
}