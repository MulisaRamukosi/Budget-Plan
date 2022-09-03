package com.puzzle.industries.domain.usescases.income

import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.models.income.Income

interface CreateIncomeUseCase : Create<Income>
interface ReadIncomeUseCase : Read<Income>
interface UpdateIncomeUseCase : Update<Income>
interface DeleteIncomeUseCase : Delete<Income>

interface IncomeUseCase {
    val create: CreateIncomeUseCase
    val read: ReadIncomeUseCase
    val update: UpdateIncomeUseCase
    val delete: DeleteIncomeUseCase
}