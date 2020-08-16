package com.baokiin.uisptit.ui.mark

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.ListMark
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.databinding.FragmentMarkBinding
import com.baokiin.uisptit.ui.info.AdapterMark
import kotlinx.android.synthetic.main.fragment_mark.*

import org.koin.androidx.viewmodel.ext.android.viewModel

class MarkFragment :Fragment() {
    val viewModel: MarkViewModel by viewModel<MarkViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentMarkBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_mark,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel=viewModel
//        requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        viewModel.getData("")
        val adapter = Adapter()
        bd.viewpager.adapter = adapter
        viewModel.listData.observe(viewLifecycleOwner, Observer {
            it?.let {
                val list:MutableList<ListMark> = mutableListOf()
                var tmp: MutableList<Mark> = mutableListOf()
                var str = it[0].semester.toString()

                tmp.add(it[0])
                for(i in 1..it.size-1){
                    if(it[i].semester.equals(str)){
                        tmp.add(it[i])
                        if(i == it.size-1)
                            list.add(ListMark(decodeSemester(str),tmp))
                    }
                    else{
                        list.add(ListMark(decodeSemester(str),tmp))
                        tmp = mutableListOf()
                        tmp.add(it[i])
                        str = it[i].semester
                    }
                }
                adapter.submitList(list)
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
    }

    fun decodeSemester(str : String) : String{
        if (str.equals("baoLuu")){
            return "Điểm Bảo Lưu"
        }
        else{
            return "Học kỳ " + str.substring(0, 1) + " - Năm học " + str.substring(1, 5) + "-" + str.substring(5, 9)
        }
    }
}