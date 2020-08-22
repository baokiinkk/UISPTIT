package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.InfoUser
import com.baokiin.uisptit.data.repository.DataRepository

interface OptionUseCase {
    val repo : DataRepository
    fun getdata(getdata:(InfoUser) -> Unit)
    fun deleteLogin()
}