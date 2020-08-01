package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.repository.LoginRepository

interface MarkUseCase {
    val repo : LoginRepository
    fun getMark(hk:String,getdata:(MutableList<Mark>) -> Unit)
}