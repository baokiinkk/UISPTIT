package com.baokiin.uisptit.ui.mark

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.databinding.FragmentMarkBinding
import com.baokiin.uisptit.ui.base.BaseFragment

class MarkFragment : BaseFragment<MarkViewModel, FragmentMarkBinding>(), MainActivity.SetUpDataCalLBack {
    override fun getLayoutRes(): Int = R.layout.fragment_mark

    override fun setUpViews() {

    }

    override fun callBack() {
        baseViewModel.getData("120192020")
        baseViewModel.listData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                for (i in it) {
                    Log.d("quocbaokiin", i.toString())
                }
            }
        })
    }
}