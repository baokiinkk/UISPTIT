package com.baokiin.uisptit.ui.mark

import android.util.Log
import androidx.lifecycle.Observer
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.ui.base.BaseFragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.databinding.FragmentMarkBinding

class MarkFragment : BaseFragment<MarkViewModel, FragmentMarkBinding>(), MainActivity.SetUpDataCalLBack {
    override fun getLayoutRes(): Int = R.layout.fragment_mark

    private val listDataObserver : Observer<MutableList<SemesterMark>?> by lazy {
        Observer<MutableList<SemesterMark>?> {
            if (it != null) {
                Log.d("quocbaokiin", it.size.toString())
                for (i in it) {
                    Log.d("quocbaokiin", i.toString())
                }
            }
        }
    }

    override fun setUpViews() {
        baseViewModel.getData("")
        baseViewModel.listData.observe(viewLifecycleOwner, listDataObserver)
    }

    override fun callBack() {

    }
}