package gal.orxeira.expenseapp.core.domain.model

import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.cat_bike
import expenseapp.shared.generated.resources.cat_bills
import expenseapp.shared.generated.resources.cat_cashback
import expenseapp.shared.generated.resources.cat_communications
import expenseapp.shared.generated.resources.cat_food
import expenseapp.shared.generated.resources.cat_home
import expenseapp.shared.generated.resources.cat_media
import expenseapp.shared.generated.resources.cat_price
import expenseapp.shared.generated.resources.cat_savings
import expenseapp.shared.generated.resources.cat_shopping
import org.jetbrains.compose.resources.DrawableResource

data class ExpenseCategory(
    val name: String,
    val icon: DrawableResource
) {
    override fun toString(): String {
        return name
    }
}

val expenseCategories = listOf(
    ExpenseCategory(
        name = "Utilities",
        icon = Res.drawable.cat_bills
    ),
    ExpenseCategory(
        name = "Transport",
        icon = Res.drawable.cat_bike
    ),
    ExpenseCategory(
        name = "Entertainment",
        icon = Res.drawable.cat_media
    ),
    ExpenseCategory(
        name = "Communication",
        icon = Res.drawable.cat_communications
    ),
    ExpenseCategory(
        name = "Groceries",
        icon = Res.drawable.cat_food
    ),
    ExpenseCategory(
        name = "Investments",
        icon = Res.drawable.cat_price
    ),
    ExpenseCategory(
        name = "Shopping",
        icon = Res.drawable.cat_shopping
    ),
    ExpenseCategory(
        name = "Taxes",
        icon = Res.drawable.cat_cashback
    ),
    ExpenseCategory(
        name = "Sports",
        icon = Res.drawable.cat_bike
    ),
    ExpenseCategory(
        name = "Rent",
        icon = Res.drawable.cat_home
    ),
    ExpenseCategory(
        name = "Savings",
        icon = Res.drawable.cat_savings
    )
)