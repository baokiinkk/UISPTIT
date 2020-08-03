package com.baokiin.uisptit.ui.examschedule

import android.util.Log
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.ui.base.BaseFragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentExamScheduleBinding

class ExamScheduleFragment : BaseFragment<ExamScheduleViewModel, FragmentExamScheduleBinding>(), MainActivity.SetUpDataCalLBack {
    override fun getLayoutRes(): Int  = R.layout.fragment_exam_schedule

    override fun setUpViews() {

    }

    override fun callBack() {
        Log.d("Choose Fragment", "Exam Schedule Fragment")
    }
}