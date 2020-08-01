package com.baokiin.uis.data.api

import android.content.Context
import com.baokiin.uisptit.data.db.LoginInfor
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jsoup.Jsoup

// T muốn là tạo 2 hàm 1 hàm kiểm tra login 1 hàm lấy list điểm nhưng mà t buồn ngủ quá rồi T_T làm hộ nha

class HttpUis( var context: Context)  {
     fun login(loginInfor: LoginInfor) : MutableMap<String,String>  {
         val list :MutableMap<String,String> = mutableMapOf()
        val cookieJars: ClearableCookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
        var client = OkHttpClient().newBuilder()
            .cookieJar(cookieJars)
            .build()
        val formBody: RequestBody = FormBody.Builder()
            .add("__EVENTTARGET" , "")
            .add("__EVENTARGUMENT" , "")
            .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$txtTaiKhoa" , loginInfor.username)
            .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$txtMatKhau" , loginInfor.password)
            .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$btnDangNhap" , "Đăng Nhập")
            .build()

        var request: Request = Request.Builder()
            .url("http://uis.ptithcm.edu.vn/default.aspx")
            .post(formBody)
            .build()

        var response = client.newCall(request).execute()
         if(response.priorResponse() == null) return mutableMapOf()


         //Diem
        request = Request.Builder()
            .url("http://uis.ptithcm.edu.vn/default.aspx?page=xemdiemthi")
            .get()
            .build()
         val responseHtml = Jsoup.parse(client.newCall(request).execute().body().string())
         val viewState = responseHtml.select("#__VIEWSTATE").attr("value")
         val formGetMark: RequestBody = FormBody.Builder()
             .add("__EVENTTARGET" , "ctl00\$ContentPlaceHolder1\$ctl00\$lnkChangeview2")
             .add("__EVENTARGUMENT" , "")
             .add("__VIEWSTATE", viewState)
             .add("__VIEWSTATEGENERATOR", "CA0B0334")
             .build()
         request = Request.Builder()
             .url("http://uis.ptithcm.edu.vn/default.aspx?page=xemdiemthi")
             .post(formGetMark)
             .build()

         response = client.newCall(request).execute()
         list.put("Diem",response.body().string())

         //infor

        return list
    }


}