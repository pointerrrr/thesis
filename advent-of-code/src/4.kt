import kotlin.math.max

fun main (args : Array<String>)
{
    val input = generateSequence (:: readLine)
    val lines = input.toList()

    val mins = lines.sumOf { minimumAmount(it) }
    println(mins)
}

fun minimumAmount(line : String) : Int
{
    val sanitized = line.replace(Regex("Game \\d+: "), "")
    val seqs = sanitized.split("; ")
    var minRed = 0
    var minBlue = 0
    var minGreen = 0
    seqs.forEach{elem ->
        val (x, y, z) = findMins(elem)
        minRed = max(x, minRed)
        minGreen = max(y, minGreen)
        minBlue = max(z, minBlue)
    }
    return minRed * minBlue * minGreen
}

fun findMins(hand : String ) : Triple<Int, Int, Int>
{
    val blocks = hand.split(", ")
    val dict = mutableMapOf("red" to 0, "blue" to 0, "green" to 0)

    blocks.forEach{elem ->
        val count = nrRegex.find(elem)
        val color = colorRegex.find(elem)
        if (count != null && color != null )
        {
            dict[color.value] = dict[color.value]!!.toInt() + count.value.toInt()
        }
    }
    return Triple(dict["red"]!!, dict["green"]!!, dict["blue"]!! )
}