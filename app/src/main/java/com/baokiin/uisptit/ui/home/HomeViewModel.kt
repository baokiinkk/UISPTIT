package com.baokiin.uisptit.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    val markLiveData: MutableLiveData<MutableList<String>?> by lazy {
        MutableLiveData<MutableList<String>?>().also {
            mutableListOf<String>()
        }
    }

    fun getMark(){
        viewModelScope.launch(Dispatchers.IO) {
            //markLiveData.postValue(markUseCase.getMark())
        }
    }
}