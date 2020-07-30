package com.baokiin.uis.data.repository

import android.util.Log
import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.database.network.Network
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LoginRepositoryImpl(override var network: Network) : LoginRepository {
    private  val list: MutableList<String>? by lazy {
        network.login(loginInfor)
    }
    private lateinit var loginInfor: LoginInfor

    @Throws(LoginRepository.LoginException::class)
    override suspend fun getStatusLogin(loginInfor: LoginInfor): Boolean = suspendCancellableCoroutine {
        this.loginInfor = loginInfor
        //list = network.login(loginInfor)
        if(list!!.isNotEmpty()){
            it.resume(true)
        }else{
            throw LoginRepository.LoginException()
        }
    }
    override suspend fun getMark(): MutableList<String>? = suspendCancellableCoroutine {
        it.resume(this.list)
    }
}