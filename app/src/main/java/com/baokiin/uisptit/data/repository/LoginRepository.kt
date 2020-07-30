package com.baokiin.uis.data.repository

import com.baokiin.uis.data.database.domain.LoginInfor

interface LoginRepository {

    @Throws(LoginException::class)
    fun isLogin(loginInfor: LoginInfor,islogin:(Boolean)->Unit)

    class LoginException: Exception(){

    }
}