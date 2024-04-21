fun main()
{
    val a = bigboy(2,3)
    val b = bigboy(4,5)

    test(a)
    a.a = 6
}

fun test(a : bigboy)
{
    x = a
}

var x : bigboy = bigboy(0,0)

class bigboy(var a : Int, var b: Int) // a and b are captured