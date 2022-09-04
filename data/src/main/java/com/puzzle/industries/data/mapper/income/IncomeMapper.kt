package com.puzzle.industries.data.mapper.income

import com.puzzle.industries.data.database.entity.income.IncomeEntity
import com.puzzle.industries.domain.models.income.Income

internal class IncomeMapper {

    fun toIncomeEntity(income: Income) : IncomeEntity{
        return IncomeEntity(
            id = income.id,
            title = income.title,
            amount = income.amount,
            frequency = income.frequency,
            description = income.description
        )
    }

    fun toIncome(income: IncomeEntity) : Income{
        return Income(
            id = income.id,
            title = income.title,
            amount = income.amount,
            frequency = income.frequency,
            description = income.description,
            lastModifyDate = income.lastModifyDate
        )
    }
}