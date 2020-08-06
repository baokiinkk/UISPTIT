package com.baokiin.uisptit.ui.option

import androidx.lifecycle.ViewModel
import com.baokiin.uisptit.data.repository.DataRepository

class OptionViewModel(private val repo: DataRepository): ViewModel(){
    fun deleteLogin(){
        repo.deleteLogin()
    }
}