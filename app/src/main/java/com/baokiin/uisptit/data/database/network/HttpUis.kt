package com.baokiin.uis.data.database.network

import android.content.Context
import com.baokiin.uis.data.database.domain.LoginInfor
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.jsoup.Jsoup
import kotlin.coroutines.resume

// T muốn là tạo 2 hàm 1 hàm kiểm tra login 1 hàm lấy list điểm nhưng mà t buồn ngủ quá rồi T_T làm hộ nha

class HttpUis(override var context: Context) : Network {
    override fun login(loginInfor: LoginInfor) : MutableList<String>  {
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

        val response = client.newCall(request).execute()
        request = Request.Builder()
            .url("http://uis.ptithcm.edu.vn/default.aspx?page=xemdiemthi")
            .get()
            .build()
        val ans =client.newCall(request).execute()
        val e = Jsoup.parse(ans.body().string())
        val name = e.select("tr.row-diem span.Label")
        val list :MutableList<String> = mutableListOf()

        for(x in name){
            list.add(x.text())
        }
        return list
    }

    override suspend fun getMark(): MutableList<String> {
        return mutableListOf()
    }


}