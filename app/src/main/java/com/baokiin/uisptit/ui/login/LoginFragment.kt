package com.baokiin.uisptit.ui.login

import android.content.Context
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.baokiin.uis.data.database.domain.LoginInfor
import com.baokiin.uis.ui.BaseFragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentLoginBinding
import org.koin.android.ext.android.inject


typealias EditEvent = (Editable?) -> Unit


@Suppress("CAST_NEVER_SUCCEEDS")
class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {


    override fun getLayoutRes(): Int = R.layout.fragment_login
    private var callBack: LoginCallBack? = null
    private val inputManager: InputMethodManager by lazy {
        activity?.applicationContext?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
    private val loginAction: View.OnClickListener by lazy {
        View.OnClickListener {
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

    private val loginInfor: LoginInfor by inject<LoginInfor>()

    override fun setUpViews() {
        baseBinding.loginButton.setOnClickListener(loginAction)
        baseBinding.usernameEt.editText?.doAfterTextChanged(changeInput)
        baseBinding.passwordEt.editText?.doAfterTextChanged(changeInput)
        baseViewModel.isLogin.observe(viewLifecycleOwner, Observer {
            // Kết thúc delay ở
            if(it == null){
                baseBinding.errorTv.text = getString(R.string.login_false)
                baseViewModel.isLogin.value = false
            }
            else if(it == true){
                callBack?.success(loginInfor)
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is LoginCallBack){
            callBack = context
        }
    }

    interface LoginCallBack{
        fun success(loginInfor: LoginInfor)
    }
}