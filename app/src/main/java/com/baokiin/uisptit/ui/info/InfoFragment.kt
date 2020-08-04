package com.baokiin.uisptit.ui.info

import android.util.Log
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.baokiin.uisptit.ui.base.BaseFragment
import com.baokiin.uisptit.MainActivity
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentInfoBinding
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : BaseFragment<InfoViewModel, FragmentInfoBinding>(), MainActivity.SetUpDataCalLBack {

    var listAdapterMark:adapterMark? = null
    override fun getLayoutRes(): Int = R.layout.fragment_info

    override fun setUpViews() {

        baseViewModel.getData("120192020")
        baseViewModel.listData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                listAdapterMark = adapterMark {
                }
                listAdapterMark!!.submitList(it)

                recycleViewDiem.adapter = listAdapterMark
            }
        })


        cardDiem.setOnClickListener {
            findNavController().navigate(R.id.to_mark)
        }
        cardTKB.setOnClickListener {
            findNavController().navigate(R.id.to_schedule)
        }
        cardExem.setOnClickListener {
            findNavController().navigate(R.id.to_exam)
        }
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