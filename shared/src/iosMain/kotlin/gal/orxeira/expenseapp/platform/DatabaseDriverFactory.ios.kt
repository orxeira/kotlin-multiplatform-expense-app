package gal.orxeira.expenseapp.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import gal.orxeira.expenseapp.database.ExpenseDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(schema = ExpenseDatabase.Schema, name = "expense_app.db")
    }
}