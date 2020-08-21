package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.TimeTable
import com.baokiin.uisptit.data.repository.DataRepository

interface TimeTableUseCase {
    val repo : DataRepository
    fun getTimeTable(getdata:(MutableList<TimeTable>) -> Unit)
}