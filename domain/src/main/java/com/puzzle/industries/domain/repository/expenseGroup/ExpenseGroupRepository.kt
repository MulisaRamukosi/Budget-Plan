package com.puzzle.industries.domain.repository.expenseGroup

import com.puzzle.industries.domain.common.*
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup

interface ExpenseGroupRepository : Create<ExpenseGroup>, ReadAll<List<ExpenseGroup>>,
    Update<ExpenseGroup>, Delete<ExpenseGroup>, MultiDelete<ExpenseGroup>