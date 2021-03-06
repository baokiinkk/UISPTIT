package com.baokiin.uisptit.ui.option

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentOptionBinding
import kotlinx.android.synthetic.main.fragment_option.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class OptionFragment : Fragment(){
    private val viewModel: OptionViewModel by viewModel()
    private lateinit var sp: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentOptionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_option,container,false)
        bd.lifecycleOwner=this
        bd.data=viewModel
        viewModel.getData()
        this.sp = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                bd.viewmodel = it
            }
        })

        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            viewModel.deleteLogin()
            sp.edit().clear().apply()
            findNavController().navigate(R.id.op_to_log)
        }

    }
    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }

    }
}