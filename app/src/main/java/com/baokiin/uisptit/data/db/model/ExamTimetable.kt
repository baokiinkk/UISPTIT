package com.baokiin.uisptit.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.*

@Entity
data class ExamTimetable(@PrimaryKey(autoGenerate = true) val id:Int,val maMon:String,val tenMon:String,val lop:String,
                         val toThi:String,val soLuong:String,val ngayThi:String,val startTime:Int,val sotiet:Int,val phong:String,
                            val ghichu:String
                         )