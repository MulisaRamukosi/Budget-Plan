package com.puzzle.industries.data.database.typeConverters.jsonConverter

import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupEntity

internal class ExpenseGroupEntityConverter :
    BaseObjectJsonConverter<ExpenseGroupEntity>(typeToken = object :
        TypeToken<ExpenseGroupEntity>() {})