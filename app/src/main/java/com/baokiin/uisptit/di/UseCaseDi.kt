package com.baokiin.uisptit.di

import com.baokiin.uisptit.data.usecase.LoginUseCase
import com.baokiin.uis.data.usecase.LoginUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val loginUseCaseDi: Module = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
}
