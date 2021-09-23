package com.neoterux.espfier.data

import com.neoterux.espfier.custom.HourParseException
import kotlin.math.abs


class Hour(var hour: Int, var minute: Int): Comparable<Hour> {

    companion object {
        fun fromString(input: String): Hour {
            val raw = input.split(":")
            if(raw.size != 2)
                throw HourParseException("String not valid: $input")
            return Hour(raw[0].toInt(), raw[1].toInt())
        }
    }

    private var negative = false
    init {
        val temp = minute
        minute = 0
        addMinutes(temp)
    }

    override operator fun compareTo(other: Hour): Int {
        val hdiff = hour - other.hour * 60
        val mdiff = minute - other.minute
        return hdiff + mdiff
    }

    fun addMinutes(min: Int){
        minute += min
        hour += minute.floorDiv(60) + if(minute < 0) 1 else 0
        minute %= 60
        fixSign()
    }

    private fun fixSign(){
        if (minute < 0 || hour < 0)
            negative = true
    }

    override fun toString(): String {
        val sign = if (negative) "-" else ""
        return "%s%02d:%02d".format(sign, abs(hour), abs(minute))
    }

}
