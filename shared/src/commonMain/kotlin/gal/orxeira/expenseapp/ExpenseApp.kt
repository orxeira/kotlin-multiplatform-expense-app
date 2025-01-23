package gal.orxeira.expenseapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import gal.orxeira.expenseapp.core.ui.theme.ExpenseAppTheme
import gal.orxeira.expenseapp.main.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun ExpenseApp() {
    KoinContext {
        ExpenseAppTheme {
            val navController = rememberNavController()

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                Box(Modifier.safeDrawingPadding()) {
                    MainScreen(
                        navController = navController,
                    )
                }
            }
        }
    }
}
