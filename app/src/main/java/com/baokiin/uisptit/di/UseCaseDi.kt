package com.baokiin.uisptit.di

import com.baokiin.uis.data.usecase.LoginUseCaseImpl
import com.baokiin.uisptit.data.usecase.*
import org.koin.core.module.Module
import org.koin.dsl.module

val loginUseCaseDi: Module = module {
    single<LoginUseCase> { LoginUseCaseImpl(get()) }
}

val markUseCaseDi: Module = module {
    single<MarkUseCase> { MarkUseCaseImpl(get()) }
}
val inforUseCaseDi: Module = module {
    single<InforUseCase> { InforUseCaseImpl(get()) }
}