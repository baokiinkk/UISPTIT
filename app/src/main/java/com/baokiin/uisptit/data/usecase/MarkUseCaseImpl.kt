package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.data.repository.DataRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MarkUseCaseImpl(override val repo: DataRepository) : MarkUseCase {
    override fun getMark(hk: String, getdata: (MutableList<Mark>) -> Unit) {
        repo.getDataDiem(hk) {
            getdata(it)
        }
    }

    override fun postDatatoSQL() {
            repo.postMarkToSQl()
    }

}