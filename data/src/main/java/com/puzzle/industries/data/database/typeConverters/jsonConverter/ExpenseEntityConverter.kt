package com.puzzle.industries.data.database.typeConverters.jsonConverter

import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.database.entity.expense.ExpenseEntity

internal class ExpenseEntityConverter :
    BaseObjectJsonConverter<ExpenseEntity>(typeToken = object : TypeToken<ExpenseEntity>() {})