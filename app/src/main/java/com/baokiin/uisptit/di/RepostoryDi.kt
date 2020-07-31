package com.baokiin.uis.di

import com.baokiin.uis.data.repository.login.LoginRepository
import com.baokiin.uis.data.repository.login.LoginRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val loginRepositoryDi: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get(),get()) }
}