package com.puzzle.industries.data.repository.expense

import com.puzzle.industries.data.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.util.ResponseMessageFactory
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

internal class ExpenseRepositoryImpl @Inject constructor(
    private val expenseMapper: ExpenseMapper,
    private val expenseDao: ExpenseDao,
    private val responseMessageFactory: ResponseMessageFactory
) : ExpenseRepository {

    override suspend fun insert(vararg entity: Expense): Response<Boolean> {
        val entities = entity.map { expense -> expenseMapper.toExpenseEntity(expense = expense) }
            .toTypedArray()

        return responseMessageFactory.buildInsertMessage {
            val result = expenseDao.insert(entity = entities)
            result.size == entity.size
        }
    }

    override suspend fun update(vararg entity: Expense): Response<Boolean> {
        val entities = entity.map { expense -> expenseMapper.toExpenseEntity(expense = expense) }
            .toTypedArray()

        return responseMessageFactory.buildUpdateMessage(expectedAffectedRow = entities.size) {
            expenseDao.update(entity = entities)
        }
    }

    override suspend fun delete(vararg entity: Expense): Response<Boolean> {
        val entities = entity.map { expense -> expenseMapper.toExpenseEntity(expense = expense) }
            .toTypedArray()

        return responseMessageFactory.buildDeleteMessage(expectedAffectedRow = entities.size){
            expenseDao.delete(entity = entities)
        }
    }
}