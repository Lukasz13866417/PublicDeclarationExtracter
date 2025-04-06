# PublicDeclarationExtracter

A Kotlin-based CLI tool that extracts and prints **all public declarations** from a Kotlin codebase.

This tool was built with a Kotlin compiler PSI via `kotlin-compiler-embeddable`, no extra libraries or plugins.
For convenient UX, this can be built as a self-contained JAR `solution.jar`. I included a wrapper shell script `solution.sh` that runs the JAR.

---
## How to build
Open the project in IntelliJ IDEA, open ```build.gradle.kts``` and run the following task by clicking the green arrow button:\
![img.png](img.png)\
This will create the ```solution.jar``` in the same folder as this README and the script ```solution.sh```.
## How to run

Provide the program with a path to any Kotlin codebase and run the JAR:

```bash
git clone https://github.com/JetBrains/Exposed
chmod +x ./solution
./solution ./Exposed
```
or instead of using the wrapper, you can just:
```declarative
java -jar solution.jar ./Exposed
```

## Example
### Input
```Kotlin
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
```
### Output
```Kotlin
fun topLevelFunction()
val topLevelProperty
class OuterClass {
  fun publicMethod()
  class NestedClass {
    fun nestedMethod()
  }
  class InnerClass {
    fun innerMethod()
  }
  object Companion {
    fun companionFunction()
  }
}
interface MyInterface {
  fun interfaceMethod()
}
abstract class AbstractThing {
  fun mustImplement()
}
sealed class SealedType {
  object Sub1 {
  }
  class Sub2 {
  }
}
enum class MyEnum {
  enum entry ONE
  enum entry TWO
}
object Singleton {
  fun singletonMethod()
}
```
