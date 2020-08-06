package com.baokiin.uisptit

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.baokiin.uis.di.HttpUis
import com.baokiin.uis.di.appdaoModule
import com.baokiin.uis.di.dataBase
import com.baokiin.uisptit.di.*
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
                appdaoModule,
                markUseCaseDi,
                markViewModelDi,
                loginRepositoryDi,
                inforViewModelDi,
                loginModuleDi,
                loginUseCase,
                optionViewModelDi
            ))
        }
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}