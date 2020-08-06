package com.baokiin.uisptit.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
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

        bd.loginButton.setOnClickListener {
            viewModel.check(bd.usernameEt.editText?.text.toString(),bd.passwordEt.editText?.text.toString())

        }
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
