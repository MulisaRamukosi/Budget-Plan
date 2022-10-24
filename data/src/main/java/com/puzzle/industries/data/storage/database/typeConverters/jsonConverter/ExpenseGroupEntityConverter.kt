package com.puzzle.industries.data.storage.database.typeConverters.jsonConverter

import com.google.gson.reflect.TypeToken
import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupEntity

internal class ExpenseGroupEntityConverter :
    BaseObjectJsonConverter<ExpenseGroupEntity>(typeToken = object :
        TypeToken<ExpenseGroupEntity>() {})