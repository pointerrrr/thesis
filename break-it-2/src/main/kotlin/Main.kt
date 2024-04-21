open class A
{

}

class B : A()
{
    fun foo()
    {

    }
}

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")


}

fun asdf (a : A)
{
    var b = a
    require(a is B)
    b.foo()
}

fun getSomeInt() : Int
{
    return 0
}

fun test() {
    var x: Int? = getSomeInt()
    run {
        if (x != null) x.inc() // SMARTCAST_IMPOSSIBLE
        x = null
    }
}