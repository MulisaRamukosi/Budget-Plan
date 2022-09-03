package com.puzzle.industries.data.repository.expenseGroup

import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupDao
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupMapper
import com.puzzle.industries.data.util.ResponseMessageFactory
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ExpenseGroupRepositoryImpl @Inject constructor(
    private val expenseGroupMapper: ExpenseGroupMapper,
    private val expenseGroupDao: ExpenseGroupDao,
    private val responseMessageFactory: ResponseMessageFactory
) : ExpenseGroupRepository {

    override suspend fun create(vararg entity: ExpenseGroup): Response<Boolean> {
        val entities =
            entity.map { expenseGroup -> expenseGroupMapper.toExpenseGroupEntity(expenseGroup = expenseGroup) }
                .toTypedArray()

        return responseMessageFactory.buildInsertMessage {
            val result = expenseGroupDao.insert(entity = entities)
            result.size == entity.size
        }
    }

    override fun read(): Response<Flow<List<ExpenseGroupWithExpenses>>> {
        return Response(
            response = expenseGroupDao.read().flowOn(context = Dispatchers.IO).map {
                val entities = mutableListOf<ExpenseGroupWithExpenses>()
                it.forEach { expenseGroupWithExpensesEntity ->
                    entities.add(expenseGroupMapper.toExpenseGroupWithExpenses(entity = expenseGroupWithExpensesEntity))
                }
                return@map entities
            }.conflate()
        )
    }

    override suspend fun update(entity: ExpenseGroup): Response<Boolean> {
        val updateEntity = expenseGroupMapper.toExpenseGroupEntity(expenseGroup = entity)

        return responseMessageFactory.buildUpdateMessage {
            val result = expenseGroupDao.update(updateEntity)
            result == 1
        }
    }

    override suspend fun delete(vararg entity: ExpenseGroup): Response<Boolean> {
        val entities =
            entity.map { expenseGroup -> expenseGroupMapper.toExpenseGroupEntity(expenseGroup = expenseGroup) }
                .toTypedArray()
        val result = expenseGroupDao.delete(entity = entities)

        return responseMessageFactory.buildDeleteMessage(success = result == entity.size)
    }
}