package com.baokiin.uisptit.ui.examschedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.data.usecase.ExamUseCase

class ExamScheduleViewModel(private val exam: ExamUseCase): ViewModel() {
    val listExamTime:MutableLiveData<MutableList<ExamTimetable>?> = MutableLiveData(null)
    fun getData(){
        exam.getExam {
            listExamTime.postValue(it)
        }
    }
}