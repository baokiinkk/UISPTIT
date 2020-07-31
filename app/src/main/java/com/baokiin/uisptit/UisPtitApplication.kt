package com.baokiin.uisptit

import android.app.Application
import com.baokiin.uis.di.*
import com.baokiin.uisptit.di.loginUseCaseDi
import com.baokiin.uisptit.di.loginViewModelDi
import com.baokiin.uisptit.di.markUseCaseDi
import com.baokiin.uisptit.di.markViewModelDi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UisPtitApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UisPtitApplication)
            modules(listOf(
                HttpUis,
                dataBase,
                loginRepositoryDi,
                loginUseCaseDi,
                markUseCaseDi,
                markViewModelDi,
                loginViewModelDi
            ))
        }
    }
}