package com.baokiin.uisptit.ui.mark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.data.usecase.MarkUseCase
import kotlinx.coroutines.launch

class MarkViewModel(private val markUseCase: MarkUseCase) : ViewModel() {
    val listData:MutableLiveData<MutableList<Mark>?> = MutableLiveData(null)


    fun getData(hk:String){
        viewModelScope.launch {
            markUseCase.getMark(hk){
                listData.postValue(it)
            }
        }
    }
    fun postDataToSQL(){
        viewModelScope.launch {
            markUseCase.postDatatoSQL()
        }
    }
}