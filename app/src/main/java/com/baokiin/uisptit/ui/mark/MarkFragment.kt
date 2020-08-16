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
    lateinit var list:MutableList<ListMark>
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
        viewModel.getDataSemester("")
        val adapter = Adapter()
        bd.viewpager.adapter = adapter
        viewModel.listData.observe(viewLifecycleOwner, Observer { mark->
            mark?.let {
                viewModel.listDataSemester.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        list = mutableListOf()
                        var tmp: MutableList<Mark> = mutableListOf()
                        var str = mark[0].semester
                        var j = 0
                        tmp.add(mark[0])
                        for (i in 1..mark.size - 1) {
                            if (mark[i].semester.equals(str)) {
                                tmp.add(mark[i])
                                if (i == mark.size - 1)
                                    list.add(ListMark(decodeSemester(str), tmp, it[j]))
                            } else {
                                list.add(ListMark(decodeSemester(str), tmp,it[j]))
                                j++
                                tmp = mutableListOf()
                                tmp.add(mark[i])
                                str = mark[i].semester
                            }
                        }
                        adapter.submitList(list)
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