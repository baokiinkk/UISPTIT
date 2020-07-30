package com.baokiin.uisptit.data.usecase

import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.repository.LoginRepository

interface LoginUseCase {
    val repo : LoginRepository
    @Throws(LoginRepository.LoginException::class)
     fun isLogin(loginInfor: LoginInfor,islogin:(Boolean)->Unit)
}