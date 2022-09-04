package com.puzzle.industries.data.database.typeConverters.jsonConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.database.typeConverters.jsonConverter.deserializer.FrequencyDeserializer
import com.puzzle.industries.domain.constants.Frequency

internal open class BaseObjectJsonConverter<T>(private val typeToken: TypeToken<T>) {

    private var gson: Gson = GsonBuilder()
        .registerTypeAdapter(Frequency::class.java, FrequencyDeserializer())
        .create()

    @TypeConverter
    fun toJsonString(entity: T): String {
        return gson.toJson(entity)
    }

    @TypeConverter
    fun toObject(jsonString: String): T {
        val type = typeToken.type
        return gson.fromJson(jsonString, type)
    }
}