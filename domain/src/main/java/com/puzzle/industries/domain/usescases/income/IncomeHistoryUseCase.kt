package com.puzzle.industries.domain.usescases.income

import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.models.income.IncomeHistory

interface InsertIncomeHistoryUseCase : Insert<IncomeHistory>
interface DeleteIncomeHistoryUseCase : Delete<IncomeHistory>
interface ReadIncomeHistoryUseCase : Read<IncomeHistory>

interface IncomeHistoryUseCase {
    val create: InsertIncomeHistoryUseCase
    val delete: DeleteIncomeHistoryUseCase
    val read: ReadIncomeHistoryUseCase
}