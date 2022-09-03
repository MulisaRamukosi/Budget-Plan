package com.puzzle.industries.domain.repository.expenseGroup

import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses

interface ExpenseGroupRepository : Insert<ExpenseGroup>, Read<ExpenseGroupWithExpenses>,
    Update<ExpenseGroup>, Delete<ExpenseGroup>