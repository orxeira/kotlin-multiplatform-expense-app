package gal.orxeira.expenseapp.feature.addexpense

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import expenseapp.shared.generated.resources.Res
import expenseapp.shared.generated.resources.add_expense
import expenseapp.shared.generated.resources.amount
import expenseapp.shared.generated.resources.category
import expenseapp.shared.generated.resources.content_description_expense_added
import expenseapp.shared.generated.resources.currency
import expenseapp.shared.generated.resources.description
import expenseapp.shared.generated.resources.enter_amount
import expenseapp.shared.generated.resources.enter_description
import expenseapp.shared.generated.resources.expense_added
import expenseapp.shared.generated.resources.ic_complete
import expenseapp.shared.generated.resources.ic_dollar
import expenseapp.shared.generated.resources.select_category
import gal.orxeira.expenseapp.core.domain.model.ExpenseCategory
import gal.orxeira.expenseapp.core.domain.model.TextFieldState
import gal.orxeira.expenseapp.core.domain.model.expenseCategories
import gal.orxeira.expenseapp.core.ui.components.ExpenseDatePicker
import gal.orxeira.expenseapp.core.ui.components.ExpenseTopAppBar
import gal.orxeira.expenseapp.core.ui.utils.UiEvents
import gal.orxeira.expenseapp.core.ui.utils.koinViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddExpenseScreen(
    navController: NavController,
    viewModel: AddExpenseViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val expenseDescription = viewModel.expenseDescription.collectAsState().value
    val expenseDate = viewModel.expenseDate.collectAsState().value
    val expenseAmount = viewModel.expenseAmount.collectAsState().value
    val selectedExpenseCategory = viewModel.selectedOption.collectAsState().value

    LaunchedEffect(key1 = Unit) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.eventsFlow.collect { event ->
                when (event) {
                    is UiEvents.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                        )
                    }

                    UiEvents.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    val successMessage = stringResource(Res.string.expense_added)

    AddExpenseScreenContent(
        snackbarHostState = snackbarHostState,
        onDateSelected = { localDate ->
            viewModel.setExpenseDate(localDate)
        },
        onClickNavigateBack = {
            navController.popBackStack()
        },
        onAmountChanged = {
            viewModel.setExpenseAmount(it)
        },
        onExpenseDescriptionChanged = {
            viewModel.setExpenseDescription(it)
        },
        onExpenseCategoryChanged = {
            viewModel.setSelectedOption(it)
        },
        amount = expenseAmount,
        description = expenseDescription,
        selectedCategory = selectedExpenseCategory,
        onClickAddExpense = {
            keyboardController?.hide()
            viewModel.buildAndAddExpense(
                description = expenseDescription,
                amount = expenseAmount,
                date = expenseDate,
                category = selectedExpenseCategory,
                successMessage = successMessage

            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreenContent(
    snackbarHostState: SnackbarHostState,
    onDateSelected: (LocalDate) -> Unit,
    onClickNavigateBack: () -> Unit,
    onAmountChanged: (String) -> Unit,
    onExpenseDescriptionChanged: (String) -> Unit,
    onExpenseCategoryChanged: (ExpenseCategory) -> Unit,
    amount: String,
    description: String,
    selectedCategory: ExpenseCategory?,
    onClickAddExpense: () -> Unit

) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                },
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                            ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(.85f),
                                    text = it.visuals.message,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                )
                                Image(
                                    modifier = Modifier
                                        .size(32.dp),
                                    painter = painterResource(
                                        Res.drawable.ic_complete
                                    ),
                                    contentDescription = stringResource(Res.string.content_description_expense_added),
                                )
                            }
                        }
                    },
                )
            }
        },
        topBar = {
            ExpenseTopAppBar(
                hasBackNavigation = true,
                onClickNavigateBack = onClickNavigateBack,
                title = stringResource(Res.string.add_expense),
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    ExpenseDatePicker(
                        onDateSelected = {
                            onDateSelected.invoke(it)
                        }
                    )
                }

                item {
                    ExpenseInputTextField(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        label = {
                            Text(
                                text = stringResource(Res.string.description),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                ),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        value = TextFieldState(text = description),
                        onValueChange = onExpenseDescriptionChanged,
                        placeholder = {
                            Text(
                                text = stringResource(Res.string.enter_description),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences,
                        ),
                        textStyle = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                        ),
                    )
                }

                item {
                    ExpenseInputTextField(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        label = {
                            Text(
                                text = stringResource(Res.string.amount),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                ),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.ic_dollar),
                                tint = MaterialTheme.colorScheme.onBackground,
                                contentDescription = stringResource(Res.string.currency)
                            )
                        },
                        value = TextFieldState(text = amount),
                        onValueChange = { newText ->
                            if (newText.matches(regex = "^\\d*(\\.\\d{0,2})?\$".toRegex())) {
                                onAmountChanged(newText)
                            }
                        },
                        isError = amount.isNotEmpty() && amount.toDoubleOrNull() == null,
                        placeholder = {
                            Text(
                                text = stringResource(Res.string.enter_amount),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Decimal
                        ),
                        textStyle = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 16.sp,
                        ),
                    )
                }

                item {
                    CategoryDialog(
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(
                                text = stringResource(Res.string.category),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                ),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        categories = expenseCategories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = onExpenseCategoryChanged
                    )
                }
            }
            Button(
                onClick = onClickAddExpense,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                shape = RoundedCornerShape(percent = 15),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 16.dp,
                    focusedElevation = 12.dp
                )
            ) {
                Text(stringResource(Res.string.add_expense))
            }

        }
    }
}

@Composable
fun ExpenseInputTextField(
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    value: TextFieldState,
    maxLines: Int = 1,
    editable: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    shape: CornerBasedShape = MaterialTheme.shapes.large,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false
) {
    Column(
        modifier = modifier,
    ) {
        if (label != null) {
            label()
            Spacer(modifier = Modifier.height(4.dp))
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .defaultMinSize(minWidth = 32.dp),
            colors = TextFieldDefaults.colors().copy(
                focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground
            ),
            value = value.text,
            onValueChange = onValueChange,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = textStyle,
            shape = shape,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            keyboardOptions = keyboardOptions,
            readOnly = !editable,
            isError = isError
        )
        if (!value.error.isNullOrEmpty()) {
            Text(
                text = value.error,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.error,
                ),
            )
        }
    }
}

@Composable
fun <T> CategoryDialog(
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    categories: List<T>,
    selectedCategory: T?,
    onCategorySelected: (T) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        if (label != null) {
            label()
            Spacer(modifier = Modifier.height(4.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minWidth = 32.dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(percent = 20)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(percent = 20)
                )
                .clickable { isDialogOpen = true }
                .padding(16.dp)
        ) {
            Text(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                text = selectedCategory?.toString() ?: stringResource(Res.string.select_category),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDialogOpen = false },
                title = { Text(text = stringResource(Res.string.select_category)) },
                text = {
                    LazyColumn {
                        items(categories) { category ->
                            Text(
                                text = category.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onCategorySelected(category)
                                        isDialogOpen = false
                                    }
                                    .padding(16.dp)
                            )
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {}
            )
        }
    }
}

