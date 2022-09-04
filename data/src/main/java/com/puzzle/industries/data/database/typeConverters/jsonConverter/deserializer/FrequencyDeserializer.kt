package com.puzzle.industries.data.database.typeConverters.jsonConverter.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.puzzle.industries.domain.constants.Frequency
import java.lang.reflect.Type

internal class FrequencyDeserializer : JsonDeserializer<Frequency>{
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Frequency {
        json?.let{
            val stringValue = it.asString
            return Frequency.valueOf(stringValue)
        }
        return Frequency.MONTHLY
    }
}