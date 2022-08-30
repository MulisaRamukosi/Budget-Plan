package com.puzzle.industries.data.mapper.expenseGroup

import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupEntity
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup

internal class ExpenseGroupMapper {

    fun toExpenseGroupEntity(expenseGroup: ExpenseGroup): ExpenseGroupEntity {
        return ExpenseGroupEntity(
            id = expenseGroup.id,
            name = expenseGroup.name
        )
    }

    fun toExpenseGroup(expenseGroup: ExpenseGroupEntity): ExpenseGroup {
        return ExpenseGroup(
            id = expenseGroup.id,
            name = expenseGroup.name,
            lastModifyDate = expenseGroup.lastModifyDate
        )
    }
}