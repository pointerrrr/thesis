import java.io.File

fun main(args : Array<String>)
{
    args.forEach{println(it)}
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
    x.someFunc(1) // a function property of x is called, usage goes from no uses to [0-1] uses
    x.someFunc(2) // a function property of x is called, usage goes form [0-1] uses to [1-∞] uses
}

class X
{
    private var a = 0
    private val b = {a + 2}

    constructor()
    {
        {
            a += 1
        }
    }

    fun X()
    {

    }

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

fun localFun()
{
    fun f (arg : Int) : Int
    {
        return arg + 1
    }
    val x = 2
    val y = f(x)
}

fun localFun2()
{
    val q = 2
    fun f (arg : Int) : Int
    {
        return arg + q
    }
    val x = 2
    val y = f(x)
}

fun noDefaultArgs(x : Int)
{
    val y = x + 3
}

fun defaultArgs(p : Int = 3, x : Int = 5 + p)
{
    val y = x + p
}

fun withLambda()
{
    val x = listOf(1,2,3)
    val y = x.map{it + 1}
}

fun withAnonFunction()
{
    val x = listOf(1,2,3)

    val z = {a : Int -> a + 1}

    val y = x.map(z)
    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
}

fun withFiles()
{
    //val file : InputStream = File("test.txt").inputStream()
}

fun withFor()
{
    while (5 > 6 || 6==6)
    {

    }
}



fun file()
{
    val x = File("test")
    x.isFile()
    x.reader().read()
}

fun objField()
{
    val x = ClassOne()
    val y = x.blub.Age
}

class ClassOne()
{
    var blub : TwoElectricBoogaloo = TwoElectricBoogaloo()
}

class TwoElectricBoogaloo()
{
    var Age : Int = 0
}

fun withReturn() : Int
{
    val x = 0
    val y = 2
    return x + y
}

// what happens if you have a function call, and they pass you the function argument twice
// I need to consider what it means for something to be used. if you use x.Name, is x used, or just x.Name as a whole
// I need to figure out the formal semantics of usage.
// Cases like passing something to function count as uses
// Kotlin compiler code might help. The file is called CFGNode it is found here within the repo: compiler/fir/semantics/src/org/jetbrains/kotlin/fir/resolve/dfa/cfg/CFGNode.kt
// compiler is found here https://github.com/JetBrains/kotlin