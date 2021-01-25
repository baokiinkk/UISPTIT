package com.baokiin.uis.data.repository.login

import android.annotation.SuppressLint
import android.text.Html
import android.util.Log
import com.baokiin.uis.data.api.HttpUis
import com.baokiin.uisptit.data.db.AppDao
import com.baokiin.uisptit.data.db.model.*
import com.baokiin.uisptit.data.repository.DataRepository
import org.jsoup.Jsoup
import org.w3c.dom.Element
import java.lang.Exception
import java.lang.annotation.ElementType
import java.text.SimpleDateFormat
import java.util.*


class DataRepositoryImpl(var network: HttpUis, var dao:AppDao) :
    DataRepository {
    private var list: MutableMap<String, String>? = null
    private val point = mapOf<String, Float>(
        "A+" to 4F,
        "A" to 3.7F,
        "B+" to 3.5F,
        "B" to 3F,
        "C+" to 2.5F,
        "C" to 2F,
        "D+" to 1.5F,
        "D" to 1F,
        "F" to 0F
    )
    private var semesterPoint = mutableMapOf<String, Float>()
    // private lateinit var loginInfor: LoginInfor

    override suspend fun isLogin(name: String, pass: String, islogin: suspend (String) -> Unit) {
        list = network.login(name, pass)
        if (!list!!.containsKey("error")) {
            try{
                dao.deleteExam()
                dao.deleteExamTimeTable()
                dao.deleteInforUser()
                dao.deleteMark()
                dao.deleteTimeTable()
                dao.deleteSemester()
                val exam = xuliLichThi(list!!.get("LichThi")!!)
                for (i in exam) {
                    //Log.d("tncnhan", i.toString())
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

                val mark = xuLiDiem(list!!.get("Diem")!!)
                var thisSem = mark[0].get(0)
                var sum = 0F
                var sumPoint = 0.0F
                for (i in mark){
                    Log.d("tncnhan", i.toString())
                    if(!i[0].equals(thisSem)){
                        semesterPoint.put(thisSem, sumPoint/sum)
                        thisSem = i[0]
                        sumPoint = 0.0F
                        sum = 0F
                    }
                    sumPoint += point.get(i[18])!! * i[4].toFloat()
                    sum += i[4].toFloat()
                    dao.addMark(
                        Mark(
                            0, i[0], i[2], i[3], i[4], i[5], i[6], i[7], i[8], i[9], i[10],
                            i[11], i[12], i[13], i[14], i[15], i[16], i[17], i[18], i[19], i[20]
                        )
                    )
                }
                semesterPoint.put(thisSem, sumPoint/sum)


                val semester = xuLiDiemTongKet(list!!.get("Diem")!!)
                for (i in semester) {
                    Log.d("tncnhan", i.toString())
                    dao.addSemester(
                        SemesterMark(
                            i[0], i[1].toFloat(), semesterPoint.get(i[0])!!, i[2].toFloat(),
                            i[3].toFloat(), i[4].toInt(), i[5].toInt()
                        )

                    )

                }

                //xuLiMonHoc2(list!!.get("TKB2")!!)
                val tkb = xuLiTKB(xuLiMonHoc(list!!.get("TKB")!!), xuLiTuanHoc(list!!.get("TuanHoc")!!))
                for (i in tkb) {
                    dao.addTimeTable(
                        TimeTable(0, i[0], i[1], i[2], i[3], i[4])
                    )
                }
                islogin("")
            }
            catch (e : Exception){

                //islogin("-----------------------------")
                Log.d("tncnhan", e.toString())
                islogin("Lỗi trong quá trình tải dữ liệu!")
            }
        }
        else {
                islogin(list!!.get("error")!!)
        }
    }


    override suspend fun addLogin(name: String, pass: String) {

            dao.addUser(LoginInfor(name,pass))
    }

    override suspend fun deleteLogin() {
            dao.deleteLogin()
    }

    override suspend fun getDataDiem(hk:String, getdata: (MutableList<Mark>) -> Unit) {
            val markHK:MutableList<Mark>
            if(hk.equals(""))
                markHK= dao.getMark()
            else markHK = dao.getMarkHK(hk)
            getdata(markHK)
    }

    override suspend fun getCNTAA(data: (Int) -> Unit) {
        data(dao.getCNTAA())
    }

    override suspend fun getDataSemester(hk: String, getdata: (MutableList<SemesterMark>) -> Unit) {
            val markHK:MutableList<SemesterMark>
            if(hk.equals(""))
                markHK= dao.getSemester()
            else markHK = dao.getSemesterHK(hk)
            getdata(markHK)
    }

    override suspend fun getExam(data: (MutableList<ExamTimetable>) -> Unit) {
            data(dao.getExamTimeTable())
    }

    override suspend fun getInforUser(data: (InfoUser) -> Unit) {
            val datadao = dao.getInforUser()
            data(datadao)
    }

    override suspend fun getLogin(data: suspend (MutableList<LoginInfor>) -> Unit) {
        var accountInfo: MutableList<LoginInfor> = dao.getLogin()

        data(accountInfo)
    }

    override suspend fun getTimeTable(data: (MutableList<TimeTable>) -> Unit) {
        val tmp = dao.getTimeTable()
        data(tmp)
    }

    @SuppressLint("SimpleDateFormat")
    fun toDate(str:String): Date {
        val sdf =
            SimpleDateFormat("dd/MM/yyyy")
        val d = sdf.parse(str)
        return d
    }
    fun xuliLichThi(htmlFile : String) : MutableList<MutableList<String>>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("table#ctl00_ContentPlaceHolder1_ctl00_gvXem td")
        val res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        val temp = mutableListOf<MutableList<String>>()
        var row : MutableList<String> = mutableListOf()
        for(i in 0 until res.size){
            val j = i%14
            if (j == 0) continue
            row.add(res[i])
            if(j == 13){
                temp.add(row)
                row = mutableListOf()
            }

        }
        return temp
    }

    fun xuLiThongTin(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("div.infor-member td")
        val res = mutableListOf<String>()
        val temp = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        for(i in 1 until res.size step 2){
            temp.add(res[i])
        }
        return temp
    }


    fun xuLiDiemTongKet(htmlFile : String) : MutableList<MutableList<String>>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("div#ctl00_ContentPlaceHolder1_ctl00_div1 tr.title-hk-diem span.Label,tr.row-diemTK span.Label")
        val res = mutableListOf<String>()
        for(x in noiDung )
        {
            if (!isBaoLuu(x.text()))
                res.add(x.text())
            Log.d("tncnhan", x.text())
        }
        val temp = mutableListOf<MutableList<String>>()
        var row : MutableList<String> = mutableListOf()
        for(i in 0 until res.size){
            val j = i%11
            if (j%2 == 0)
            {
                if(j == 0)
                    row.add(getDigits(res[i]))
                else
                    row.add(res[i])
            }
            if(j == 10){
                temp.add(row)

                row = mutableListOf()
            }
        }

        return temp
    }
    fun xuLiDiem(htmlFile: String): MutableList<MutableList<String>> {
        val noiDung: MutableList<String> = mutableListOf()
        val htmlDidJsoup = Jsoup.parse(htmlFile)
        val doc = htmlDidJsoup.select("div#ctl00_ContentPlaceHolder1_ctl00_div1 tr.title-hk-diem span.Label,tr.row-diem span.Label")
        for (x in doc) {
            noiDung.add(x.text())
        }
        val res = mutableListOf<MutableList<String>>()
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

//        for(i in 0 until res.size){
//            Log.d("tncnhan", res[i].toString())
//        }
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
//    fun xuLiMonHoc(htmlFile : String) : MutableList<MutableList<String>>{
//        val htmlDidJsoup = Jsoup.parse(htmlFile)
//        val doc = htmlDidJsoup.select("table.body-table td")
//        val noiDung = mutableListOf<String>()
//        for (x in doc) {
//            noiDung.add(x.text())
//        }
//        val res = mutableListOf<MutableList<String>>()
//        var monHoc:MutableList<String> = mutableListOf()
//        for (i in 0 until noiDung.size) {
//            val j = i%15
//            if (j == 14) {
//                if(!monHoc[0].equals(""))
//                    res.add(monHoc)
//                monHoc = mutableListOf()
//            }
//            else{
//                monHoc.add(noiDung[i])
//            }
//        }
//        for(x in res){
//            Log.d("tncnhanMonHocCu", x.toString())
//        }
//        return res
//    }

    fun xuLiMonHoc(htmlFile : String) : MutableList<MutableList<String>>{
        val htmlDidJsoup = Jsoup.parse(htmlFile)
        //val doc = htmlDidJsoup.select("div.grid-roll2>table.body-table")
        val table =  htmlDidJsoup.select("div.grid-roll2>table")
        val res = mutableListOf<MutableList<String>>()
        for (x in table) {
            val obj = x.child(0).child(0)
            try {
                for(i in 0 until obj.child(8).childNodes().size){
                        var infoObj = mutableListOf<String>()
                        for(i in 0 until 5)
                            infoObj.add(obj.child(i).text())
                        infoObj.add(obj.child(8).child(i).text())
                        infoObj.add(obj.child(9).child(i).text())
                        infoObj.add(obj.child(10).child(i).text())
                        infoObj.add(obj.child(11).child(i).text().substring(0, 4))
                        infoObj.add(getTuan(obj.child(13).child(i).getElementsByAttribute("onmouseover").attr("onmouseover")))
                        res.add(infoObj)
//                        Log.d("tncnhanMon", infoObj.toString())
                        //Log.d("tncnhanMon", "--------------------------------------------")
                }
                //Log.d("tncnhanMon",obj.child(13).child(0).getElementsByAttribute("onmouseover").attr("onmouseover"))

            }
            catch (e : Exception){
//                Log.d("tncnhan", "get Fail")
            }


        }


        return res
    }

    // gom mã tuần học và môn học để thành dữ liệu đổ vào sql
    fun xuLiTKB(listMonHoc : MutableList<MutableList<String>>, listTuan : MutableList<String>) : MutableList<MutableList<String>>{
        val res = mutableListOf<MutableList<String>>()
        var row = mutableListOf<String>()
        for (i in 0 until listTuan.size){
            var emptyWeek = true
            val week = listTuan.get(i)
            for (obj in listMonHoc){
                if (isHaveObj(i, obj[9])){
//                    Log.d("tncnhan", "i = " + i.toString() + ", " + obj[9])
                    emptyWeek = false
                    row = mutableListOf()
                    row.add(week)
                    row.add(thuTuNgay(obj[5]))
                    if(obj[6] == "1") row.add("0")
                    else    row.add("1")
                    row.add(obj[1])
                    row.add(obj[8])
                    res.add(row)
                }
            }
            if(emptyWeek){
                row = mutableListOf()
                row.add(week)
                row.add("")
                row.add("")
                row.add("")
                row.add("")
                res.add(row)
            }
        }
//        for (x in res)
//            Log.d("tncnhanTuanKTB", x.toString())
        return res
    }

    // biến đổi chuỗi chứa 2 ngày thành list chứa 2 biến ngày
    fun getDate(stringDate : String) : MutableList<Date>{
        val startDate = stringDate.substring(1, 11)
        val endDate = stringDate.substring(13, 23)
        val res = mutableListOf<Date>()
        res.add(toDate(startDate))
        res.add(toDate(endDate))
        return res
    }

    // kiểm tra ngày trong string có năm giữa 2 ngày trong dates hay không
    fun checkDate(stringWeek : String, dates : MutableList<Date>) : Boolean{
        val strDay = stringWeek.substring(2,4) + "/" + stringWeek.substring(4,6) + "/" + stringWeek.substring(6,10)
        //Log.d("tncnhan", strDay)
        val day = toDate(strDay)
        return (day.before(dates[1]) && (day.after(dates[0]) || day.equals(dates[0])))
    }

    // trả về list mã tuần học có trong kì học đó
    fun xuLiTuanHoc(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("#ctl00_ContentPlaceHolder1_ctl00_ddlTuan>option")
        val res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(getDigits(x.text()))
        }
        return res
    }

    fun thuTuNgay(ngay : String) : String{
        val res = when(ngay){
            "Hai" -> 2
            "Ba" -> 3
            "Tư" -> 4
            "Năm" -> 5
            "Sáu" -> 6
            "Bảy" -> 7
            else -> 8
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

    private fun getTuan(ddrivetiptuan : String) : String{
        return ddrivetiptuan.substring(15, ddrivetiptuan.length-2)
    }

    private fun isHaveObj(index : Int, weekString : String) : Boolean{
        try {
            return weekString[index] != '-'
        }
        catch (e : Exception) {
            return false
        }
    }

}