package gal.orxeira.expenseapp.di


import gal.orxeira.expenseapp.feature.addexpense.AddExpenseViewModel
import gal.orxeira.expenseapp.feature.home.HomeViewModel
import gal.orxeira.expenseapp.feature.expensedetail.ExpenseDetailViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::HomeViewModel)
    singleOf(::AddExpenseViewModel)
    singleOf(::ExpenseDetailViewModel)
}