package com.baokiin.uisptit.ui.info

import com.baokiin.uis.ui.BaseFragment
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment<InfoViewModel, FragmentInfoBinding>(), MainActivity.MarkCallBack {

    override fun callBack() {
        baseViewModel.getMark()
    }

    override fun getLayoutRes(): Int = R.layout.fragment_info

    override fun setUpViews() {
//        baseViewModel.markLiveData.observe(this, Observer {
//            if(it == null) return@Observer
//            for(x in it){
//                println(x)
//            }
//        })
    }
}