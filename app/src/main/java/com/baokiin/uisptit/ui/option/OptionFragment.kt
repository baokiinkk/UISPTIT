package com.baokiin.uisptit.ui.option

import android.util.Log
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.ui.base.BaseFragment
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentOptionBinding

class OptionFragment : BaseFragment<OptionViewModel, FragmentOptionBinding>(), MainActivity.SetUpDataCalLBack {
    override fun getLayoutRes(): Int = R.layout.fragment_option

    override fun setUpViews() {

    }

    override fun callBack() {
        Log.d("Choose Fragment", "Option Fragment")
    }
}