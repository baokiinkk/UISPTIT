package com.baokiin.uisptit.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.usecase.InforUseCase
import com.baokiin.uisptit.data.usecase.InforUseCaseImpl
import com.baokiin.uisptit.data.usecase.MarkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoViewModel(private val inforUseCase: InforUseCase) : ViewModel() {
    val listData:MutableLiveData<MutableList<Mark>?> = MutableLiveData(null)


    fun getData(hk:String){
        viewModelScope.launch {
            inforUseCase.getMark(hk){
                listData.postValue(it)
            }
        }
    }
}