package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.data.repository.DataRepository

interface MarkUseCase {
    val repo : DataRepository
    suspend fun getMark(hk:String,getdata:(MutableList<Mark>) -> Unit)
    suspend fun getDataSemester(hk: String,getdata:(MutableList<SemesterMark>)->Unit)
}