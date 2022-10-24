package com.puzzle.industries.data.storage.database.typeConverters.jsonConverter

import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity

internal class ExpenseEntityConverter :
    BaseObjectJsonConverter<ExpenseEntity>(typeToken = object : TypeToken<ExpenseEntity>() {})