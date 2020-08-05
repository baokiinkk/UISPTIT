package com.baokiin.uisptit.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentScheduleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ScheduleFragment : Fragment() {
    val viewModel: ScheduleViewModel by viewModel<ScheduleViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentScheduleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_schedule,container,false)
        bd.lifecycleOwner=this
      //  bd.viewmodel=viewModel
        return bd.root
    }
}