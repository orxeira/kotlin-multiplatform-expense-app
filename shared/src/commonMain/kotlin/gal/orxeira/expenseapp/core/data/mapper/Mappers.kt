package gal.orxeira.expenseapp.core.data.mapper

import database.ExpenseEntity
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.domain.model.expenseCategories
import kotlinx.datetime.LocalDate

fun ExpenseEntity.toExpense() = Expense(
    id = id,
    description = description,
    amount = amount,
    date = LocalDate.parse(date),
    category = expenseCategories.firstOrNull { cat -> cat.name == this.category }
        ?: expenseCategories.first()
)

fun Expense.toExpenseEntity() = ExpenseEntity(
    id = id,
    description = description,
    amount = amount,
    date = date.toString(),
    category = category.name
)
