package org.example

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*

internal fun printPublicDeclarations(ktFile: KtFile) {
    for (decl in ktFile.declarations) {
        recursivePrintPublic(decl)
    }
}

private fun recursivePrintPublic(decl: KtDeclaration, indent: String = "") {
    if (!isEffectivelyPublic(decl)) return

    when (decl) {

        is KtEnumEntry -> {
            println("${indent}enum entry ${decl.name}")
        }

        is KtNamedFunction -> {
            println("${indent}fun ${decl.name}()")
        }

        is KtClass -> {
            val classKeyword = when {
                decl.isInterface() -> "interface"
                decl.isEnum() -> "enum class"
                decl.isSealed() -> "sealed class"
                decl.isAnnotation() -> "annotation class"
                decl.hasModifier(KtTokens.ABSTRACT_KEYWORD) -> "abstract class"
                else -> "class"
            }

            println("${indent}$classKeyword ${decl.name} {")
            decl.declarations.forEach {
                recursivePrintPublic(it, "$indent  ")
            }
            println("${indent}}")
        }

        is KtObjectDeclaration -> {
            println("${indent}object ${decl.name ?: "<anonymous>"} {")
            decl.declarations.forEach {
                recursivePrintPublic(it, "$indent  ")
            }
            println("${indent}}")
        }

        is KtProperty -> {
            println("${indent}val ${decl.name}")
        }

        is KtTypeAlias -> {
            println("${indent}typealias ${decl.name}")
        }
    }
}

internal fun isEffectivelyPublic(decl: KtDeclaration): Boolean {
    return !decl.hasModifier(KtTokens.PRIVATE_KEYWORD)
            && !decl.hasModifier(KtTokens.PROTECTED_KEYWORD)
            && !decl.hasModifier(KtTokens.INTERNAL_KEYWORD)
}
