val numberNames = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun main(args : Array<String>)
{
    example()
    val input = generateSequence (:: readLine)

    val lines = input.toList()
    val digits = findDigits(lines)

    val values = digits.map { it.first().digitToInt() * 10 + it.last().digitToInt() }
    println(values.sum())

}

fun findDigits(lines : List<String>) : List<String>
{
    return lines.map{findDigitsHelper2(it)}
}

fun findDigitsHelper2(line : String) : String
{
    var tempString = line
    numberNames.forEachIndexed { index, elem ->
        tempString = tempString.replace(elem, elem.first() + (index + 1).toString() + elem.last())
    }
    return tempString.filter{it.isDigit()}
}


fun example()
{
    val i = 0
    val j = i + 1
    val k = readlnOrNull()
    println(j)
}
