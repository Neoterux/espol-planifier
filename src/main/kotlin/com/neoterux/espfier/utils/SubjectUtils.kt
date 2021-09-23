package com.neoterux.espfier.utils

import com.neoterux.espfier.data.Hour
import com.neoterux.espfier.data.Schedule

fun Schedule.intersect(other: Schedule): Boolean {
    return day == other.day && (other.initTime.inRange(this) || this.initTime.inRange(other))
}

fun Hour.inRange(start: Hour, end: Hour) = this in start..end

fun Hour.inRange(sched: Schedule) =  inRange(sched.initTime, sched.endTime)

operator fun Hour.minus(other: Hour) = Hour(this.hour - other.hour, this.minute - other.minute)

fun Hour.totalMinutes() = this.hour * 60 + this.minute

fun Hour.countSteps(stepSize: Int = 30): Int {
    var count = 0
    var min = this.totalMinutes()
    println("Total min: $min")
    while(min > 0){
        count++
        min -= stepSize
    }
    return count
}

fun Schedule.cellQuantity() = (endTime - initTime).countSteps()
