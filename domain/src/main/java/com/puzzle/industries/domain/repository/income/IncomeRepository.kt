package com.puzzle.industries.domain.repository.income

import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.common.crud.*

interface IncomeRepository : Create<Income>, Read<Income>, Update<Income>, Delete<Income>