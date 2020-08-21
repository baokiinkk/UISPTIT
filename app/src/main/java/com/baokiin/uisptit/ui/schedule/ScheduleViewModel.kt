package com.baokiin.uisptit.ui.schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baokiin.uisptit.data.db.model.TimeTable
import com.baokiin.uisptit.data.usecase.TimeTableUseCase

class ScheduleViewModel(private val timeUseCase: TimeTableUseCase):ViewModel(){
    val data:MutableLiveData<MutableList<TimeTable> ?> = MutableLiveData(null)

    fun getData(){
        timeUseCase.getTimeTable {
            data.postValue(it)
        }
    }
}