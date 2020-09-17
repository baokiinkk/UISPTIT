package com.baokiin.uisptit.ui.mark

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.ListMark
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.SemesterMark
import com.baokiin.uisptit.databinding.FragmentMarkBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_mark.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class MarkFragment :Fragment() {
    private val viewModel: MarkViewModel by viewModel()
    private var tabLayoutMediator:TabLayoutMediator? = null
    lateinit var list:MutableList<ListMark>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentMarkBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_mark,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel=viewModel
//
        viewModel.getData("")
        requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        viewModel.getDataSemester("")
        val adapter = Adapter()
        bd.viewpager.adapter = adapter
        viewModel.listData.observe(viewLifecycleOwner, Observer { mark->
            mark?.let {
                viewModel.listDataSemester.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        list = mutableListOf()
                        var tmp: MutableList<Mark> = mutableListOf()
                        val tmpSemester:MutableList<SemesterMark> = mutableListOf()
                        var str = mark[0].semester
                        if(str == "baoLuu")
                            tmpSemester.add( SemesterMark("",0f,0f,0f,0f,0,0))
                        var j = 0
                        tmpSemester.addAll(it)
                        tmp.add(mark[0])
                        for (i in 1 until mark.size) {
                            if (mark[i].semester == str) {
                                tmp.add(mark[i])
                                if (i == mark.size - 1) {
                                    if(j>=it.size)
                                        tmpSemester.add( SemesterMark("",0f,0f,0f,0f,0,0))
                                    list.add(ListMark(decodeSemester(str), tmp, tmpSemester[j]))
                                }
                            } else {
                                if(j>=it.size)
                                    tmpSemester.add( SemesterMark("",0f,0f,0f,0f,0,0))
                                list.add(ListMark(decodeSemester(str), tmp,tmpSemester[j]))
                                j++
                                tmp = mutableListOf()
                                tmp.add(mark[i])
                                str = mark[i].semester
                            }
                        }
                        adapter.submitList(list)
                        bd.viewpager.setCurrentItem(list.size-1,true)
                    }
                })

            }
        })


        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnXoay.setOnClickListener {
            if(requireActivity().requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT)
                requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            else
                requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }
        tabLayoutMediator = TabLayoutMediator(
            tabLayout,
            viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = (position + 1).toString()
            }
        )
        tabLayoutMediator!!.attach()
    }

    private fun decodeSemester(str : String) : String{
        return if (str == "baoLuu"){
            "Điểm Bảo Lưu"
        } else{
            "Học kỳ " + str.substring(0, 1) + " - Năm học " + str.substring(1, 5) + "-" + str.substring(5, 9)
        }
    }
}