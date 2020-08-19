package com.baokiin.uisptit.ui.examschedule

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentExamScheduleBinding
import com.baokiin.uisptit.ui.info.AdapterExam

import org.koin.androidx.viewmodel.ext.android.viewModel

class ExamScheduleFragment :Fragment(){
    val viewModel: ExamScheduleViewModel by viewModel<ExamScheduleViewModel>()
    lateinit var adapter:AdapterExam
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        val bd: FragmentExamScheduleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exam_schedule,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel= viewModel
        viewModel.getData()
        adapter = AdapterExam()
        bd.recycleView.layoutManager = LinearLayoutManager(context)
        bd.recycleView.adapter = adapter
        viewModel.listExamTime.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("quocbaokiin",it.toString())
                adapter.submitList(it)
            }
        })
        return bd.root
    }
}