package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.repository.DataRepository

class MarkUseCaseImpl(override val repo: DataRepository) : MarkUseCase {
    override fun getMark(hk: String, getdata: (MutableList<Mark>) -> Unit) {
        repo.getDataDiem(hk) {
            getdata(it)
        }
    }


}