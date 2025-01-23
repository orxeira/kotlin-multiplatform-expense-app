package gal.orxeira.expenseapp.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gal.orxeira.expenseapp.feature.expensedetail.ExpenseDetailScreen
import gal.orxeira.expenseapp.feature.about.AboutScreen
import gal.orxeira.expenseapp.feature.addexpense.AddExpenseScreen
import gal.orxeira.expenseapp.feature.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,

    ) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.Home
    ) {
        composable<Screens.Home> {
            HomeScreen(
                navController = navController
            )
        }

        composable<Screens.About> {
            AboutScreen(
                navController = navController
            )
        }

        composable<Screens.AddExpense> {
            AddExpenseScreen(
                navController = navController
            )
        }

        composable<Screens.ExpenseDetail> { navBackStackEntry ->
            val addExpense: Screens.AddExpense = navBackStackEntry.toRoute()
            ExpenseDetailScreen(
                expenseId = addExpense.expenseId,
                navController = navController
            )

        }
    }
}