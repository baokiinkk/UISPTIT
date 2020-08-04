package com.baokiin.uisptit.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.LoginInfor
import com.baokiin.uisptit.databinding.FragmentLoginBinding
import com.baokiin.uisptit.ui.base.BaseFragment
import com.google.gson.Gson
import org.koin.android.ext.android.get


typealias EditEvent = (Editable?) -> Unit


@Suppress("CAST_NEVER_SUCCEEDS")
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {

    private val editor : SharedPreferences.Editor? by lazy {
        activity?.getSharedPreferences(MainActivity.LOGIN_FORGOT, AppCompatActivity.MODE_PRIVATE)?.edit()
    }

    private val gson: Gson by lazy {
        Gson()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_login
    private var callBack: LoginCallBack? = null
    private val inputManager: InputMethodManager by lazy {
        activity?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private val loginAction: View.OnClickListener by lazy {
        View.OnClickListener {
            Log.d("quocbaokiin","Dang nhap")
            // Tạo delay ở
            loginInfor.username = baseBinding.usernameEt.editText?.text.toString()
            loginInfor.password = baseBinding.passwordEt.editText?.text.toString()
            activity?.currentFocus?.let {
                inputManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
            baseViewModel.login(loginInfor)
        }
    }
    private val changeInput: EditEvent by lazy {
        object : EditEvent {
            override fun invoke(editable: Editable?) {
                baseBinding.errorTv.text = ""
            }
        }
    }

    private val isLoginObserver: Observer<Boolean?> by lazy {
        Observer<Boolean?> {
            // Kết thúc delay ở
            if (it == null) {
                baseBinding.errorTv.text = getString(R.string.login_false)
                baseViewModel.isLogin.value = false
            } else if (it == true) {
                callBack?.success(loginInfor)
            }
        }
    }

    private lateinit var loginInfor: LoginInfor

    override fun setUpViews() {
        loginInfor = get()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        baseBinding.loginButton.setOnClickListener(loginAction)
        baseBinding.usernameEt.editText?.doAfterTextChanged(changeInput)
        baseBinding.passwordEt.editText?.doAfterTextChanged(changeInput)
        baseViewModel.isLogin.observe(this, isLoginObserver)

        if (callBack?.setUpData() != null) {
            loginInfor = callBack?.setUpData()!!
            baseBinding.usernameEt.editText?.setText(loginInfor.username)
            baseBinding.passwordEt.editText?.setText(loginInfor.password)
            baseViewModel.login(loginInfor)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginCallBack) {
            callBack = context
        }
    }

    interface LoginCallBack {
        fun success(loginInfor: LoginInfor)
        fun setUpData(): LoginInfor?
    }

    override fun onDestroy() {
        super.onDestroy()
        editor?.putString(MainActivity.LOGIN_FORGOT, gson.toJson(loginInfor))
        editor?.commit()
    }
}