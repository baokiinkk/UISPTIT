package com.baokiin.uisptit.ui.schedule

import android.util.Log
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.ui.base.BaseFragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentScheduleBinding


class ScheduleFragment : BaseFragment<ScheduleViewModel, FragmentScheduleBinding>(), MainActivity.SetUpDataCalLBack {
    override fun getLayoutRes(): Int = R.layout.fragment_schedule

    override fun setUpViews() {

    }

    override fun callBack() {
        Log.d("Choose Fragment", "Schedule Fragment")
    }
}