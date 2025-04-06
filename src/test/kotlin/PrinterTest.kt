import org.example.printPublicDeclarations
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.KtPsiFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class PrinterTest {
    private val disposable = Disposer.newDisposable()
    private val environment = KotlinCoreEnvironment.createForProduction(
        disposable,
        CompilerConfiguration(),
        EnvironmentConfigFiles.JVM_CONFIG_FILES
    )
    private val psiFactory = KtPsiFactory(environment.project)

    private fun captureStdout(block: () -> Unit): String {
        val originalOut = System.out
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        block()
        System.setOut(originalOut)
        return outContent.toString().trim()
    }

    @Test
    fun `should print public property and typealias`() {
        val file = psiFactory.createFile("""
            val foo = 42
            typealias ID = String
        """.trimIndent())

        val output = captureStdout {
            printPublicDeclarations(file)
        }

        assertEquals(
            """
            val foo
            typealias ID
            """.trimIndent(),
            output
        )
    }

    @Test
    fun `should print sealed class and nested declarations`() {
        val file = psiFactory.createFile("""
            sealed class Shape {
                class Circle : Shape()
                object Square : Shape()
            }
        """.trimIndent())

        val output = captureStdout {
            printPublicDeclarations(file)
        }

        assertEquals(
            """
            sealed class Shape {
              class Circle {
              }
              object Square {
              }
            }
            """.trimIndent(),
            output
        )
    }

    @Test
    fun `should print enum class and entries`() {
        val file = psiFactory.createFile("""
            enum class Mode {
                BASIC, ADVANCED
            }
        """.trimIndent())

        val output = captureStdout {
            printPublicDeclarations(file)
        }

        assertEquals(
            """
            enum class Mode {
              enum entry BASIC
              enum entry ADVANCED
            }
            """.trimIndent(),
            output
        )
    }
}
