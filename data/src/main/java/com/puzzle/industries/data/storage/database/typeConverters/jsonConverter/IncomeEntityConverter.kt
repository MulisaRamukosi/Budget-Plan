package com.puzzle.industries.data.storage.database.typeConverters.jsonConverter

import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.storage.database.entity.income.IncomeEntity

internal class IncomeEntityConverter :
    BaseObjectJsonConverter<IncomeEntity>(typeToken = object : TypeToken<IncomeEntity>() {})