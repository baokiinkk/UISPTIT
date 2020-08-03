package com.baokiin.uis.data.repository.login

import android.annotation.SuppressLint
import android.util.Log
import com.baokiin.uis.data.api.HttpUis
import com.baokiin.uisptit.data.db.AppDao
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
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
            postMarkToSQl()
            islogin(true)
        } else {
            throw DataRepository.LoginException()
        }


    }
    override fun postMarkToSQl() {
        GlobalScope.launch(Dispatchers.IO) {
            var x = xuLiDiem(list!!.get("Diem")!!)
            dao.deleteMark()
            for (i in x)
                dao.addMark(Mark(0,i[0],i[2],i[3],i[4],i[5],i[6],i[7],i[8],i[9],i[10],
                    i[11],i[12],i[13],i[14],i[15],i[16],i[17],i[18],i[19],i[20]))


//                val y = xuliLichThi(list!!.get("Diem")!!)
//                for(i in y){
//                    dao.addSemester(SemesterMark(i[0],i[1].toFloat(),i[2].toFloat(),i[3].toFloat(),
//                    i[4].toFloat(),i[5].toInt(),i[6].toInt()))
//                }

            // trả về list môn học để đổ vào SQL, có dạng: [472906202005072020, 5, 1, Cấu trúc dữ liệu và giải thuật, 2A08]
            // 472906202005072020 -> mã tuần -> tuần 47 từ 29-06-2020 đến 05-07-2020
            // 5 -> thứ 5
            // 1 -> buổi sáng / 2 là buổi chiều
            // Cấu trúc dữ liệu và giải thuật -> tên
            // 2A08 -> phòng
//                var x = xuLiTKB(xuLiMonHoc(list!!.get("TKB")!!), xuLiTuanHoc(list!!.get("TuanHoc")!!))
//                Log.d("tncnhan", "sizeTKB:" + x.size.toString())
//                for( i in x)
//                {
//                    Log.d("tncnhan",i.toString())
//                }

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
        for(i in 0 until res.size){
            val j = i%12
            if (j == 0) continue
            row.add(getDigits(res[i]))
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
            res.add(x.text())
        }
        var temp = mutableListOf<MutableList<String>>()
        var row : MutableList<String> = mutableListOf()
        for(i in 0..res.size-1){
            val j = i%13
            if (j%2 == 0)
                row.add(res[i])
            if(j == 12){
                temp.add(row)
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
                    if(obj[9] == "1") row.add("1")
                    else    row.add("2")
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

}