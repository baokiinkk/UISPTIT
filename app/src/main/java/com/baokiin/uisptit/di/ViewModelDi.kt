package com.baokiin.uisptit.di

import com.baokiin.uisptit.ui.home.HomeViewModel
import com.baokiin.uisptit.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

// File này ta sẽ tạo ra các module của tầng ViewModel

val loginViewModelDi: Module = module {
    viewModel{ LoginViewModel(get()) }
}

val markViewModelDi: Module = module {
    viewModel { HomeViewModel() }
}