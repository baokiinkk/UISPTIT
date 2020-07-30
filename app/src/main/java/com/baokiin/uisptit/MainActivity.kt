package com.baokiin.uisptit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.baokiin.uis.ui.BaseActivity
import com.baokiin.uisptit.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object{
        val LOGIN_CODE = 1901
    }

    private val startIntent: Intent by lazy {
        Intent(this, StartActivity::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun setUpViews() {
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

       startActivityForResult(startIntent, LOGIN_CODE)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN_CODE){
            if(resultCode == Activity.RESULT_OK){
                try {

                }catch (e: Exception){
                    println(e.message.toString())
                }
            }
        }
    }

    interface MarkCallBack{
        fun callBack()
    }
}
