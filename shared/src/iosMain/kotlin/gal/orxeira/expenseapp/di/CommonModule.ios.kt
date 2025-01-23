package gal.orxeira.expenseapp.di

import gal.orxeira.expenseapp.platform.DatabaseDriverFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DatabaseDriverFactory() }
}