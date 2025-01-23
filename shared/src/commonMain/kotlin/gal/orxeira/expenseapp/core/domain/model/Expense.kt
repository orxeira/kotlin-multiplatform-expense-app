package gal.orxeira.expenseapp.core.domain.model

import kotlinx.datetime.LocalDate

data class Expense(
    val id: Int = 0,
    val description: String,
    val amount: Double,
    val date: LocalDate,
    val category: ExpenseCategory
)