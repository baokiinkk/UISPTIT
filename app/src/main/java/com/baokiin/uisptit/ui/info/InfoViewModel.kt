package com.baokiin.uisptit.ui.info

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.model.*
import com.baokiin.uisptit.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InfoViewModel(private val repo: DataRepository) : ViewModel() {
    val listData:MutableLiveData<MutableList<Mark>?> = MutableLiveData(null)
    val title:MutableLiveData<String?> = MutableLiveData(null)
    val bool:MutableLiveData<Boolean?> = MutableLiveData(null)
    val login:MutableLiveData<LoginInfor?> = MutableLiveData(null)
    val listSemester:MutableLiveData<MutableList<SemesterMark>?> = MutableLiveData(null)
    val cntaa:MutableLiveData<Int> = MutableLiveData(0)
    val listExam:MutableLiveData<MutableList<ExamTimetable>?> = MutableLiveData(null)
    val dataTimeTableTime:MutableLiveData<MutableList<TimeTable> ?> = MutableLiveData(null)
    init {
        getData()
    }
    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getDataDiem("") {
                listData.postValue(it)
            }
            repo.getInforUser {
                title.postValue(xuLiTen(it.ten))
            }
            repo.getDataSemester("") {
                listSemester.postValue(it)
            }
            repo.getCNTAA(cntaa::postValue)
            repo.getExam {
                listExam.postValue(it)
            }
            repo.getTimeTable {
                dataTimeTableTime.postValue(it)
            }
        }
    }
    fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getLogin { infor ->
                repo.isLogin(infor[0].username, infor[0].password) {
                    bool.postValue(it)
                }
            }
        }
    }
    @SuppressLint("DefaultLocale")
    private fun xuLiTen(name : String) : String{
        var res = ""
        for(i in (name.length-1) downTo 0){
            if (name[i] == ' ')
                break
            res += name[i]
        }
        res = res.reversed().toLowerCase()
        res = res.capitalize()
        return res
    }

}