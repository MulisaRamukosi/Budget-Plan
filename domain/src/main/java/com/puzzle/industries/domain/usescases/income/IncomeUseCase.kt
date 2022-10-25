package com.puzzle.industries.domain.usescases.income

import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.usescases.base.UseCaseDelete
import com.puzzle.industries.domain.usescases.base.UseCaseInsert
import com.puzzle.industries.domain.usescases.base.UseCaseRead
import com.puzzle.industries.domain.usescases.base.UseCaseUpdate

interface InsertIncomeUseCase : UseCaseInsert<Income>
interface ReadIncomeUseCase : UseCaseRead<Income>
interface UpdateIncomeUseCase : UseCaseUpdate<Income>
interface DeleteIncomeUseCase : UseCaseDelete<Income>

interface IncomeUseCase {
    val create: InsertIncomeUseCase
    val read: ReadIncomeUseCase
    val update: UpdateIncomeUseCase
    val delete: DeleteIncomeUseCase
}