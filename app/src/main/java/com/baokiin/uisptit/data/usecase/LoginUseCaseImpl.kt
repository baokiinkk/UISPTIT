package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.repository.DataRepository

class LoginUseCaseImpl(override val repo: DataRepository) :LoginUseCase {
    override fun isLogin(name: String, pass: String, islogin: (Boolean) -> Unit) {
        repo.isLogin(name,pass){
            if(it == true)
                repo.addLogin(name,pass)
            islogin(it)
        }
    }

    override fun getLogin(bool: (Boolean) -> Unit) {
        repo.getLogin {
            if(it.size == 1)
                    bool(true)
            else bool(false)
        }
    }

    override fun deleteLogin() {
        repo.deleteLogin()
    }
}