package com.puzzle.industries.data.database.typeConverters.jsonConverter

import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.database.entity.income.IncomeEntity

internal class IncomeEntityConverter :
    BaseObjectJsonConverter<IncomeEntity>(typeToken = object : TypeToken<IncomeEntity>() {})