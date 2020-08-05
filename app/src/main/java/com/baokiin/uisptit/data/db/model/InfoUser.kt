package com.baokiin.uisptit.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InfoUser(@PrimaryKey val id:String,val ten:String,val phai:String,val noiSinh:String,val lop:String,val nganh:String,val khoa:String,
                        val heDaoTao:String,val KhoaHoc:String)