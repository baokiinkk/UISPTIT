package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.repository.DataRepository

class LoginUseCaseImpl(override val repo: DataRepository) :LoginUseCase {
    suspend override fun isLogin(name: String, pass: String, islogin:suspend(Boolean) -> Unit) {

        repo.isLogin(name,pass){
            if(it)
                repo.addLogin(name,pass)
            islogin(it)
        }
    }

    suspend override fun getLogin(bool: suspend (Boolean) -> Unit) {
        repo.getLogin {
            if(it.size == 1)
                    bool(true)
            else bool(false)
        }
    }

    suspend override fun deleteLogin() {
        repo.deleteLogin()
    }
}