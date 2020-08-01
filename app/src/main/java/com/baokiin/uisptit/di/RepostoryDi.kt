package com.baokiin.uisptit.di

import com.baokiin.uisptit.data.repository.LoginRepository
import com.baokiin.uis.data.repository.login.LoginRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val loginRepositoryDi: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get(),get()) }
}