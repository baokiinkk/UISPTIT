package com.baokiin.uisptit.data.db.model

class ListMark(ten: String, listMark: MutableList<Mark>, val listSemesterMark: SemesterMark) {
    val hocki:String = ten
    val list:MutableList<Mark> = listMark
}