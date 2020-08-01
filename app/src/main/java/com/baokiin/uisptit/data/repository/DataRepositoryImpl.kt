package com.baokiin.uis.data.repository.login

import android.annotation.SuppressLint
import android.util.Log
import com.baokiin.uis.data.api.HttpUis
import com.baokiin.uisptit.data.db.AppDao
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*


class DataRepositoryImpl(var network: HttpUis, var dao:AppDao) :
    DataRepository {
    private var list: MutableMap<String, String>? = null
    // private lateinit var loginInfor: LoginInfor


    @Throws(DataRepository.LoginException::class)
    override fun isLogin(loginInfor: LoginInfor, islogin: (Boolean) -> Unit) {
        this.list = network.login(loginInfor)
        if (list!!.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
//                var x = xuLiDiem(list!!.get("Diem")!!)
//                dao.deleteMark()
//                for (i in x)
//                    dao.addMark(Mark(0,i[0],i[2],i[3],i[4],i[5],i[6],i[7],i[8],i[9],i[10],
//                        i[11],i[12],i[13],i[14],i[15],i[16],i[17],i[18],i[19],i[20]))


//                val y = xuliLichThi(list!!.get("Diem")!!)
//                for(i in y){
//                    dao.addSemester(SemesterMark(i[0],i[1].toFloat(),i[2].toFloat(),i[3].toFloat(),
//                    i[4].toFloat(),i[5].toInt(),i[6].toInt()))
//                }
                var x = xuLiTKB(list!!.get("TKB")!!)
                for( i in x)
                {
                    Log.d("quocbaokiin",i)
                }
                islogin(true)
            }

        } else {
            throw DataRepository.LoginException()
        }


    }

    override fun getDataDiem( hk:String,getdata: (MutableList<Mark>) -> Unit) {
        GlobalScope.launch {

            val markHK:MutableList<Mark>
            if(hk.equals(""))
                markHK= dao.getMark()
            else markHK = dao.getMarkHK(hk)
            getdata(markHK)
        }
    }

    override fun getDataSemester(hk: String, getdata: (MutableList<SemesterMark>) -> Unit) {
        GlobalScope.launch {
            val markHK:MutableList<SemesterMark>
            if(hk.equals(""))
                markHK= dao.getSemester()
            else markHK = dao.getSemesterHK(hk)
            getdata(markHK)
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun toDate(str:String): java.util.Date {
        val sdf =
            SimpleDateFormat("dd/MM/yyyy")
        val d = sdf.parse(str)
        return d
    }
    fun xuliLichThi(htmlFile : String) : MutableList<MutableList<String>>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("table#ctl00_ContentPlaceHolder1_ctl00_gvXem td")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        var temp = mutableListOf<MutableList<String>>()
        var row : MutableList<String> = mutableListOf()
        for(i in 0..res.size-1){
            val j = i%12
            if (j == 1)
            {
                row = mutableListOf()
                row.add(maHocKi(res[i]))
            }
            else
            {
                if(j == 11){
                    temp.add(row)
                }
                else if(j > 1)
                    row.add(res[i])
            }
        }

        return temp
    }

    fun xuLiThongTin(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("div.infor-member td")
        var res = mutableListOf<String>()
        var temp = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        for(i in 1..res.size-1 step 2){
            temp.add(res[i])
        }
        return temp
    }
    fun xuLiDiemTongKet(htmlFile : String) : MutableList<MutableList<String>>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("div#ctl00_ContentPlaceHolder1_ctl00_div1 tr.title-hk-diem span.Label,tr.row-diemTK span.Label")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        var temp = mutableListOf<MutableList<String>>()
        var row : MutableList<String> = mutableListOf()
        for(i in 0..res.size-1){
            val j = i%13
            if (j == 0)
            {
                row = mutableListOf()
                row.add(maHocKi(res[i]))
            }
            else
            {
                if (j%2 == 0)
                    row.add(res[i])
                if(j == 12){
                    temp.add(row)
                }
            }
        }

        return temp
    }
    fun xuLiDiem(htmlFile: String): MutableList<MutableList<String>> {
        var noiDung: MutableList<String> = mutableListOf()
        var htmlDidJsoup = Jsoup.parse(htmlFile)
        var doc = htmlDidJsoup.select("div#ctl00_ContentPlaceHolder1_ctl00_div1 tr.title-hk-diem span.Label,tr.row-diem span.Label")
        for (x in doc) {
            noiDung.add(x.text())
        }
        var res = mutableListOf<MutableList<String>>()
        var result:MutableList<String> = mutableListOf()
        result.add("")
        var cnt = 1
        var hk = ""
        for (x in noiDung) {

            // x có dạng "Học kỳ ......."
            if (isHocKy(x)) {
                //res.add(x)
                hk = maHocKi(x)
                result[0]=(hk)

            } else {
                if (cnt < 20) {
                    result.add(x)

                    cnt++;
                } else {
                    result.add(x)
                    res.add(result)
                    result = mutableListOf()
                    result.add(hk)
                    cnt = 1
                }
            }
        }
        return res
    }

    private fun maHocKi(stringHK : String) : String{
        var res = ""
        for (x in stringHK){
            if (x.isDigit())
                res += x
        }
        return res
    }

    fun xuLiTKB(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("table.body-table td")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        return res
    }

    fun xuLiTuanHoc(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("#ctl00_ContentPlaceHolder1_ctl00_ddlTuan>option")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        return res
    }

    private fun isHocKy(name: String): Boolean {
        val hocki = "Học kỳ"
        if (name.length <= hocki.length)
            return false
        else {
            for (i in hocki.indices) {
                if (hocki[i] != name[i])
                    return false
            }
            return true
        }
    }

}