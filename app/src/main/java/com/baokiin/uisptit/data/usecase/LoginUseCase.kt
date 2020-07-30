package com.baokiin.uis.data.usecase

import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.repository.LoginRepository

interface LoginUseCase {
    val repo : LoginRepository
    suspend fun getStatusLogin(loginInfor: LoginInfor): Boolean
}