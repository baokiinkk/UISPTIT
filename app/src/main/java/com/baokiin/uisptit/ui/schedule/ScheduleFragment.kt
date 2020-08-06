package com.baokiin.uisptit.ui.schedule

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentScheduleBinding
import com.db.williamchart.slidertooltip.SliderTooltip
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.MutableMap.MutableEntry


class ScheduleFragment : Fragment() {
    val viewModel: ScheduleViewModel by viewModel<ScheduleViewModel>()
    @SuppressLint("Range")
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