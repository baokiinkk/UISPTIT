package com.baokiin.uis.data.api

import android.content.Context
import android.util.Log
import com.baokiin.uisptit.data.db.model.LoginInfor
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jsoup.Jsoup
import java.util.concurrent.TimeUnit

// T muốn là tạo 2 hàm 1 hàm kiểm tra login 1 hàm lấy list điểm nhưng mà t buồn ngủ quá rồi T_T làm hộ nha

class HttpUis( var context: Context)  {
     fun login(name:String,pass:String) : MutableMap<String,String>  {
         Log.d("tncnhan", "start request")
         val list :MutableMap<String,String> = mutableMapOf()
         val cookieJars: ClearableCookieJar =
            PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
         var client = OkHttpClient().newBuilder()
            .cookieJar(cookieJars)
             .connectTimeout(5, TimeUnit.SECONDS)
             .writeTimeout(5, TimeUnit.SECONDS)
             .readTimeout(5, TimeUnit.SECONDS)
            .build()
         val formBody: RequestBody = FormBody.Builder()
            .add("__EVENTTARGET" , "")
            .add("__EVENTARGUMENT" , "")
            .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$txtTaiKhoa" , name.trim())
            .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$txtMatKhau" , pass.trim())
            .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$btnDangNhap" , "Đăng Nhập")
            .build()

         var request: Request = Request.Builder()
            .url("http://uis.ptithcm.edu.vn/default.aspx")
            .post(formBody)
            .build()

         var response = client.newCall(request).execute()
         if(response.priorResponse() == null) return mutableMapOf()
         Log.d("tncnhan", "login done")

         //Diem
         request = Request.Builder()
            .url("http://uis.ptithcm.edu.vn/default.aspx?page=xemdiemthi")
            .get()
            .build()
         var responseHtml = Jsoup.parse(client.newCall(request).execute().body().string())
         var viewState = responseHtml.select("#__VIEWSTATE").attr("value")
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
         list["Diem"] = response.body().string()
         Log.d("tncnhan", "diem done")
         //Lich thi
         request = Request.Builder()
             .url("http://uis.ptithcm.edu.vn/Default.aspx?page=xemlichthi")
             .get()
             .build()
         if (response.body() == null)
             Log.d("tncnhan", "null")
         response = client.newCall(request).execute()
         list["LichThi"] = response.body().string()
         Log.d("tncnhan", "lich thi done")
         // TKB
         request = Request.Builder()
             .url("http://uis.ptithcm.edu.vn/Default.aspx?page=thoikhoabieu")
             .get()
             .build()
         response = client.newCall(request).execute()
         val res = response.body().string()
         responseHtml = Jsoup.parse(res)
         list["TuanHoc"] = res
         Log.d("tncnhan", "tuan hoc done")
//         val weeks = responseHtml.select("#ctl00_ContentPlaceHolder1_ctl00_ddlTuan>option")
//         for(x in weeks) {
//             Log.d("tncnhan", x.text())
//         }
         viewState = responseHtml.select("#__VIEWSTATE").attr("value")
         val formGetSchedule: RequestBody = FormBody.Builder()
             .add("__EVENTTARGET" , "ctl00\$ContentPlaceHolder1\$ctl00\$rad_ThuTiet")
             .add("__EVENTARGUMENT" , "")
             .add("__VIEWSTATE", viewState)
             .add("__VIEWSTATEGENERATOR", "CA0B0334")
             .add("ctl00\$ContentPlaceHolder1\$ctl00\$rad_ThuTiet", "rad_ThuTiet")
             .add("ctl00\$ContentPlaceHolder1\$ctl00\$rad_MonHoc", "rad_MonHoc")
             .build()
         request = Request.Builder()
             .url("http://uis.ptithcm.edu.vn/Default.aspx?page=thoikhoabieu&sta=1")
             .post(formGetSchedule)
             .build()
         response = client.newCall(request).execute()

         list["TKB"] = response.body().string()
         Log.d("tncnhan", "tkb done")
        return list
    }


}