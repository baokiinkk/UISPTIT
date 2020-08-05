package com.baokiin.uisptit.ui.hello

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.AppDao
import kotlinx.coroutines.launch

class HelloViewModel (val dao:AppDao): ViewModel() {
        val isCheck:MutableLiveData<Boolean?> = MutableLiveData(null)
    fun check(){
        viewModelScope.launch {
            val login = dao.getLogin()
            if(login.size == 1){
                isCheck.postValue(true)
            }
            else isCheck.postValue(false)
        }
    }
}