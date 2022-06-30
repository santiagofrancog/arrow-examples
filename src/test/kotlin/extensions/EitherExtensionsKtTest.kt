package extensions

import arrow.core.left
import arrow.core.right
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

private data class MockClass(val str: String)
private data class MockError(val msg: String)

internal class EitherExtensionsKtTest {

    @Test
    fun `shouldBeRight - ok`() {
        val obj = MockClass("Value")
        obj.right() shouldBeRight obj
    }

    @Test
    fun `shouldBeRight - right with different value`() {
        val obj = MockClass("Value")
        val obj2 = MockClass("Value2")
        assertThrows(AssertionError::class.java) { obj.right() shouldBeRight obj2 }
    }

    @Test
    fun `shouldBeRight - is left`() {
        val err = MockError("Error")
        val obj = MockClass("Value")
        assertThrows(AssertionError::class.java) { err.left() shouldBeRight obj }
    }

    @Test
    fun `shouldBeLeft - ok`() {
        val err = MockError("Error")
        err.left() shouldBeLeft err
    }

    @Test
    fun `shouldBeLeft - left with different value`() {
        val err = MockError("Error")
        val err2 = MockError("Error2")
        assertThrows(AssertionError::class.java) { err.left() shouldBeLeft err2 }
    }

    @Test
    fun `shouldBeLeft - is right`() {
        val obj = MockClass("Value")
        val err = MockError("Error")
        assertThrows(AssertionError::class.java) { obj.right() shouldBeLeft err }
    }

    @Test
    fun `shouldBeLeftOfType - ok`() {
        MockError("Error").left().shouldBeLeftOfType<MockError>()
    }

    @Test
    fun `shouldBeLeftOfType - left with different type`() {
        val err = MockError("Error")
        val assertionErr = assertThrows(AssertionError::class.java) { err.left().shouldBeLeftOfType<java.lang.Exception>() }
        assertEquals("Either should be Left<${Exception::class.qualifiedName}> but was Left<${MockError::class.qualifiedName}>", assertionErr.message)
    }

    @Test
    fun `shouldBeLeftOfType - right`() {
        val obj = Object()
        val assertionErr = assertThrows(AssertionError::class.java) { obj.right().shouldBeLeftOfType<MockError>() }
        assertEquals("Either should be Left<${MockError::class.qualifiedName}> but was Right($obj)", assertionErr.message)
    }
}
