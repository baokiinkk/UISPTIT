package com.baokiin.uis.data.usecase

import com.baokiin.uis.data.repository.login.LoginInfor
import com.baokiin.uis.data.repository.login.LoginRepository
import com.baokiin.uisptit.data.usecase.LoginUseCase

class LoginUseCaseImpl(override val repo: LoginRepository) : LoginUseCase {
    @Throws(LoginRepository.LoginException::class)
    override  fun isLogin(loginInfor: LoginInfor, islogin:(Boolean)->Unit){
        repo.isLogin(loginInfor){
                islogin(it)
        }
    }

}