package com.baokiin.uisptit.ui.info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baokiin.uisptit.data.db.model.InfoUser
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.repository.DataRepository
import kotlinx.coroutines.launch

class InfoViewModel(private val repo: DataRepository) : ViewModel() {
    val listData:MutableLiveData<MutableList<Mark>?> = MutableLiveData(null)
     val title:MutableLiveData<String?> = MutableLiveData(null)
    fun getData(hk:String){
        viewModelScope.launch(){
            repo.getDataDiem(hk){
                listData.postValue(it)
            }

        }
    }
    fun get(){
        viewModelScope.launch {
            repo.getInforUser {
                title.postValue(xuLiTen(it.ten))
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