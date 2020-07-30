package com.baokiin.uis.di

import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.database.network.HttpUis
import com.baokiin.uis.data.database.network.Network
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val HttpUis: Module = module {
    single<Network> { HttpUis(androidContext()) }
}

val dataBase: Module = module {
    factory { LoginInfor("", "") }
}