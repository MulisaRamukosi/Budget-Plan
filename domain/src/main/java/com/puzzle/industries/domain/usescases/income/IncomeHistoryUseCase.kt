package com.puzzle.industries.domain.usescases.income

import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.MultiDelete
import com.puzzle.industries.domain.common.ReadAll
import com.puzzle.industries.domain.models.income.IncomeHistory

interface CreateIncomeHistoryUseCase : Create<IncomeHistory>
interface DeleteIncomeHistoryUseCase : Delete<IncomeHistory>, MultiDelete<IncomeHistory>
interface ReadIncomeHistoryUseCase : ReadAll<IncomeHistory>

interface IncomeHistoryUseCase {
    val create: CreateIncomeHistoryUseCase
    val delete: DeleteIncomeHistoryUseCase
    val read: ReadIncomeHistoryUseCase
}