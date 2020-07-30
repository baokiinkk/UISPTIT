package com.baokiin.uis.ui.login
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.data.usecase.LoginUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(val useCase: LoginUseCase) : ViewModel() {
    var onclicks: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isLogin: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().apply {
            value = false
        }
    }
    fun listener(){
        onclicks.postValue(true)
    }

    fun login(loginInfor: LoginInfor) {
        viewModelScope.launch(Dispatchers.IO) {
            isLogin.postValue(useCase.getStatusLogin(loginInfor))
        }
    }
}