package com.baokiin.uisptit.ui.examschedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.data.usecase.ExamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExamScheduleViewModel(private val exam: ExamUseCase): ViewModel() {
    val listExamTime:MutableLiveData<MutableList<ExamTimetable>?> = MutableLiveData(null)
    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            exam.getExam {
                listExamTime.postValue(it)
            }
        }
    }
}