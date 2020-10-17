package com.baokiin.uisptit.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel(val usecase:LoginUseCase) : ViewModel() {
    val bool: MutableLiveData<String?> = MutableLiveData(null)
    fun check(name:String,pass:String){
        viewModelScope.launch(Dispatchers.IO) {
            usecase.isLogin(name, pass) {
                bool.postValue(it)
            }
        }
    }


}