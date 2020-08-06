package com.baokiin.uisptit.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.usecase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(val usecase:LoginUseCase) : ViewModel() {
    val bool:MutableLiveData<Boolean?> = MutableLiveData(null)
    val isUser:MutableLiveData<Boolean?> = MutableLiveData(null)
    fun check(name:String,pass:String){
            usecase.isLogin(name,pass){
                bool.postValue(it)
            }

    }
    fun isUser(){

            usecase.getLogin {
                isUser.postValue(it)
            }

    }

}