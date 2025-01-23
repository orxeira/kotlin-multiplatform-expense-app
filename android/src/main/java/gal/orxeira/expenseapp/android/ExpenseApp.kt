package gal.orxeira.expenseapp.android

import android.app.Application
import gal.orxeira.expenseapp.android.di.androidModule
import gal.orxeira.expenseapp.di.KoinInit
import gal.orxeira.expenseapp.shared.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class ExpenseApp: Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInit().init{
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@ExpenseApp)
            modules(
                listOf(
                    androidModule,
                ),
            )
        }
    }
}