package gal.orxeira.expenseapp.feature.expensedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.default_description
import expenseapp.shared.generated.resources.description
import expenseapp.shared.generated.resources.expense_detail
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.ui.components.CategoryIcon
import gal.orxeira.expenseapp.core.ui.components.ExpenseDatePicker
import gal.orxeira.expenseapp.core.ui.components.ExpenseTopAppBar
import gal.orxeira.expenseapp.core.ui.utils.koinViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpenseDetailScreen(
    expenseId: Int,
    navController: NavHostController,
    viewModel: ExpenseDetailViewModel = koinViewModel()
) {

    LaunchedEffect(expenseId) {
        viewModel.getExpenseDetails(expenseId)
    }

    val expense = viewModel.expense.collectAsState().value
    expense?.let {
        ExpenseDetailContent(
            expense = it,
            onClickNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetailContent(
    expense: Expense,
    onClickNavigateBack: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            ExpenseTopAppBar(
                hasBackNavigation = true,
                onClickNavigateBack = onClickNavigateBack,
                title = stringResource(Res.string.expense_detail),
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Date
            item {
                ExpenseDatePicker(
                    modifier = Modifier.padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    date = expense.date,
                    isEnabled = false
                )
            }
            //Amount
            item {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    val circleSize = maxWidth * 0.3f

                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.primary
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onPrimary,
                            text = "$${expense.amount}",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            maxLines = 1,
                        )
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillParentMaxHeight()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 0.dp
                            )
                        )
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                CategoryIcon(icon = expense.category.icon)
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically),
                                    text = expense.category.name,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        item {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.description),
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                                    fontSize = MaterialTheme.typography.titleSmall.fontSize
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(align = Alignment.Top, unbounded = false)
                                        .heightIn(min = 100.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = RoundedCornerShape(percent = 20)
                                        )

                                ) {
                                    Text(
                                        modifier = Modifier.padding(16.dp),
                                        text = expense.description.ifEmpty { stringResource(Res.string.default_description) },
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}