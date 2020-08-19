package com.baokiin.uisptit.data.usecase


import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.data.repository.DataRepository

class ExamUseCaseImpl(override val repo: DataRepository) :ExamUseCase
{
    override fun getExam(getdata: (MutableList<ExamTimetable>) -> Unit) {
        repo.getExam {
            getdata(it)
        }
    }
}