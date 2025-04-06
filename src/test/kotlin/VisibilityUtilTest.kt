import org.example.isEffectivelyPublic
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.psi.KtPsiFactory
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VisibilityUtilTest {
    private val disposable = Disposer.newDisposable()
    private val environment = KotlinCoreEnvironment.createForProduction(
        disposable,
        CompilerConfiguration(),
        EnvironmentConfigFiles.JVM_CONFIG_FILES
    )
    private val psiFactory = KtPsiFactory(environment.project)

    @Test
    fun `public function should be effectively public`() {
        val file = psiFactory.createFile("fun foo() {}")
        val fn = file.declarations.first()
        assertTrue(isEffectivelyPublic(fn))
    }

    @Test
    fun `private function should not be public`() {
        val file = psiFactory.createFile("private fun bar() {}")
        val fn = file.declarations.first()
        assertFalse(isEffectivelyPublic(fn))
    }
}
