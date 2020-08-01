package com.baokiin.uisptit.di

import com.baokiin.uisptit.ui.info.InfoViewModel
import com.baokiin.uisptit.ui.login.LoginViewModel
import com.baokiin.uisptit.ui.mark.MarkViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// File này ta sẽ tạo ra các module của tầng ViewModel

val loginViewModelDi: Module = module {
    viewModel{ LoginViewModel(get()) }
}

val inforViewModelDi: Module = module {
    viewModel { InfoViewModel() }
}

val markViewModelDi: Module = module {
    viewModel { MarkViewModel(get()) }
}