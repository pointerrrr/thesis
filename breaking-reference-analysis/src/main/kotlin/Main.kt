open class A {

}

class B : A()
{
    fun foo()
    {

    }
}

fun main(args: Array<String>) {



}

fun test(x : A)
{
    val b = x
    require(x is B)
    b.foo()
}