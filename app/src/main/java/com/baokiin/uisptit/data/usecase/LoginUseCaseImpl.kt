package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.repository.DataRepository

class LoginUseCaseImpl(override val repo: DataRepository) :LoginUseCase {
    override suspend fun isLogin(name: String, pass: String, islogin: suspend (String) -> Unit) {

        repo.isLogin(name, pass) {
            if (it.equals(""))
                repo.addLogin(name, pass)

            islogin(it)
        }
    }

    override suspend fun getLogin(bool: suspend (Boolean) -> Unit) {
        repo.getLogin {
            if (it.size == 1)
                bool(true)
            else bool(false)
        }
    }

    override suspend fun deleteLogin() {
        repo.deleteLogin()
    }
}