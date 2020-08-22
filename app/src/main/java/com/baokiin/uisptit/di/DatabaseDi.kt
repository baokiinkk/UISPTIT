package com.baokiin.uis.di

import com.baokiin.uisptit.data.db.model.LoginInfor
import org.koin.android.ext.koin.androidApplication
import com.baokiin.uis.data.api.HttpUis
import com.baokiin.uisptit.data.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val HttpUis: Module = module {
    single {HttpUis(androidApplication())}
}

val dataBase: Module = module {
    factory { LoginInfor("", "") }
}
val appdaoModule= module {
    single { AppDatabase.getInstance(androidApplication()).appDao()}
}