package _0_zero_division

import arrow.core.Either
import arrow.core.left
import arrow.core.right

object OTPCalculator {

    /**
     * Devuelve el resultado de la división siempre y cuando el divisor no sea 0 (cero).
     * Si el divisor es 0 (cero), lanza una ArithmeticException
     * */
    fun divide(divisor: Float, dividend: Float): Float =
        if (dividend == 0f)
            throw ZERO_DIVISION_EX
        else
            divisor / dividend

    /**
     * Devuelve un Either.Right con el resultado de la división
     * o un Either.Left con una ArithmeticException si el divisor es 0 (cero)
     */
    fun divideEither(divisor: Float, dividend: Float): Either<ArithmeticException, Float> =
        if (dividend == 0f)
            ZERO_DIVISION_EX.left()
        else
            (divisor / dividend).right()

    val ZERO_DIVISION_EX = ArithmeticException("Zero division")

    /**
     * Definición de función:
     * Dados dos conjuntos A y B, una función entre ellos es una asociación (f) que a cada elemento de A le asigna un único elemento de B.
     * Se dice entonces que A es el dominio de f y que B es su codominio/imagen.
     *
     * Qué diferencia hay entre estos dos métodos?
     * El primero no responde a la definición de función ya que no tiene imagen para ningún elemento del dominio con la forma (x,0)
     * */
}
