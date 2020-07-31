package com.baokiin.uisptit.ui.home

import androidx.lifecycle.Observer
import com.baokiin.uis.ui.BaseFragment
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(), MainActivity.MarkCallBack {

    override fun callBack() {
        baseViewModel.getMark()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_home

    override fun setUpViews() {
//        baseViewModel.markLiveData.observe(this, Observer {
//            if(it == null) return@Observer
//            for(x in it){
//                println(x)
//            }
//        })
    }
}