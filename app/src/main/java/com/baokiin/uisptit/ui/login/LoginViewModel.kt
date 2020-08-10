package com.baokiin.uisptit.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baokiin.uisptit.data.usecase.LoginUseCase


class LoginViewModel(val usecase:LoginUseCase) : ViewModel() {
    val bool:MutableLiveData<Boolean?> = MutableLiveData(null)
    val style:MutableLiveData<String> = MutableLiveData("@style/SpinKitView.ChasingDots")
    fun check(name:String,pass:String){
            usecase.isLogin(name,pass){
                bool.postValue(it)
            }

    }


}