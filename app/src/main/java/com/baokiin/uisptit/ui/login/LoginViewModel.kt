package com.baokiin.uisptit.ui.login
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.repository.DataRepository
import com.baokiin.uisptit.data.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(val useCase: LoginUseCase) : ViewModel() {
    val isLogin: MutableLiveData<Boolean?> by lazy {
        MutableLiveData<Boolean?>().apply {
            value = false
        }
    }


    fun login(loginInfor: LoginInfor) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                useCase.isLogin(loginInfor){
                    isLogin.postValue(it)
                }
            }catch (e: DataRepository.LoginException){
                isLogin.postValue(null)
            }
        }
    }
}