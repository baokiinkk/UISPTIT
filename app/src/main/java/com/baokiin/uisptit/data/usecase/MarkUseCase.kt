package com.baokiin.uis.data.usecase

import com.baokiin.uis.data.repository.LoginRepository

interface MarkUseCase {
    var repo: LoginRepository
    suspend fun getMark(): MutableList<String>?
}
