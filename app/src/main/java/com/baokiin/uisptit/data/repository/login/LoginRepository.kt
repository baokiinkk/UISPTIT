package com.baokiin.uis.data.repository.login

import com.baokiin.uisptit.data.db.model.Mark


interface LoginRepository {

    @Throws(LoginException::class)
    fun isLogin(loginInfor: LoginInfor,islogin:(Boolean)->Unit)
    fun getDataDiem(hk:String,getdata:(MutableList<Mark>)->Unit)
    class LoginException: Exception(){

    }
}