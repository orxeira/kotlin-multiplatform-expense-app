package gal.orxeira.expenseapp.core.domain.repository

import gal.orxeira.expenseapp.core.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(): Flow<List<Expense>>
    fun getExpense(id: Int): Flow<Expense?>
    suspend fun addExpense(expense: Expense)
    suspend fun deleteExpense(id: Int)
    suspend fun deleteAllExpenses()
}