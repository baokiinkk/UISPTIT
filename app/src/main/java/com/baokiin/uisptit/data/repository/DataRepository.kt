package com.baokiin.uisptit.data.repository

import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark


interface DataRepository {

    @Throws(LoginException::class)
    fun isLogin(loginInfor: LoginInfor, islogin:(Boolean)->Unit)
    fun getDataDiem(hk:String,getdata:(MutableList<Mark>)->Unit)
    fun getDataSemester(hk:String,getdata:(MutableList<SemesterMark>)->Unit)
    fun postMarkToSQl()
    class LoginException: Exception(){

    }
}