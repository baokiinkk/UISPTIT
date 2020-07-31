package com.baokiin.uisptit

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.baokiin.uis.ui.BaseActivity
import com.baokiin.uisptit.databinding.ActivityMainBinding
import com.baokiin.uisptit.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object{
        val LOGIN_CODE = 1901
    }

    private val startIntent: Intent by lazy {
        Intent(this, StartActivity::class.java)
    }

    private val navController : NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun setUpViews() {
        startActivityForResult(startIntent, LOGIN_CODE)
        val navView: BottomNavigationView = baseViewBinding.navView//findViewById(R.id.nav_view)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
        //    R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN_CODE){
            if(resultCode == Activity.RESULT_OK){
                try {
                    (navHostFragment?.childFragmentManager?.fragments?.get(0) as HomeFragment).callBack()
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
