package com.neoterux.espfier.data

import com.google.gson.annotations.SerializedName
import com.neoterux.espfier.custom.Color

data class Subject (
    @SerializedName("subject-name", alternate = ["subject_name", "subname"])
    var name: String = "",

    var code: String = "",

    @SerializedName("schedules")
    var schedules: List<Course> = emptyList()

){
    lateinit var color: Color
}
