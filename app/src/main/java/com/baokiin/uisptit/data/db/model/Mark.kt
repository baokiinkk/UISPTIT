package com.baokiin.uisptit.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mark (@PrimaryKey(autoGenerate = true) val id:Int,val semester:String,val objectId:String,
            val objectName:String,val objectCredits:String,val percentCC:String,
            val percentKT:String,val percentTH:String,val percentSE:String,
            val percentThi:String,val CC:String,val KT:String,val TH:String,
            val Se:String,val Thi1:String,val Thi2:String,val Thi3:String,
            val Tk10:String,val TKCH:String,val KQ1:String,val KQ:String)