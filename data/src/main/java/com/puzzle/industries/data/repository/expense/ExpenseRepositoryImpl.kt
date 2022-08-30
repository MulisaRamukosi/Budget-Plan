package com.puzzle.industries.data.repository.expense

import com.puzzle.industries.data.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

internal class ExpenseRepositoryImpl @Inject constructor(
    private val expenseMapper: ExpenseMapper,
    private val expenseDao: ExpenseDao
) : ExpenseRepository {

    override suspend fun create(vararg entity: Expense): Boolean {
        val entities = entity.map { expense -> expenseMapper.toExpenseEntity(expense = expense) }
        val result = expenseDao.insert(entity = entities)
        return result.size == entity.size
    }

    override suspend fun update(entity: Expense): Boolean {
        val updateEntity = expenseMapper.toExpenseEntity(entity)
        val result = expenseDao.update(updateEntity)
        return result == 1
    }

    override suspend fun delete(vararg entity: Expense): Boolean {
        val entities = entity.map { expense -> expenseMapper.toExpenseEntity(expense = expense) }
        val result = expenseDao.delete(entity = entities)
        return result == entity.size
    }
}