package com.baokiin.uisptit.ui.mark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentMarkBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

class MarkFragment :Fragment() {
    val viewModel: MarkViewModel by viewModel<MarkViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentMarkBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_mark,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel=viewModel
        return bd.root
    }
}