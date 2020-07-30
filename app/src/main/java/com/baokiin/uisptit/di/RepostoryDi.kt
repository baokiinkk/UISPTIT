package com.baokiin.uis.di

import com.baokiin.uis.data.repository.LoginRepository
import com.baokiin.uis.data.repository.LoginRepositoryImpl
import com.baokiin.uis.data.repository.Repository
import org.koin.core.module.Module
import org.koin.dsl.module

val loginRepositoryDi: Module = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}