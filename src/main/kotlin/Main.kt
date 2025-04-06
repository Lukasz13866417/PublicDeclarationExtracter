package org.example

import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.psi.KtPsiFactory
import java.io.File

fun main(args: Array<String>) {
    val input = File(args.getOrNull(0) ?: error("Provide path to a .kt file or a directory"))
    require(input.exists()) {
        "Invalid path: ${input.absolutePath}"
    }

    val disposable = Disposer.newDisposable()
    val configuration = CompilerConfiguration()

    val environment = KotlinCoreEnvironment.createForProduction(
        disposable,
        configuration,
        EnvironmentConfigFiles.JVM_CONFIG_FILES
    )

    val psiFactory = KtPsiFactory(environment.project)

    val kotlinFiles: Sequence<File> = when {
        input.isFile && input.extension == "kt" -> sequenceOf(input)
        input.isDirectory -> input.walkTopDown().filter { it.isFile && it.extension == "kt" }
        else -> error("Input must be a .kt file or a directory containing .kt files")
    }

    kotlinFiles.forEach { file ->
        println(file)
        val ktFile = psiFactory.createFile(file.readText())
        printPublicDeclarations(ktFile)
    }

    Disposer.dispose(disposable)
}
