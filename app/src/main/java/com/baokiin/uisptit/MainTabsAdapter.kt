package com.baokiin.uisptit

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModel
import com.baokiin.uisptit.ui.base.BaseFragment
import com.baokiin.uisptit.ui.examschedule.ExamScheduleFragment
import com.baokiin.uisptit.ui.info.InfoFragment
import com.baokiin.uisptit.ui.mark.MarkFragment
import com.baokiin.uisptit.ui.option.OptionFragment
import com.baokiin.uisptit.ui.schedule.ScheduleFragment

class MainTabsAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {
    private val listFragment: MutableList<BaseFragment<out ViewModel, out ViewDataBinding>> by lazy {
        mutableListOf<BaseFragment<out ViewModel, out ViewDataBinding>>(
            ScheduleFragment(),
            MarkFragment(),
            InfoFragment(),
            ExamScheduleFragment(),
            OptionFragment()
        )
    }
    private val listTitle: MutableList<String> by lazy {
        mutableListOf(
            "Schedule",
            "Mark",
            "Info",
            "Exam Schedule",
            "Option"
        )
    }

    public fun setUpData(){
        for(item in listFragment){
            (item as MainActivity.SetUpDataCalLBack).callBack()
        }
    }

    override fun getItem(position: Int): Fragment = listFragment[position]

    override fun getPageTitle(position: Int): CharSequence? = listTitle[position]

    override fun getCount(): Int = listFragment.size
}