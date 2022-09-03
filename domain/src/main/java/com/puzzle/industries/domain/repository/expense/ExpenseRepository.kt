package com.puzzle.industries.domain.repository.expense

import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.common.crud.*

interface ExpenseRepository : Insert<Expense>, Update<Expense>, Delete<Expense>