package com.baokiin.uisptit.ui.option

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentOptionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class OptionFragment : Fragment(){
    val viewModel: OptionViewModel by viewModel<OptionViewModel>()
    lateinit var sp: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentOptionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_option,container,false)
        bd.lifecycleOwner=this
        //bd.viewmodel=viewModel
        sp = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        bd.button.setOnClickListener {
            viewModel.deleteLogin()
            sp.edit().clear().apply()
            findNavController().navigate(R.id.op_to_log)
        }
        return bd.root
    }
}