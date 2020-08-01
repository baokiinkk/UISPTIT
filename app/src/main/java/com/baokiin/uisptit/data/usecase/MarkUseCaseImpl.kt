package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.repository.LoginRepository

class MarkUseCaseImpl(override val repo: LoginRepository) : MarkUseCase {
    override fun getMark(hk: String, getdata: (MutableList<Mark>) -> Unit) {
        repo.getDataDiem(hk) {
            getdata(it)
        }
    }
}