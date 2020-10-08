package com.baokiin.uisptit.data.repository

import com.baokiin.uisptit.data.db.model.*


interface DataRepository {

    @Throws(LoginException::class)
    suspend fun isLogin(name:String,pass:String, islogin: suspend (Boolean)->Unit)
    suspend fun getDataDiem(hk:String,getdata:(MutableList<Mark>)->Unit)
    suspend fun getCNTAA(data:(Int)->Unit)
    suspend fun getDataSemester(hk: String,getdata:(MutableList<SemesterMark>)->Unit)
    suspend fun getExam(data: (MutableList<ExamTimetable>) -> Unit)
    suspend fun getInforUser(data:(InfoUser)->Unit)
    suspend fun getLogin(data: suspend (MutableList<LoginInfor>) -> Unit)
    suspend fun getTimeTable(data:(MutableList<TimeTable>)->Unit)
    suspend fun addLogin(name:String,pass:String)
    suspend fun deleteLogin()
    class LoginException : Exception()
}