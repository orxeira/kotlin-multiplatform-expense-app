package gal.orxeira.expenseapp.di

import database.ExpenseEntity
import gal.orxeira.expenseapp.core.data.adapter.idAdapter
import gal.orxeira.expenseapp.core.data.repository.ExpenseRepositoryImpl
import gal.orxeira.expenseapp.core.domain.repository.ExpenseRepository
import gal.orxeira.expenseapp.database.ExpenseDatabase
import gal.orxeira.expenseapp.platform.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

fun commonModule() = module {
    /**
     * Database
     */
    single<ExpenseDatabase> {
        ExpenseDatabase(
            driver = get<DatabaseDriverFactory>().createDriver(),
            expenseEntityAdapter = ExpenseEntity.Adapter(
                idAdapter = idAdapter
            ),
        )
    }

    /**
     * Repositories
     */
    single<ExpenseRepository> {
        ExpenseRepositoryImpl(
            database = get(),
        )
    }
}

expect fun platformModule(): Module