package gal.orxeira.expenseapp.feature.expensedetail


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExpenseDetailViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _expense = MutableStateFlow<Expense?>(null)
    val expense = _expense.asStateFlow()
    fun getExpenseDetails(expenseId: Int) {
        viewModelScope.launch {
            expenseRepository.getExpense(expenseId).collectLatest {
                _expense.value = it
            }
        }
    }
}