package gal.orxeira.expenseapp.core.ui.navigation

import kotlinx.serialization.Serializable
sealed class Screens {
    @Serializable
    object Home: Screens()

    @Serializable
    data class AddExpense(val expenseId: Int = -1): Screens()

    @Serializable
    data class ExpenseDetail(val expenseId: Int = -1): Screens()

    @Serializable
    object About: Screens()

}