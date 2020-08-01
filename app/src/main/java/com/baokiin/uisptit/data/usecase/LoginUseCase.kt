package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.repository.DataRepository
import com.baokiin.uisptit.data.db.model.Mark


interface LoginUseCase {
    val repo : DataRepository
    @Throws(DataRepository.LoginException::class)
     fun isLogin(loginInfor: LoginInfor, islogin:(Boolean)->Unit)
    fun getMark(hk:String,getdata:(MutableList<Mark>) -> Unit)
}