package com.puzzle.industries.data.storage.database.typeConverters.jsonConverter.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.puzzle.industries.domain.constants.FrequencyType
import java.lang.reflect.Type

internal class FrequencyDeserializer : JsonDeserializer<FrequencyType>{
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): FrequencyType {
        json?.let{
            val stringValue = it.asString
            return FrequencyType.valueOf(stringValue)
        }
        return FrequencyType.MONTHLY
    }
}