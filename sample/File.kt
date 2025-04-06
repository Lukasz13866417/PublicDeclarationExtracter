package my.test.package

fun topLevelFunction() {}
val topLevelProperty = 42

private fun privateFunction() {}

internal class InternalClass {
    fun internalClassMethod() {}
}

class OuterClass {
    fun publicMethod() {}

    private fun privateMethod() {}

    class NestedClass {
        fun nestedMethod() {}
    }

    inner class InnerClass {
        fun innerMethod() {}
    }

    companion object {
        fun companionFunction() {}
    }
}

interface MyInterface {
    fun interfaceMethod()
}

abstract class AbstractThing {
    abstract fun mustImplement()
}

sealed class SealedType {
    object Sub1 : SealedType()
    class Sub2(val data: String) : SealedType()
}

enum class MyEnum {
    ONE, TWO
}

object Singleton {
    fun singletonMethod() {}
}