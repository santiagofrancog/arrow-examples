package _0_zero_division

import extensions.shouldBeLeft
import extensions.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class OTPCalculatorTest {

    @Test
    fun division() {
        OTPCalculator.divide(3f, 2f) shouldBe 1.5f
    }

    @Test
    fun divisionByZero() {
        assertThrows(OTPCalculator.ZERO_DIVISION_EX.javaClass) { OTPCalculator.divide(3f, 0f) }
    }

    @Test
    fun divisionEither() {
        OTPCalculator.divideEither(3f, 2f) shouldBeRight 1.5f
    }

    @Test
    fun divisionByZeroEither() {
        OTPCalculator.divideEither(3f, 0f) shouldBeLeft OTPCalculator.ZERO_DIVISION_EX
    }
}
