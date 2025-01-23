package gal.orxeira.expenseapp.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gal.orxeira.expenseapp.core.domain.model.Expense
import gal.orxeira.expenseapp.core.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    val expenses = expenseRepository.getExpenses()
        .map { expenses ->
            ExpenseState.Success(
                expenses = expenses
                    .sortedBy { it.date },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExpenseState.Loading,
        )

    private val _showConfirmDeleteDialog = MutableStateFlow<Expense?>(null)
    val showConfirmDeleteDialog = _showConfirmDeleteDialog.asStateFlow()
    fun setShowConfirmDeleteDialog(expense: Expense) {
        _showConfirmDeleteDialog.value = expense
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense.id)
            closeConfirmDeleteDialog()
        }
    }

    fun closeConfirmDeleteDialog(){
        _showConfirmDeleteDialog.value = null
    }
}

sealed class ExpenseState {
    data object Loading : ExpenseState()
    data class Success(
        val expenses: List<Expense>,
    ) : ExpenseState()
}