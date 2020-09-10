package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.repository.DataRepository

interface LoginUseCase {

    val repo : DataRepository
    suspend fun isLogin(name:String,pass:String,islogin: suspend (Boolean)->Unit)
    suspend fun getLogin(bool:suspend (Boolean)->Unit)
    suspend fun deleteLogin()
}