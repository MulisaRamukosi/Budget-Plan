package com.puzzle.industries.domain.usescases.income

import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.ReadAll
import com.puzzle.industries.domain.common.Update
import com.puzzle.industries.domain.models.income.Income

interface CreateIncomeUseCase : Create<Income>
interface ReadIncomeUseCase : ReadAll<Income>
interface UpdateIncomeUseCase : Update<Income>
interface DeleteIncomeUseCase : Delete<Income>

interface IncomeUseCase {

    val create: CreateIncomeUseCase
    val read: ReadIncomeUseCase
    val update: UpdateIncomeUseCase
    val delete: DeleteIncomeUseCase

}