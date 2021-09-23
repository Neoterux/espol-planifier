package com.neoterux.espfier.data

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.neoterux.espfier.data.adapter.JsonHourAdapter

data class Schedule(
        @SerializedName("day")
        var day: String,
        @JsonAdapter(JsonHourAdapter::class)
        @SerializedName("init-hour", alternate = ["init", "start"])
        var initTime: Hour,
        @JsonAdapter(JsonHourAdapter::class)
        @SerializedName("end-hour", alternate = ["end", "finish"])
        var endTime: Hour
)
