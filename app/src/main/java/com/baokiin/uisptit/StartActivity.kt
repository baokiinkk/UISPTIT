package com.baokiin.uisptit

import android.app.Activity
import android.content.Intent
import com.baokiin.uis.data.repository.login.LoginInfor
import com.baokiin.uis.ui.BaseActivity
import com.baokiin.uisptit.ui.login.LoginFragment
import com.baokiin.uisptit.databinding.ActivityStartBinding

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



}