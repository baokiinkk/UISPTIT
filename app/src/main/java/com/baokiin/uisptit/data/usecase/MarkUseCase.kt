package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.repository.DataRepository

interface MarkUseCase {
    val repo : DataRepository
    fun getMark(hk:String,getdata:(MutableList<Mark>) -> Unit)
    fun postDatatoSQL()
}