package com.baokiin.uisptit.data.repository

import com.baokiin.uisptit.data.db.model.*


interface DataRepository {

    @Throws(LoginException::class)
    fun isLogin(name:String,pass:String, islogin:(Boolean)->Unit)
    fun getDataDiem(hk:String,getdata:(MutableList<Mark>)->Unit)
    fun getCNTAA(data:(Int)->Unit)
    fun getDataSemester(hk: String,getdata:(MutableList<SemesterMark>)->Unit)
    fun getExam(data: (MutableList<ExamTimetable>) -> Unit)
    fun getInforUser(data:(InfoUser)->Unit)
    fun getLogin(data: (MutableList<LoginInfor>) -> Unit)
    fun getTimeTable(data:(MutableList<TimeTable>)->Unit)
    fun addLogin(name:String,pass:String)
    fun deleteLogin()
    class LoginException: Exception(){

    }
}