package com.baokiin.uis.data.repository

import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.database.network.HttpUis
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.security.auth.login.LoginException
import kotlin.coroutines.resume

class LoginRepositoryImpl( var network: HttpUis) : LoginRepository {
    private  var list: MutableList<String>? = null
   // private lateinit var loginInfor: LoginInfor



    @Throws(LoginRepository.LoginException::class)
    override fun isLogin(loginInfor: LoginInfor, islogin:(Boolean)->Unit) {
        this.list = network.login(loginInfor)
        if(list!!.isNotEmpty()){
            islogin(true)
        }else {
            throw LoginRepository.LoginException()
        }
    }


}