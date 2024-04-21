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