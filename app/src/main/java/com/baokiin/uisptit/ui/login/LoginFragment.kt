package com.baokiin.uisptit.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.LoginFragmentBinding
import kotlinx.android.synthetic.main.login_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(){
    val viewModel: LoginViewModel by viewModel<LoginViewModel>()
    lateinit var sp:SharedPreferences
    @SuppressLint("CommitPrefEdits")
    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: LoginFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        sp = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        if(sp.getBoolean("login",false))
            findNavController().navigate(R.id.login_to_infor)


        viewModel.bool.observe(viewLifecycleOwner, Observer {
            if(it == true){
                findNavController().navigate(R.id.login_to_infor)
                sp.edit().putBoolean("login",true).apply()
            }
            else if(it == false){
                bd.errorTv.text = "Sai tài khoản hoặc mật khẩu"
            }
        })

        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       login_button.setOnClickListener {
            viewModel.check(username_et.editText?.text.toString(),password_et.editText?.text.toString())
        }

        val span = SpannableString("Bằng cách Đăng Nhập, bạn đồng ý với chính sách bảo mật của chúng tôi.")
        span.setSpan(RelativeSizeSpan(1.3f),36,54,0)
        span.setSpan(UnderlineSpan(),36,54,0)
        span.setSpan(StyleSpan(Typeface.BOLD),36,54,0)
        span.setSpan(object :ClickableSpan(){
            override fun onClick(widget: View) {
                findNavController().navigate(R.id.to_link)
            }
        },36,54,0)
        textView4.movementMethod = LinkMovementMethod.getInstance()
        textView4.setText(span)
    }

    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { v, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().finish()
                true
            } else false
        }
    }



}
