const val redCubes = 12
const val greenCubes = 13
const val blueCubes = 14
val nrRegex = "\\d+".toRegex()
val colorRegex = "[A-Za-z]+".toRegex()

fun main (args : Array<String>)
{
    val input = generateSequence (:: readLine)
    val lines = input.toList()

    val possibleGames = lines.filter {isPossible(it)}
    val nrs = possibleGames.sumOf { nrRegex.find(it)!!.value.toInt() }
    println(nrs)
}

fun isPossible(line : String) : Boolean
{
    val sanitized = line.replace(Regex("Game \\d+: "), "")
    val seqs = sanitized.split("; ")
    seqs.forEach{elem ->
        if (!isPossibleHand(elem))
            return false
    }
    return true
}

fun isPossibleHand( hand : String) : Boolean
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
    return dict["red"]!! <= redCubes && dict["blue"]!! <= blueCubes && dict["green"]!! <= greenCubes
}