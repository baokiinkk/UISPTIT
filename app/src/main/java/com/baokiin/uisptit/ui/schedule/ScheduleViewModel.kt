package com.baokiin.uisptit.ui.schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.model.TimeTable
import com.baokiin.uisptit.data.usecase.TimeTableUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(private val timeUseCase: TimeTableUseCase):ViewModel(){
    val data:MutableLiveData<MutableList<TimeTable> ?> = MutableLiveData(null)

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            timeUseCase.getTimeTable {
                data.postValue(it)
            }
        }
    }
}