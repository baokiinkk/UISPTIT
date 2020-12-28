package com.baokiin.uis.data.api

import android.content.Context
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import java.util.concurrent.TimeUnit


class HttpUis( var context: Context)  {
     fun login(name:String,pass:String) : MutableMap<String,String>  {
         //Log.d("tncnhan", "start request")\
         val list: MutableMap<String, String> = mutableMapOf()
         //check  name
         if(!hasActiveInternetConnection(context)){
             list["error"] = "Không kết nối được tới Server UIS!"
             return list
         }

         if (name.length > 0 && name.get(0).toLowerCase() != 'n') {
             list["error"] = "Tài khoản không hợp lệ!"
             return list
         }


         try{
             val address = InetAddress.getByName("uis.ptithcm.edu.vn")
             val cookieJars: ClearableCookieJar =
                 PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
             var client = OkHttpClient().newBuilder()
                 .cookieJar(cookieJars)
                 .connectTimeout(10, TimeUnit.SECONDS)
                 .writeTimeout(10, TimeUnit.SECONDS)
                 .readTimeout(10, TimeUnit.SECONDS)
                 .build()

             //check CAPCHA
             var request: Request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/default.aspx")
                 .get()
                 .build()
             var response = client.newCall(request).execute()
             var responseHtml = Jsoup.parse(response.body().string())
             val capcha = responseHtml.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblCapcha")
             //Log.d("tncnhan", "capcha: " + capcha.text())
             if (capcha != null){
                 var viewState = responseHtml.select("#__VIEWSTATE").attr("value")
                 var formBody = FormBody.Builder()
                     .add("__VIEWSTATE", viewState)
                     .add("__VIEWSTATEGENERATOR", "CA0B0334")
                     .add("ctl00\$ContentPlaceHolder1\$ctl00\$txtCaptcha", capcha.text())
                     .add("ctl00\$ContentPlaceHolder1\$ctl00\$btnXacNhan", "Vào website")
                     .build()
                 request = Request.Builder()
                     .url("https://uis.ptithcm.edu.vn/default.aspx?page=xemdiemthi")
                     .post(formBody)
                     .build()
                 client.newCall(request).execute()
             }
             //==================================================

             //LOGIN =============
             var formBody  = FormBody.Builder()
                 .add("__EVENTTARGET" , "")
                 .add("__EVENTARGUMENT" , "")
                 .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$txtTaiKhoa" , name.trim())
                 .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$txtMatKhau" , pass.trim())
                 .add("ctl00\$ContentPlaceHolder1\$ctl00\$ucDangNhap\$btnDangNhap" , "Đăng Nhập")
                 .build()

             request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/default.aspx")
                 .post(formBody)
                 .build()

             response = client.newCall(request).execute()
             //Log.d("tncnhan", "code: " + response.)
             if (response.priorResponse() == null) {
                 list["error"] = "Sai tên đăng nhập hoặc tài khoản!"
                 return list
             }



//         request = Request.Builder()
//             .url("http://123.30.155.178:85/api/Schedule")
//             .get()
//             .build()
//         response = client.newCall(request).execute()

             // DIEM ==============
             //TODO
             request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/default.aspx?page=xemdiemthi")
                 .get()
                 .build()
             response = client.newCall(request).execute()
             if(response.priorResponse() != null){
                 list["error"] =
                     "Vào trang UIS mục Xem Điểm để đánh giá giảng viên sau đó mới có thể tiếp tục sử dụng app!"
                 return list
             }
             responseHtml = Jsoup.parse(response.body().string())
             var viewState = responseHtml.select("#__VIEWSTATE").attr("value")
             formBody = FormBody.Builder()
                 .add("__EVENTTARGET", "ctl00\$ContentPlaceHolder1\$ctl00\$lnkChangeview2")
                 .add("__EVENTARGUMENT", "")
                 .add("__VIEWSTATE", viewState)
                 .add("__VIEWSTATEGENERATOR", "CA0B0334")
                 .build()
             request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/default.aspx?page=xemdiemthi")
                 .post(formBody)
                 .build()

             response = client.newCall(request).execute()

             list["Diem"] = response.body().string()


             // LICH THI =============
             request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/Default.aspx?page=xemlichthi")
                 .get()
                 .build()
             //if (response.body() == null)
             response = client.newCall(request).execute()
             list["LichThi"] = response.body().string()


             //TODO
             //test TKB
             request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/default.aspx?page=thoikhoabieu&sta=1")
                 .get()
                 .build()
             response = client.newCall(request).execute()

             list["TKB"] = response.body().string()

             //====================================
             //============================



             //TUAN HOC =================

             request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/Default.aspx?page=thoikhoabieu")
                 .get()
                 .build()
             response = client.newCall(request).execute()
             val res = response.body().string()
             list["TuanHoc"] = res


             // TKB ==================
//             responseHtml = Jsoup.parse(res)
//             viewState = responseHtml.select("#__VIEWSTATE").attr("value")
//             formBody = FormBody.Builder()
//                 .add("__EVENTTARGET" , "ctl00\$ContentPlaceHolder1\$ctl00\$rad_ThuTiet")
//                 .add("__EVENTARGUMENT" , "")
//                 .add("__VIEWSTATE", viewState)
//                 .add("__VIEWSTATEGENERATOR", "CA0B0334")
//                 .add("ctl00\$ContentPlaceHolder1\$ctl00\$rad_ThuTiet", "rad_ThuTiet")
//                 .add("ctl00\$ContentPlaceHolder1\$ctl00\$rad_MonHoc", "rad_MonHoc")
//                 .build()
//             request = Request.Builder()
//                 .url("http://uis.ptithcm.edu.vn/Default.aspx?page=thoikhoabieu&sta=1")
//                 .post(formBody)
//                 .build()
//             response = client.newCall(request).execute()
//
//             list["TKB2"] = response.body().string()




             // LOG OUT ================
             formBody = FormBody.Builder()
                 .add("__EVENTTARGET" , "ctl00\$Header1\$ucLogout\$lbtnLogOut")
                 .add("__EVENTARGUMENT" , "")
                 .add("__VIEWSTATEGENERATOR", "CA0B0334")
                 .build()

             request = Request.Builder()
                 .url("https://uis.ptithcm.edu.vn/default.aspx")
                 .post(formBody)
                 .build()

             client.newCall(request).execute()

             return list
         }
         catch (e : Exception){
             list["error"] = "Lỗi trong quá trình tải dữ liệu!"
             return list
         }
    }

    fun hasActiveInternetConnection(context: Context?): Boolean {
            try {
                val urlc: HttpURLConnection =
                    URL("https://www.google.com").openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connection", "close")
                urlc.connectTimeout = 1500
                urlc.connect()
                return urlc.responseCode === 200
            } catch (e: IOException) {
                //sLog.d("tncnhan", "no ping")
            }
        return false
    }



}