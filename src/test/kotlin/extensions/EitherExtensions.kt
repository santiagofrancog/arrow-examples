package extensions
import arrow.core.left
import arrow.core.right
import io.kotest.matchers.shouldBe

infix fun <R : Any?> arrow.core.Either<Any?, R>.shouldBeRight(expected: R) =
    this shouldBe expected.right()

infix fun <R : Any?> arrow.core.Either<Any?, R>.shouldBeRight(fn: (R) -> Unit) {
    this.fold(
        { assert(false) { "error" } },
        { fn(it) }
    )
}

infix fun <L : Any?> arrow.core.Either<L, Any?>.shouldBeLeft(expected: L) =
    this shouldBe expected.left()

inline fun <reified A> arrow.core.Either<Any?, Any?>.shouldBeLeftOfType() =
    when (this) {
        is arrow.core.Either.Right -> {
            assert(false) { "Either should be Left<${A::class.qualifiedName}> but was Right(${this.value})" }
        }
        is arrow.core.Either.Left -> {
            val valueA = this.value
            if (valueA is A)
                assert(true) { "Either should be Left<${A::class.qualifiedName}>" }
            else
                assert(false) { "Either should be Left<${A::class.qualifiedName}> but was Left<${if (valueA == null) "Null" else valueA::class.qualifiedName}>" }
        }
    }
