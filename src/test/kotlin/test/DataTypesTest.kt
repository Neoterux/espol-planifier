package test

import com.neoterux.espfier.data.Hour
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DataTypesTest {


    @DisplayName("Parsing Hour test")
    @Test
    fun testParse() {
        val target = Hour(1, 3)
        val target2 = Hour(24, 69)
        val target3 = Hour(689, 96)
        assertEquals("01:03", target.toString())
        assertEquals("25:09", target2.toString())
        assertEquals("690:36", target3.toString())
        println("Parsing test passed")
    }

    @DisplayName("Hour class test minute addition")
    @Test
    fun testHourAdd(){
        val testh = Hour(4, 10)
        var carry = 0
        for (i in 1..30) {
            testh.addMinutes(i)
            carry += i
        }
        println("Total minutes added: $carry")
        assertEquals("11:55", testh.toString())
        println("Positive minute addition passed")
    }


    @DisplayName("Negative minute addition")
    @Test
    fun negativeHour(){
        var testh = Hour(1, 0)
        testh.addMinutes(-90)
        assertEquals("-00:30", testh.toString())
        testh = Hour(10, 0)
        testh.addMinutes(-1230)
        assertEquals("-10:30", testh.toString())
    }
}
