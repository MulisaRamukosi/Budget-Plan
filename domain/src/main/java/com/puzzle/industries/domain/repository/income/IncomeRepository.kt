package com.puzzle.industries.domain.repository.income

import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.Read
import com.puzzle.industries.domain.common.Update

interface IncomeRepository : Create<Income>, Read<Income>, Update<Income>, Delete<Income>