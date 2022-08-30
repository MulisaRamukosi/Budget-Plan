package com.puzzle.industries.domain.repository.income

import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.ReadAll
import com.puzzle.industries.domain.common.Update

interface IncomeRepository : Create<Income>, ReadAll<Income>, Update<Income>, Delete<String>