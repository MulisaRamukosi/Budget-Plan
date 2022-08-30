package com.puzzle.industries.domain.repository.expense

import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.Update

interface ExpenseRepository : Create<Expense>, Update<Expense>, Delete<Expense>