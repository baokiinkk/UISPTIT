package com.baokiin.uisptit

import android.app.Activity
import android.content.Intent
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uis.ui.BaseActivity
import com.baokiin.uisptit.ui.login.LoginFragment
import com.baokiin.uisptit.databinding.ActivityStartBinding
import kotlinx.android.synthetic.main.activity_start.*
import java.lang.Exception

class StartActivity : BaseActivity<ActivityStartBinding>(), LoginFragment.LoginCallBack {

    override fun getLayoutRes(): Int = R.layout.activity_start

    override fun setUpViews() {

    }
    companion object{
        const val LOGIN_SUCCESS = "Login_Success"
    }

    override fun success(loginInfor: LoginInfor) {
        val intent: Intent = Intent()
        intent.putExtra(LOGIN_SUCCESS, loginInfor)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun setUpData(): LoginInfor? {
        val serializableExtra = intent.getSerializableExtra(MainActivity.LOGIN_FORGOT)
        return if(serializableExtra == null)
            null
        else
            serializableExtra as LoginInfor
    }

    override fun onBackPressed() {
//        try {
//            (my_nav_host_fragment?.childFragmentManager?.fragments?.get(0) as LoginFragment)
//            finishAffinity()
//        }catch (e: Exception){
//            super.onBackPressed()
//        }
    }
}