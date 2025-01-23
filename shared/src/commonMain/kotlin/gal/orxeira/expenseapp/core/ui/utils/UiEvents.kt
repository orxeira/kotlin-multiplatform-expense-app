package gal.orxeira.expenseapp.core.ui.utils

sealed class UiEvents {
    data class ShowSnackbar(val message: String) : UiEvents()
    data object NavigateBack : UiEvents()
}