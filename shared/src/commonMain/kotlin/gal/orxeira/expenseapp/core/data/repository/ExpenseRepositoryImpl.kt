package gal.orxeira.expenseapp.core.data.repository

import app.cash.sqldelight.coroutines.asFlow
import gal.orxeira.expenseapp.core.data.mapper.toExpense
import gal.orxeira.expenseapp.core.data.mapper.toExpenseEntity
import gal.orxeira.expenseapp.core.data.utils.mapToList
import gal.orxeira.expenseapp.core.data.utils.mapToOneOrNull
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.domain.repository.ExpenseRepository
import gal.orxeira.expenseapp.database.ExpenseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(database: ExpenseDatabase) : ExpenseRepository {
    private val dbQueries = database.expenseQueries
    override fun getExpenses(): Flow<List<Expense>> {
        return dbQueries
            .getAllExpenses()
            .asFlow()
            .mapToList()
            .map { expenses ->
                expenses.map {
                    it.toExpense()
                }
            }
    }

    override fun getExpense(id: Int): Flow<Expense?> {
        return dbQueries
            .getExpenseById(id)
            .asFlow()
            .mapToOneOrNull()
            .map { expenseEntity ->
                expenseEntity?.toExpense()
            }
    }

    override suspend fun addExpense(expense: Expense) {
        expense.toExpenseEntity().let { entity ->
            dbQueries.insertExpense(
                description = entity.description,
                amount = entity.amount,
                date = entity.date,
                category = entity.category
            )
        }
    }

    override suspend fun deleteExpense(id: Int) {
        dbQueries.deleteExpenseById(id)
    }

    override suspend fun deleteAllExpenses() {
        dbQueries.deleteAllExpenses()
    }


}