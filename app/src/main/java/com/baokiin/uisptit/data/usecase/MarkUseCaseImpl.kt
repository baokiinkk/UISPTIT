package com.baokiin.uis.data.usecase

import com.baokiin.uis.data.repository.LoginRepository

class MarkUseCaseImpl(override var repo: LoginRepository) : MarkUseCase {
    override suspend fun getMark(): MutableList<String>? = repo.getMark()
}