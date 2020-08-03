package com.baokiin.uisptit.ui.info

import android.util.Log
import com.baokiin.uisptit.ui.base.BaseFragment
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment<InfoViewModel, FragmentInfoBinding>(), MainActivity.SetUpDataCalLBack {

    override fun getLayoutRes(): Int = R.layout.fragment_info

    override fun setUpViews() {
//        baseViewModel.markLiveData.observe(this, Observer {
//            if(it == null) return@Observer
//            for(x in it){
//                println(x)
//            }
//        })
    }

    override fun callBack() {
        Log.d("Choose Fragment", "Info Fragment")
    }
}