package com.neoterux.espfier.data.adapter

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.neoterux.espfier.custom.NotUsedException
import com.neoterux.espfier.data.Hour

/**
 * ## Json Hour Adapter
 * This class handle the behaviour to parse a json string to the specified Hour.
 */
class JsonHourAdapter: TypeAdapter<Hour>() {

    override fun write(out: JsonWriter?, value: Hour?){
        out?.use {
            it.jsonValue(value?.toString() ?: "00:00")
        }
    }

    override fun read(input: JsonReader): Hour {
        val value = input.nextString()
        return Hour.fromString(value)
    }

}
