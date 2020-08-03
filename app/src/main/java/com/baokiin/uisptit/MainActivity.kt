package com.baokiin.uisptit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.baokiin.uis.ui.BaseActivity
import com.baokiin.uisptit.StartActivity.Companion.LOGIN_SUCCESS
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.databinding.ActivityMainBinding
import com.baokiin.uisptit.ui.info.InfoFragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson

class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object {
        const val LOGIN_CODE = 1901
        const val LOGIN_FORGOT = "LOGIN_FORGOT"
    }

    private val startIntent: Intent by lazy {
        Intent(this, StartActivity::class.java)
    }

    private fun getLoginFromSharedPreferences(): LoginInfor? {
        val string = pref.getString(LOGIN_FORGOT, null)
        return gson.fromJson<LoginInfor>(string, LoginInfor::class.java)
    }

    private val pref: SharedPreferences by lazy {
        getSharedPreferences(LOGIN_FORGOT, MODE_PRIVATE)
    }

    private val gson: Gson by lazy {
        Gson()
    }

    private val tabs: TabLayout by lazy {
        baseViewBinding.tabs
    }

    private val mainAdapter: MainTabsAdapter by lazy {
        MainTabsAdapter(supportFragmentManager)
    }

    private val setUpData: TabLayout.OnTabSelectedListener by lazy {
        object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { mainAdapter.setUpData(it) }
            }
        }
    }

//    private val navController : NavController by lazy {
//        findNavController(R.id.nav_host_fragment)
//    }

//    private val navHostFragment by lazy {
//        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//    }
//    val navView: BottomNavigationView by lazy{
//        baseViewBinding.navView
//    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setUpViews() {
        startActivityForResult(startIntent, LOGIN_CODE)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
        //    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        // navView.setupWithNavController(navController)
        baseViewBinding.viewPager.adapter = mainAdapter
        tabs.setupWithViewPager(baseViewBinding.viewPager)
        tabs.addOnTabSelectedListener(setUpData)
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    @SuppressLint("CommitPrefEdits")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOGIN_CODE) {
            if (resultCode == Activity.RESULT_OK) {
//                navView.selectedItemId = R.id.navigation_infor
//                (navHostFragment?.childFragmentManager?.fragments?.get(0) as InfoFragment).callBack()
                //mainAdapter.setUpData(tabs.selectedTabPosition)
//                val loginInfor: LoginInfor = data?.getSerializableExtra(LOGIN_SUCCESS) as LoginInfor
//                editor.putString(LOGIN_FORGOT, gson.toJson(loginInfor))
//                editor.commit()
                tabs.getTabAt(mainAdapter.count / 2)?.select()
                //mainAdapter.setUpData(index)
            }
        }
    }

    //Interface này được sử dụng vì khi đăng nhập có 2 trường hợp
    //Thứ nhất: Đăng nhập lần đầu hàm callBack sẽ được gọi sau khi đăng nhập
    //Thứ 2: Đăng nhập lần thứ 2 hàm callBack sẽ được gọi sau khi lấy dữ liệu
    interface SetUpDataCalLBack {
        fun callBack()
    }
}
