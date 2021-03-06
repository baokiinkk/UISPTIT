package com.baokiin.uisptit.data.usecase

import com.baokiin.uisptit.data.db.model.TimeTable
import com.baokiin.uisptit.data.repository.DataRepository

class TimeTableUseCaseImpl(override val repo: DataRepository) :TimeTableUseCase{
    override suspend fun getTimeTable(getdata: (MutableList<TimeTable>) ->Unit) {
        repo.getTimeTable {
            getdata(it)
        }
    }
}