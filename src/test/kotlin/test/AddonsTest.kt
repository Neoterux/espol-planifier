package test

import com.neoterux.espfier.custom.Color
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AddonsTest {

    @Test
    @DisplayName("Hex Color test")
    fun testColor() {
        Assertions.assertEquals("#010304", Color(1, 3, 4).hex())
        assertEquals("#FF0000", Color(255, 0, 0).hex())
    }

    @Test
    fun testRgba() {
        assertEquals("rgba(1,1,3,0.39)", Color(1, 1, 3,  0.3857942).rgba())
    }
}
