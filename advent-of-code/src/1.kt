fun main(args: Array<String>) {
    val input = generateSequence (:: readLine)
    val lines = input.toList()

    val digits = lines.map { it.filter {it.isDigit()} }
    val nrs = digits.map {it.first().digitToInt() * 10 + it.last().digitToInt()}

    val result = nrs.sum()

    println(result)
}