package gal.orxeira.expenseapp.core.data.adapter

import app.cash.sqldelight.ColumnAdapter

val idAdapter = object : ColumnAdapter<Int, Long> {
    override fun decode(databaseValue: Long): Int {
        return databaseValue.toInt()
    }

    override fun encode(value: Int): Long {
        return value.toLong()
    }
}