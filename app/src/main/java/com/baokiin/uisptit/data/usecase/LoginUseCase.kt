package com.baokiin.uisptit.data.usecase

import com.baokiin.uis.data.repository.login.LoginInfor
import com.baokiin.uis.data.repository.login.LoginRepository
import com.baokiin.uisptit.data.db.model.Mark


interface LoginUseCase {
    val repo : LoginRepository
    @Throws(LoginRepository.LoginException::class)
     fun isLogin(loginInfor: LoginInfor, islogin:(Boolean)->Unit)
    fun getMark(hk:String,getdata:(MutableList<Mark>) -> Unit)
}