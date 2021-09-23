package com.neoterux.espfier.data

import com.google.gson.annotations.SerializedName

data class Course(
        var id: String = "",
        @SerializedName("class-no") var classNo:String = "",
        @SerializedName("teacher-name") var teacherName: String = "",
        var cenacad: Double = -1.0,
        @SerializedName("depends-on") var dependsOn: String = "",
        @SerializedName("daily-schedule", alternate = ["daily"]) var dailySched: List<Schedule> = emptyList(),
){
    @SerializedName("exam-schedule", alternate = ["exam"])
    lateinit var examSched: Schedule
}
