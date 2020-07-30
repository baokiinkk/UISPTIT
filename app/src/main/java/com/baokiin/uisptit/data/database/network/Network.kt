package com.baokiin.uis.data.database.network

import android.content.Context
import com.baokiin.uis.data.database.domain.LoginInfor
import kotlinx.coroutines.flow.Flow

interface Network {
    var context: Context
    fun login(loginInfor: LoginInfor) : MutableList<String>
    suspend fun getMark(): MutableList<String>
}