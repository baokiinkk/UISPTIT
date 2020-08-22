package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.InfoUser
import com.baokiin.uisptit.data.repository.DataRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OptionUsecaseImpl(override val repo: DataRepository):OptionUseCase{
    override fun getdata(getdata: (InfoUser) -> Unit) {
        GlobalScope.launch {
            repo.getInforUser {
                getdata(it)
            }
        }
    }

    override fun deleteLogin() {
        GlobalScope.launch {
            repo.deleteLogin()
        }
    }

}
