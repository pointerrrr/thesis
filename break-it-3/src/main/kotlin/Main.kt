fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

val exampleList = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

fun example1() : List<Int>
{
    // map creates a new list with an intermediary result. This result is then passed to filter, which again creates a new list.
    // The intermediary result from map is never referenced again, so it should be fine to overwrite it with the result of filter.
    // This saves memory, especially if more than one higher order function is chained. The next
    val result = exampleList.map{it + 1}.filter{ it % 2 == 0 }
    return result
}

fun example2() : List<Int>
{
    // Every iteration new memory is allocated for the result of map that is then passed into the next iteration of map.
    // The intermediary result is the never used again, thus we could overwrite it in every iteration, instead of allocating new memory.
    var result = exampleList
    for (i in 1..10)
    {
        result = result.map{it + 1}
    }
    return result
}

fun example3() : List<Int>
{
    // list1 and list2 are created and concatenated. The original lists are never used again. The memory could be reused to store result.
    // something about constant evaluation
    val list1 = listOf(0, 1, 2, 3)
    val list2 = listOf(4, 5, 6, 7)
    val result = list1 + list2
    return result
}

fun example4() : List<Int>
{
    // Here temp is used twice, but the second time it is used, is also the last. Most usage analyses only check for
    // used (at most) once, or used more than once, and only apply optimizations when it is used (at most) once.
    // This instance however, temp is used more than once, but the last update could still be done in place
    // without breaking anything.
    // Perhaps a form of usage analysis that gives how often a value could be used after this expression, instead of
    // just saying if a value is used only once or more than once, or saying if this is the last usage or not.
    val temp = exampleList.map { it + 1 }
    val max = temp.max()
    val result = temp.map{ it + max}
    return result
}

fun example5() : List<Int>
{
    // At the initialization of result, someList is cast to an immutable list. This creates a copy in memory, but if
    // we can be sure that someList will not be referenced again, we can reuse the existing list and just change its type.
    val someList = mutableListOf(0, 1, 2, 3, 4, 5)
    someList[0] = 1
    val result = someList.toList()
    return result
}

fun example6() : List<Int>
{
    // asReversed can do destructive updates, as someList is not used again
    val someList = exampleList.map {it + 1}
    val result = someList.asReversed()
    return result
}

fun example7() : List<Int>
{
    // In the case of intersect, a set is returned instead of a list, but list1 and list2 are no longer used.
    // We know the set contains at most as many elements as the smallest list, so the old memory of the list should
    // fit the entire set. Perhaps this is generalizable to memory use. So if we know certain memory will not be used again,
    // we can immediately reuse it, to store the new value,
    // as long as we know the resulting memory usage will be less than or equal to the previous.
    val list1 = listOf(0, 1, 2, 3)
    val list2 = listOf(2, 3, 4, 5)
    val result = list1.intersect(list2).toList()
    return result
}

fun noExample1() : List<Int> {
    // Here temp is captured by the closure of someFunc, meaning that in-place updates cannot be done on temp, even if
    // it is the last usage.
    val temp = exampleList.map { it }
    val someFunc = {a : List<Int> -> {a}}
    val intermediary = someFunc(temp)

    val result = temp.map { it + 1 }
    val intermediary2 = intermediary()
    return result
}