package com.baokiin.uis.data.repository.login


interface LoginRepository {

    @Throws(LoginException::class)
    fun isLogin(loginInfor: LoginInfor,islogin:(Boolean)->Unit)

    class LoginException: Exception(){

    }
}