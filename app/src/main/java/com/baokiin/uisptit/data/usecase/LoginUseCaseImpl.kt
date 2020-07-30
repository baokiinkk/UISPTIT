package com.baokiin.uis.data.usecase

import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.repository.LoginRepository

class LoginUseCaseImpl(override val repo: LoginRepository) : LoginUseCase {
    override suspend fun getStatusLogin(loginInfor: LoginInfor): Boolean = repo.getStatusLogin(loginInfor)
}