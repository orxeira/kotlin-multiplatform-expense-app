package gal.orxeira.expenseapp.feature.addexpense


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.domain.model.ExpenseCategory
import gal.orxeira.expenseapp.core.domain.model.expenseCategories
import gal.orxeira.expenseapp.core.domain.repository.ExpenseRepository
import gal.orxeira.expenseapp.core.ui.utils.UiEvents
import gal.orxeira.expenseapp.core.ui.utils.today
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class AddExpenseViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _eventsFlow = Channel<UiEvents>(Channel.UNLIMITED)
    val eventsFlow = _eventsFlow.receiveAsFlow()

    private val _expenseDescription = MutableStateFlow("")
    val expenseDescription = _expenseDescription.asStateFlow()
    fun setExpenseDescription(description: String) {
        _expenseDescription.value = description
    }

    private val _selectedOption = MutableStateFlow<ExpenseCategory?>(null)
    val selectedOption = _selectedOption.asStateFlow()
    fun setSelectedOption(option: ExpenseCategory?) {
        _selectedOption.value = option
    }

    private val _expenseDate = MutableStateFlow(today().date)
    val expenseDate = _expenseDate.asStateFlow()
    fun setExpenseDate(date: LocalDate) {
        _expenseDate.value = date
    }

    private val _expenseAmount = MutableStateFlow("")
    val expenseAmount = _expenseAmount.asStateFlow()
    fun setExpenseAmount(amount: String) {
        _expenseAmount.value = amount
    }

    private fun reset() {
        setExpenseAmount("")
        setExpenseDate(today().date)
        setExpenseDescription("")
        setSelectedOption(null)
    }

    fun buildAndAddExpense(
        description: String,
        amount: String?,
        date: LocalDate,
        category: ExpenseCategory?,
        successMessage: String

    ) {
        if (amount.isNullOrEmpty() || amount.toDoubleOrNull() == null) {
            //is not valid
            //TODO: Set error state
        } else {
            val expense = Expense(
                description = description,
                amount = amount.toDouble(),
                date = date,
                category = category ?: expenseCategories.first()
            )

            viewModelScope.launch {
                expenseRepository.addExpense(expense)
                reset()
                _eventsFlow.trySend(UiEvents.ShowSnackbar(successMessage))
            }
        }
    }
}