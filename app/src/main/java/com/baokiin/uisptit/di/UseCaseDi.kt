package com.baokiin.uisptit.di

import com.baokiin.uisptit.data.usecase.*
import org.koin.core.module.Module
import org.koin.dsl.module


val markUseCaseDi: Module = module {
    single<MarkUseCase> { MarkUseCaseImpl(get()) }
}
val inforUseCaseDi: Module = module {
    single<InforUseCase> { InforUseCaseImpl(get()) }
}
val loginUseCase:Module = module {
    single<LoginUseCase> {LoginUseCaseImpl(get())}
}