package com.baokiin.uis.data.usecase

import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.repository.LoginRepository
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.usecase.LoginUseCase

class LoginUseCaseImpl(override val repo: LoginRepository) : LoginUseCase {
    @Throws(LoginRepository.LoginException::class)
    override  fun isLogin(loginInfor: LoginInfor, islogin:(Boolean)->Unit){
        repo.isLogin(loginInfor){
                islogin(it)
        }
    }

    override fun getMark(hk:String,getdata: (MutableList<Mark>) -> Unit) {
        repo.getDataDiem(hk) {
            getdata(it)
        }
    }

}