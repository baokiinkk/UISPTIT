package com.baokiin.uis.data.repository

import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.database.network.Network
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    var network: Network
    @Throws(LoginException::class)suspend fun getStatusLogin(loginInfor: LoginInfor) : Boolean
    suspend fun getMark(): MutableList<String>?

    class LoginException: Exception(){

    }
}