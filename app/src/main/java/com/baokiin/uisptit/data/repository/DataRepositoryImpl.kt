package com.baokiin.uis.data.repository.login

import android.annotation.SuppressLint
import android.util.Log
import com.baokiin.uis.data.api.HttpUis
import com.baokiin.uisptit.data.db.AppDao
import com.baokiin.uisptit.data.db.model.*
import com.baokiin.uisptit.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.MutableMap.MutableEntry


class DataRepositoryImpl(var network: HttpUis, var dao:AppDao) :
    DataRepository {
    private var list: MutableMap<String, String>? = null
    // private lateinit var loginInfor: LoginInfor


    @Throws(DataRepository.LoginException::class)
    override fun isLogin(name:String,pass:String, islogin: (Boolean) -> Unit) {
        GlobalScope.launch (Dispatchers.IO){
           list = network.login(name,pass)
            if (list!!.isNotEmpty()) {
                dao.deleteExam()
                dao.deleteExamTimeTable()
                dao.deleteInforUser()
                dao.deleteMark()
                dao.deleteTimeTable()
                dao.deleteSemester()
                val exam = xuliLichThi(list!!.get("LichThi")!!)
                for (i in exam) {
                    dao.addExamTimeTable(
                        ExamTimetable(
                            0,
                            i[0],
                            i[1],
                            i[2],
                            i[3],
                            i[4],
                            i[5],
                            i[6].toInt(),
                            i[7].toInt(),
                            i[8],
                            i[9]
                        )
                    )
                }

                val inforUser = xuLiThongTin(list!!.get("LichThi")!!)
                dao.addInforUser(InfoUser(inforUser[0],inforUser[1],inforUser[2],inforUser[3],inforUser[4],inforUser[5],
                        inforUser[6],inforUser[7],inforUser[8]))

                var mark = xuLiDiem(list!!.get("Diem")!!)
                for (i in mark)
                    dao.addMark(
                            Mark(
                                    0, i[0], i[2], i[3], i[4], i[5], i[6], i[7], i[8], i[9], i[10],
                                    i[11], i[12], i[13], i[14], i[15], i[16], i[17], i[18], i[19], i[20]
                            )
                    )


                val semester = xuLiDiemTongKet(list!!.get("Diem")!!)
                for (i in semester) {
                    Log.d("quocbaokiin",i.toString())
                    dao.addSemester(
                            SemesterMark(
                                    i[0], i[1].toFloat(), i[2].toFloat(), i[3].toFloat(),
                                    i[4].toFloat(), i[5].toInt(), i[6].toInt()
                            )

                    )

                }


                val tkb = xuLiTKB(xuLiMonHoc(list!!.get("TKB")!!), xuLiTuanHoc(list!!.get("TuanHoc")!!))
                for (i in tkb) {
                    Log.d("tncnhan", "tkb" + i.toString())
                    dao.addTimeTable(
                            TimeTable(0, i[0], i[1], i[2], i[3], i[4])
                    )
                }

                islogin(true)
            } else {
                islogin(false)
            }
        }
    }


    override fun addLogin(name: String, pass: String) {
        GlobalScope.launch(Dispatchers.IO){
            dao.addUser(LoginInfor(name,pass))
        }
    }

    override fun deleteLogin() {
        GlobalScope.launch(Dispatchers.IO){
            dao.deleteLogin()
        }
    }

    override fun getDataDiem( hk:String,getdata: (MutableList<Mark>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO){
            val markHK:MutableList<Mark>
            if(hk.equals(""))
                markHK= dao.getMark()
            else markHK = dao.getMarkHK(hk)
            getdata(markHK)
        }
    }

    override fun getCNTAA(data: (Int) -> Unit) {
        GlobalScope.launch {
            data(dao.getCNTAA())
        }
    }

    override fun getDataSemester(hk: String, getdata: (MutableList<SemesterMark>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO){
            val markHK:MutableList<SemesterMark>
            if(hk.equals(""))
                markHK= dao.getSemester()
            else markHK = dao.getSemesterHK(hk)
            getdata(markHK)
        }
    }

    override fun getExam(data: (MutableList<ExamTimetable>) -> Unit) {
        GlobalScope.launch {
            data(dao.getExamTimeTable())
        }
    }

    override fun getInforUser(data: (InfoUser) -> Unit) {
        GlobalScope.launch(Dispatchers.Default){
            val datadao = dao.getInforUser()
            data(datadao)
        }
    }

    override fun getLogin(data: (MutableList<LoginInfor>) -> Unit) {
        GlobalScope.launch {
            data(dao.getLogin())
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
        for(i in 0 until res.size){
            val j = i%12
            if (j == 0) continue
            row.add(res[i])
            if(j == 11){
                temp.add(row)
                row = mutableListOf()
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
            if (!isBaoLuu(x.text()))
                res.add(x.text())
        }
        var temp = mutableListOf<MutableList<String>>()
        var row : MutableList<String> = mutableListOf()
        for(i in 0 until res.size){
            val j = i%13
            if (j%2 == 0)
            {
                if(j == 0)
                    row.add(getDigits(res[i]))
                else
                    row.add(res[i])
            }
            if(j == 12){
                temp.add(row)
                Log.d("tncnhan", row.toString())
                row = mutableListOf()
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
                hk = getDigits(x)
                result[0]=(hk)

            }
            else if (isBaoLuu(x)){
                hk = "baoLuu"
                result[0]=(hk)
            }
            else {
                if (cnt < 20) {
                    result.add(x)
                    cnt++
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

    // trả về chuỗi chỉ chứa số của xâu truyền vào
    private fun getDigits(stringHK : String) : String{
        var res = ""
        for (x in stringHK){
            if (x.isDigit())
                res += x
        }
        return res
    }

    // từ file html môn học -> list chứa lịch từng môn học
    fun xuLiMonHoc(htmlFile : String) : MutableList<MutableList<String>>{
        val htmlDidJsoup = Jsoup.parse(htmlFile)
        val doc = htmlDidJsoup.select("table.body-table td")
        val noiDung = mutableListOf<String>()
        for (x in doc) {
            noiDung.add(x.text())
        }
        var res = mutableListOf<MutableList<String>>()
        var monHoc:MutableList<String> = mutableListOf()
        for (i in 0 until noiDung.size) {
            val j = i%15
            if (j == 14) {
                res.add(monHoc)
                monHoc = mutableListOf()
            }
            else{
                monHoc.add(noiDung[i])
            }
        }
        return res
    }

    // gom mã tuần học và môn học để thành dữ liệu đổ vào sql
    fun xuLiTKB(listMonHoc : MutableList<MutableList<String>>, listTuan : MutableList<String>) : MutableList<MutableList<String>>{
        var res = mutableListOf<MutableList<String>>()
        var row = mutableListOf<String>()
        for (week in listTuan){
            for (obj in listMonHoc){
                if (checkDate(week,getDate(obj[13]))){
                    row = mutableListOf()
                    row.add(week)
                    row.add(thuTuNgay(obj[8]))
                    if(obj[9] == "0") row.add("0")
                    else    row.add("1")
                    row.add(obj[1])
                    row.add(obj[11])
                    res.add(row)
                }
            }
        }
        return res
    }

    // biến đổi chuỗi chứa 2 ngày thành list chứa 2 biến ngày
    fun getDate(stringDate : String) : MutableList<Date>{
        val startDate = stringDate.substring(1, 11)
        val endDate = stringDate.substring(13, 23)
        var res = mutableListOf<Date>()
        res.add(toDate(startDate))
        res.add(toDate(endDate))
        return res
    }

    // kiểm tra ngày trong string có năm giữa 2 ngày trong dates hay không
    fun checkDate(stringWeek : String, dates : MutableList<Date>) : Boolean{
        val strDay = stringWeek.substring(2,4) + "/" + stringWeek.substring(4,6) + "/" + stringWeek.substring(6,10)
        //Log.d("tncnhan", strDay)
        val day = toDate(strDay)
        //Log.d("tncnhan", dates[0].toString() + "_" + day.toString() + "_" + dates[1].toString())
        return (day.before(dates[1]) && (day.after(dates[0]) || day.after(dates[0])))
    }

    // trả về list mã tuần học có trong kì học đó
    fun xuLiTuanHoc(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("#ctl00_ContentPlaceHolder1_ctl00_ddlTuan>option")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(getDigits(x.text()))
        }
        Log.d("tncnhan", res.toString())
        return res
    }

    fun thuTuNgay(ngay : String) : String{
        var res = when(ngay){
            "Hai" -> 2
            "Ba" -> 3
            "Tư" -> 4
            "Năm" -> 5
            "Sáu" -> 6
            "Bảy" -> 7
            else -> 0
        }
        return res.toString()
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

    private fun isBaoLuu(name: String): Boolean {
        val baoluu = "Điểm bảo"
        if (name.length <= baoluu.length)
            return false
        else {
            for (i in baoluu.indices) {
                if (baoluu[i] != name[i])
                    return false
            }
            return true
        }
    }

}