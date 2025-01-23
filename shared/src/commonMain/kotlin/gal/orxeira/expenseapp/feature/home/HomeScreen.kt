package gal.orxeira.expenseapp.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.il_completed
import expenseapp.shared.generated.resources.il_empty
import expenseapp.shared.generated.resources.overview
import expenseapp.shared.generated.resources.welcome
import expenseapp.shared.generated.resources.welcome_add_expense
import expenseapp.shared.generated.resources.welcome_instructions
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.ui.components.ConfirmDeleteDialog
import gal.orxeira.expenseapp.core.ui.components.ExpenseItem
import gal.orxeira.expenseapp.core.ui.navigation.Screens
import gal.orxeira.expenseapp.core.ui.utils.koinViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val expensesState = viewModel.expenses.collectAsState().value
    val showConfirmDeleteDialog = viewModel.showConfirmDeleteDialog.collectAsState().value

    showConfirmDeleteDialog?.let { expenseToDelete ->
        ConfirmDeleteDialog(
            expenseToDelete,
            onDeleteConfirm = { viewModel.deleteExpense(expenseToDelete) },
            onDismiss = { viewModel.closeConfirmDeleteDialog() }
        )
    }

    HomeScreenContent(
        onClickExpense = {
            navController.navigate(Screens.ExpenseDetail(expenseId = it.id))
        },
        expenseState = expensesState,
        onDeleteExpense = { viewModel.setShowConfirmDeleteDialog(it) },
        onClickAddExpense = { navController.navigate(Screens.AddExpense()) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    expenseState: ExpenseState,
    onClickExpense: (expense: Expense) -> Unit,
    onDeleteExpense: (expense: Expense) -> Unit,
    onClickAddExpense: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row {
                            Text(
                                text = stringResource(Res.string.overview),
                                style = TextStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                                )
                            )
                        }
                    }
                })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            when (expenseState) {
                ExpenseState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is ExpenseState.Success -> {
                    val expenses = expenseState.expenses.sortedByDescending { it.date }

                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (expenses.isNotEmpty()) {
                            items(
                                items = expenses,
                                key = { it.id },
                            ) {
                                ExpenseItem(
                                    expense = it,
                                    onClick = onClickExpense,
                                    onLongClick = onDeleteExpense
                                )
                            }
                        } else {
                            item {
                                CreateFirstExpenseComponent(
                                    expenses = expenses,
                                    onClickAddExpense = onClickAddExpense
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CreateFirstExpenseComponent(
    expenses: List<Expense>,
    onClickAddExpense: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            modifier = Modifier
                .size(300.dp)
                .align(CenterHorizontally),
            painter = painterResource(if (expenses.isEmpty()) Res.drawable.il_empty else Res.drawable.il_completed),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            text = if (expenses.isEmpty()) {
               stringResource(Res.string.welcome)
            } else {
                ""
            },
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            text = if (expenses.isEmpty()) {
                stringResource(Res.string.welcome_instructions)
            } else {
                ""
            },
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
            ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))
        if (expenses.isEmpty()) {
            Button(
                onClick = onClickAddExpense,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = stringResource(Res.string.welcome_add_expense),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
        }
    }
}