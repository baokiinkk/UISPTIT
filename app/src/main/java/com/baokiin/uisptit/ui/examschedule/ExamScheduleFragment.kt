package com.baokiin.uisptit.ui.examschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentExamScheduleBinding

import org.koin.androidx.viewmodel.ext.android.viewModel

class ExamScheduleFragment :Fragment(){
    val viewModel: ExamScheduleViewModel by viewModel<ExamScheduleViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentExamScheduleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exam_schedule,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel=viewModel
        return bd.root
    }
}