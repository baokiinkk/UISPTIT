package com.baokiin.uis.data.repository.login

import android.util.Log
import com.baokiin.uis.data.api.HttpUis
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jsoup.Jsoup
import javax.security.auth.login.LoginException
import kotlin.coroutines.resume

class LoginRepositoryImpl( var network: HttpUis) : LoginRepository {
    private var list: MutableMap<String, String>? = null
    // private lateinit var loginInfor: LoginInfor


    @Throws(LoginRepository.LoginException::class)
    override fun isLogin(loginInfor: LoginInfor, islogin: (Boolean) -> Unit) {
        this.list = network.login(loginInfor)
        if (list!!.isNotEmpty()) {
            val x = xuLiDiemTongKet(list!!.get("Diem")!!)
            for(i in x){
               //0 Log.d("quocbaokiin",i)
            }
            islogin(true)

        } else {
            throw LoginRepository.LoginException()
        }


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
    fun xuLiDiem(htmlFile: String): MutableList<String> {
        var noiDung: MutableList<String> = mutableListOf()
        var htmlDidJsoup = Jsoup.parse(htmlFile)
        var doc = htmlDidJsoup.select("div#ctl00_ContentPlaceHolder1_ctl00_div1 tr.title-hk-diem span.Label,tr.row-diem span.Label")
        for (x in doc) {
            noiDung.add(x.text())
        }
        var res = mutableListOf<String>()
        var cnt = 1
        var result = ""
        for (x in noiDung) {
            // x có dạng "Học kỳ ......."
            if (isHocKy(x)) {
                res.add(x)
            } else {
                if (cnt < 20) {
                    result += x + " | "
                    cnt++;
                } else {
                    result += x + " | "
                    res.add(result)
                    cnt = 1
                    result = ""
                }
            }
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