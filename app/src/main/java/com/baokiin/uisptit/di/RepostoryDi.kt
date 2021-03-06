package com.baokiin.uisptit.di

import com.baokiin.uisptit.data.repository.DataRepository
import com.baokiin.uis.data.repository.login.DataRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val loginRepositoryDi: Module = module {
    single<DataRepository> { DataRepositoryImpl(get(),get()) }
}