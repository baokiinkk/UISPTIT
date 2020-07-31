package com.baokiin.uis.di

import com.baokiin.uis.data.repository.login.LoginInfor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import com.baokiin.uis.data.api.HttpUis
import org.koin.core.module.Module
import org.koin.dsl.module

val HttpUis: Module = module {
    single {HttpUis(androidApplication())}
}

val dataBase: Module = module {
    factory { LoginInfor("", "") }
}