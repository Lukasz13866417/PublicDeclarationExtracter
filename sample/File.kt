package sample

fun apiFunction() {}

private fun hiddenFunction() {}

object RootObject {
    val value = 123

    fun objectFunction() {}

    private class PrivateHelper {
        fun helperLogic() {}
    }

    companion object {
        fun companionVisible() {}
    }
}

sealed class Shape {
    class Circle(val radius: Double) : Shape()
    object Square : Shape()
}

class Container {
    fun exposedMethod() {}

    private fun privateMethod() {}

    class Nested {
        fun deep() {
            fun localHelper() {} // should be skipped
        }
    }
}

enum class Mode {
    BASIC,
    ADVANCED {
        fun advancedOnly() {} // shouldn't be detected
    }
}

interface Reporter {
    fun report()
}

typealias ID = String
