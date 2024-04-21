import kotlin.reflect.KProperty

fun main()
{
    globalValue()
}

fun obvious()
{
    val x = 1
    val y = x // direct aliasing
}

fun property()
{
    val withProperty = withProperty()
    val y = withProperty.x // aliasing by property field
}

class withProperty
{
    val x = 5
}

var fileWide = 3

fun wholeFile()
{
    val y = fileWide // aliasing to a global variable, we have no clue where this can point to
}

fun fromFunction()
{
    val x = 3
    val y = selfReturn(x) // aliasing through function call
}

fun selfReturn(x : Int) : Int
{
    return x
}

fun throughHiddenFunctionCall()
{
    val y = fileWide
    val z = returnFileWide() // y and z are now aliases
}

fun returnFileWide() : Int
{
    return fileWide
}

fun bothNull()
{
    val x = null
    val y = null
}

fun fromArray()
{
    val arr = listOf(1,2,3)
    val x = arr[0]
    var y : Int
    for (i in 1..10)
    {
        if (i == 0)
            y = arr[i]
    }
}

fun aliasingWithinList()
{
    val obj1 = withProperty()
    val obj2 = withProperty()
    val obj3 = withProperty()
    val asdf = listOf(obj1, obj2, obj3, obj1)
    val x = asdf[0]
    val y = asdf[3] // these two share a reference, because they are aliased within list asdf
}

fun lambdaCapture() {
    var counter = 0  // Mutable state
    val incrementCounter = { counter++ }  // Lambda that captures and modifies the mutable state

    incrementCounter()  // Modifies the counter variable

    println(counter)  // Outputs: 1
}

class MyClass {
    companion object {
        var sharedValue = 0
    }

    fun incrementSharedValue() {
        sharedValue++
    }

    fun getSharedValue() : Int
    {
        return sharedValue
    }
}

fun globalValue()
{
    val instance1 = MyClass()
    val instance2 = MyClass()

    instance1.incrementSharedValue()
    val x = instance1.getSharedValue()
    val y = instance2.getSharedValue()
}

fun hiddenAliasing()
{
    val x = 0
    hiddenSetter1(x)
    hiddenSetter2(x)
}


fun hiddenSetter1(x : Int)
{
    hiddenVar = x
}

fun hiddenSetter2(x : Int)
{
    hiddenVar = x
}

var hiddenVar = 0
var hiddenVar2 = 0


fun hiddenAliasing2()
{
    anotherHiddenSetter1()
    anotherHiddenSetter2()
    val x = hiddenVar == hiddenVar2
}

fun anotherHiddenSetter1()
{
    hiddenVar = hiddenVar2
}

fun anotherHiddenSetter2()
{
    hiddenVar = hiddenVar2
}