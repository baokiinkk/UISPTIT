package com.baokiin.uis.data.repository.login

import android.util.Log
import com.baokiin.uis.data.api.HttpUis
import com.baokiin.uisptit.data.db.AppDao
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.data.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import org.jsoup.Jsoup


class LoginRepositoryImpl( var network: HttpUis,var dao:AppDao) :
    LoginRepository {
    private var list: MutableMap<String, String>? = null
    // private lateinit var loginInfor: LoginInfor


    @Throws(LoginRepository.LoginException::class)
    override fun isLogin(loginInfor: LoginInfor, islogin: (Boolean) -> Unit) {
        this.list = network.login(loginInfor)
        if (list!!.isNotEmpty()) {
            GlobalScope.launch(Dispatchers.IO) {
                val lichthi = xuliLichThi(list!!.get("LichThi")!!)
                for (i in lichthi)
                {
                    //Log.d("tncnhan", i)
                }
                val x = xuLiDiem(list!!.get("Diem")!!)
                dao.deleteMark()
                for (i in x)
                    dao.addMark(Mark(0,i[0],i[2],i[3],i[4],i[5],i[6],i[7],i[8],i[9],i[10],
                        i[11],i[12],i[13],i[14],i[15],i[16],i[17],i[18],i[19],i[20]))
                islogin(true)
            }

        } else {
            throw LoginRepository.LoginException()
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

    fun xuliLichThi(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("table#ctl00_ContentPlaceHolder1_ctl00_gvXem td")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        return res
    }

    fun xuLiThongTin(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("div.infor-member td")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        return res
    }
    fun xuLiDiemTongKet(htmlFile : String) : MutableList<String>{
        val doc = Jsoup.parse(htmlFile)
        val noiDung = doc.select("div#ctl00_ContentPlaceHolder1_ctl00_div1 tr.title-hk-diem span.Label,tr.row-diemTK span.Label")
        var res = mutableListOf<String>()
        for(x in noiDung )
        {
            res.add(x.text())
        }
        return res
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
                hk = maHocKi(hk)
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