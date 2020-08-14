package com.baokiin.uisptit.ui.mark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentMarkBinding
import com.baokiin.uisptit.ui.info.AdapterMark

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
        viewModel.getData("220192020")
        val adapter = AdapterMark(){}
        bd.recycleViewDiem.adapter = adapter
        bd.recycleViewDiem.layoutManager = LinearLayoutManager(context)
        viewModel.listData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        return bd.root
    }
}