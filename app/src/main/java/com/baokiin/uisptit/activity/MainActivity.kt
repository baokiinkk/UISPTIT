package com.baokiin.uisptit.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.baokiin.uisptit.R

class MainActivity : AppCompatActivity(){
    lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun check(){

    }
    override fun onBackPressed() {
        if(!findNavController(R.id.mobile_navigation).popBackStack())
        {
            finish()
        }

    }
}
