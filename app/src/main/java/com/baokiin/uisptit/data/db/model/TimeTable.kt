package com.baokiin.uisptit.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
//
@Entity
data class TimeTable(@PrimaryKey(autoGenerate = true) val id:Int,val tuan:String,val thu:String,val buoi:String,val tenMon:String,val phong:String)