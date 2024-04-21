fun main()
{

}

fun simple1()
{
    val x : Int // add x to analysis domain
    x = 5 // assignment of x does not change value of x in analysis domain
    val y = x + 5 // x appears on RHS of assignment, usage goes from no uses to [0-1] uses
    val z = x + 10 // x appears on RHS of assignment, usage goes from [0-1] uses to [1-∞]
    val q = x + 15 // x appears on RHS of assignment, usage stays at [1-∞]
}

fun funcCall1()
{
    val x : Int = 0 // add x to analysis domain
    someFunc(x) // x is used as function argument, usage goes from no uses to [0-1] uses
    someFunc(x) // x is used as function argument, usage goes from [0-1] uses to [1-∞] uses
}

fun someFunc(a : Int)
{

}

fun funcCall2()
{
    val x = X()
    var y = x
    x.someFunc(1) // a function property of x is called, usage goes from no uses to [0-1] uses
    x.someFunc(2) // a function property of x is called, usage goes form [0-1] uses to [1-∞] uses
}

class X
{
    private var a = 0
    fun someFunc(x : Int)
    {
        a += x
    }
}

fun asOp()
{
    val x = B()
    val y = x as A // usage of x goes up
}

open class A {}

class B : A() {}

fun ifCond()
{
    val x = 1
    if (x > 10) // usage of x goes up
    {

    }
    else
    {

    }
}

fun ifOp()
{
    val x = 10
    if (10 < 9)
    {
        println(x) // usage x goes to 1
    }
    else
    {
        println(x + 1) // usage x goes to 1
    }
    // join usages uf x. since both are 1, we should get 1
}


fun ifOp2()
{
    val x = 10
    if( 10 < 9)
    {
        println(x)
    }
    else
    {
        println(0)
    }
    // join usages of x. in the first branch x is used once, in the second branch x is not used.
    // LUB is x is used once, so x is used once from here on out


}

fun param1(x : Int)
{
    val y = x + 1 // usage of x goes from unknown to unknown
}

fun localClass()
{
    class A()
    {

    }
    val x = A()
}

fun withBinding()
{
    5
    val a : Int

    val x = {a : Int -> a + 1}
}

fun exampleFieldCount()
{
    val a = HelperType()
    val y = something(a) // usage of a is on one
    val z = a.x + 3 // a.x has usage one, but a's usage does not increase
}

fun something(a : HelperType) : Int
{
    return a.y
}

class HelperType()
{
    val x = 5
    var y = 3
}

fun functionCallsAreNotOnce()
{
    val x = HelperType()
    val y = functionCallHelper(x) // because functionCallHelper assigns x to functionCallHelperVar, it would not be safe
                                  // to say that x has only been used once. Because of this it is not generally possible
                                  // to determine how often a variable is used when used as function argument.
                                  // Analysis on the called function would be needed to determine how often the
                                  // arguments are used. This can be done either at compile time for general functions,
                                  // or ahead of time for specific functions, such as map, filter, etc.
    return y
}

var functionCallHelperVar : HelperType = HelperType()

fun functionCallHelper(x : HelperType)
{
    functionCallHelperVar = x
}

fun loops()
{
    var x = 1
    for (i in 1..10)
    {
        x = x + i // X has one use every iteration of this loop, and is not used outside of it. In this case
    }
}

fun loops2()
{
    var x = 1
    var y = 0
    for (i in 1..10)
    {
        y = y + x // in this
    }
}



fun plusEqual()
{
    var y = 1
    y += 1
}

fun whenExample()
{
    val x = 3
    when(x)
    {
        1 -> return
        2 -> return
    }
}

fun tryCatchExample()
{
    val x = 0
    try {
        val y = 3 / x
    }
    catch (e : ArithmeticException)
    {

    }
}

fun throwExample()
{
    throw ArithmeticException()
}

fun binaryOpExample()
{
    val a = false
    val b = true
    if (a || b)
    {
        return
    }
}

fun checkNullCall()
{
    var x : Int?
    x = null
    x?.dec()
}

fun elvisOp()
{
    val x = if (2 != 1) 3 else 2
}

fun whileExample()
{
    while(true)
    {

    }
}

// what happens if you have a function call, and they pass you the function argument twice
// I need to consider what it means for something to be used. if you use x.Name, is x used, or just x.Name as a whole
// I need to figure out the formal semantics of usage.
// Cases like passing something to function count as uses
// Kotlin compiler code might help. The file is called CFGNode it is found here within the repo: compiler/fir/semantics/src/org/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode.kt
// compiler is found here https://github.com/JetBrains/kotlin

// Note: field usage was determined that a usage of a.b counts as both a use for a and b, because if the pointer for a changes,
// so does the pointer for b, because b is a relative pointer. How does this work out of b is an absolute pointer?