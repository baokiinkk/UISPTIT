package com.baokiin.uisptit.ui.mark

import android.util.Log
import androidx.lifecycle.Observer
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.databinding.FragmentMarkBinding
import com.baokiin.uisptit.ui.base.BaseFragment

class MarkFragment : BaseFragment<MarkViewModel, FragmentMarkBinding>(), MainActivity.SetUpDataCalLBack {
    override fun getLayoutRes(): Int = R.layout.fragment_mark

    private val listDataObserver : Observer<MutableList<Mark>?> by lazy {
        Observer<MutableList<Mark>?> {
            if (it != null) {
                for (i in it) {
                    Log.d("quocbaokiin", i.toString())
                }
            }
        }
    }
    override fun setUpViews() {
        baseViewModel.listData.observe(this, listDataObserver)
        baseBinding.logMark.setOnClickListener {
            baseViewModel.getData("")
        }
    }

    override fun callBack() {
        //baseViewModel.getData("120192020")
        Log.d("Choose Fragment", "Mark Fragment")
    }
}