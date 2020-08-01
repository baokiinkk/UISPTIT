package com.baokiin.uisptit.di

import com.baokiin.uisptit.data.usecase.LoginUseCase
import com.baokiin.uis.data.usecase.LoginUseCaseImpl
import com.baokiin.uisptit.data.usecase.MarkUseCase
import com.baokiin.uisptit.data.usecase.MarkUseCaseImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val loginUseCaseDi: Module = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
}

val markUseCaseDi: Module = module {
    single<MarkUseCase> { MarkUseCaseImpl(get()) }
}