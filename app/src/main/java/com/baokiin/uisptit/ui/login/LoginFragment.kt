package com.baokiin.uisptit.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.LoginFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    val viewModel: LoginViewModel by viewModel<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: LoginFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel

        viewModel.deleteLogin()

        bd.loginButton.setOnClickListener {
            viewModel.check(bd.usernameEt.editText?.text.toString(),bd.passwordEt.editText?.text.toString())

        }

        viewModel.bool.observe(viewLifecycleOwner, Observer {
            if(it == true){
                findNavController().navigate(R.id.login_to_infor)
            }
            else if(it == false){
                bd.errorTv.text = "Sai tài khoản hoặc mật khẩu"
            }
        })

        return bd.root
    }

}