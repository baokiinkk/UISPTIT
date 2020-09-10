package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.data.repository.DataRepository

interface ExamUseCase {
    val repo : DataRepository
    suspend fun getExam(getdata:(MutableList<ExamTimetable>) -> Unit)
}