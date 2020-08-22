package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.repository.DataRepository

interface LoginUseCase {

    val repo : DataRepository
    fun isLogin(name:String,pass:String,islogin:(Boolean)->Unit)
    fun getLogin(bool:(Boolean)->Unit)
    fun deleteLogin()
}