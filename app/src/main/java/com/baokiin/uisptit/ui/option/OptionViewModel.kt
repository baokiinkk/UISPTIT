package com.baokiin.uisptit.ui.option

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baokiin.uisptit.data.db.model.InfoUser
import com.baokiin.uisptit.data.usecase.OptionUseCase

class OptionViewModel(private val repo: OptionUseCase): ViewModel(){
    val data:MutableLiveData<InfoUser?> = MutableLiveData(null)
    fun deleteLogin(){
        repo.deleteLogin()
    }
    fun getData(){
        repo.getdata {
            data.postValue(it)
        }
    }
}