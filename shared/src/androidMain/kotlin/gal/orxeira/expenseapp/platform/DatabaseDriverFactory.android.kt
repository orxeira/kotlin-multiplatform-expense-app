package gal.orxeira.expenseapp.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import gal.orxeira.expenseapp.database.ExpenseDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(ExpenseDatabase.Schema, context, "expense_app.db")
    }
}