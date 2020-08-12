package com.baokiin.uisptit.ui.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.data.db.model.LoginInfor
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.data.repository.DataRepository

class InfoViewModel(private val repo: DataRepository) : ViewModel() {
    val listData:MutableLiveData<MutableList<Mark>?> = MutableLiveData(null)
    val title:MutableLiveData<String?> = MutableLiveData(null)
    val bool:MutableLiveData<Boolean?> = MutableLiveData(null)
    val login:MutableLiveData<LoginInfor?> = MutableLiveData(null)
    val listSemester:MutableLiveData<MutableList<SemesterMark>?> = MutableLiveData(null)
    val CNTAA:MutableLiveData<Int> = MutableLiveData(0)
    val listExam:MutableLiveData<MutableList<ExamTimetable>?> = MutableLiveData(null)
    fun getData(hk:String){
            repo.getDataDiem(hk){
                listData.postValue(it)
            }
            repo.getInforUser {
                title.postValue(xuLiTen(it.ten))
            }
            repo.getDataSemester {
                listSemester.postValue(it)
            }
            repo.getCNTAA {
                CNTAA.postValue(it)
            }
            repo.getExam {
                listExam.postValue(it)
            }
    }
    fun reload() {
            repo.getLogin {
                repo.isLogin(it[0].username, it[0].password) {
                    bool.postValue(it)
                }
            }
    }
    fun xuLiTen(name : String) : String{
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