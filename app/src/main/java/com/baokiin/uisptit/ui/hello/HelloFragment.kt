package com.baokiin.uisptit.ui.hello

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.HelloFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelloFragment : Fragment() {
    val viewModel: HelloViewModel by viewModel<HelloViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: HelloFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.hello_fragment, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel

        viewModel.check()
        viewModel.isCheck.observe(viewLifecycleOwner, Observer {
            if(it == true){
                findNavController().navigate(R.id.login_to_infor)
            }
            else if( it == false)
                findNavController().navigate(R.id.to_login)
        })
        return bd.root
    }
}