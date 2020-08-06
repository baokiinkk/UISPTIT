package com.baokiin.uisptit.data.repository

import com.baokiin.uisptit.data.db.model.InfoUser
import com.baokiin.uisptit.data.db.model.LoginInfor
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark


interface DataRepository {

    @Throws(LoginException::class)
    fun isLogin(name:String,pass:String, islogin:(Boolean)->Unit)
    fun getDataDiem(hk:String,getdata:(MutableList<Mark>)->Unit)
    fun getDataSemester(hk:String,getdata:(MutableList<SemesterMark>)->Unit)
    fun getInforUser(data:(InfoUser)->Unit)
    fun getLogin(data: (MutableList<LoginInfor>) -> Unit)
    fun addLogin(name:String,pass:String)
    fun deleteLogin()
    class LoginException: Exception(){

    }
}