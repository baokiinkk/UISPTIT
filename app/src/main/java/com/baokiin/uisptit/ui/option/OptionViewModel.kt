package com.baokiin.uisptit.ui.option

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.model.InfoUser
import com.baokiin.uisptit.data.usecase.OptionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OptionViewModel(private val repo: OptionUseCase): ViewModel(){
    val data:MutableLiveData<InfoUser?> = MutableLiveData(null)
    fun deleteLogin(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteLogin()
        }
    }
    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getdata {
                data.postValue(it)
            }
        }
    }
}