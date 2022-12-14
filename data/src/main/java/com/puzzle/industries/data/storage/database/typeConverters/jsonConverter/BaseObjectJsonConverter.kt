package com.puzzle.industries.data.storage.database.typeConverters.jsonConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.storage.database.typeConverters.jsonConverter.deserializer.FrequencyDeserializer
import com.puzzle.industries.domain.constants.FrequencyType

internal open class BaseObjectJsonConverter<T>(private val typeToken: TypeToken<T>) {

    private var gson: Gson = GsonBuilder()
        .registerTypeAdapter(FrequencyType::class.java, FrequencyDeserializer())
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