package com.baokiin.uisptit.ui.examschedule

import android.content.Intent
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
import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.databinding.FragmentExamScheduleBinding
import com.baokiin.uisptit.ui.info.AdapterExam
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class ExamScheduleFragment :Fragment(){
    private val viewModel: ExamScheduleViewModel by viewModel()
    private lateinit var adapter:AdapterExam
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        val bd: FragmentExamScheduleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exam_schedule,container,false)
        bd.lifecycleOwner=this
        bd.viewmodel= viewModel
        viewModel.getData()
        adapter = AdapterExam{}
        bd.recycleView.layoutManager = LinearLayoutManager(context)
        bd.recycleView.adapter = adapter
        viewModel.listExamTime.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
            //nút lịch
//        bd.btnLich.setOnClickListener {
//            val intent = Intent(Intent.ACTION_EDIT)
//            intent.type = "vnd.android.cursor.item/event"
//            val listExam = viewModel.listExamTime.value
//            if (listExam != null) {
//                for(i in listExam) {
//                    addCalendar(i)
//                }
//            }
//        }
        return bd.root
    }
    private fun addCalendar(obj : ExamTimetable){
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        val beginTime = dateToMillis(obj.ngayThi) + beginObj(obj.startTime)
        val endTime = beginTime + 4 * 60 * 60 * 1000
        intent.putExtra("beginTime", beginTime)
        intent.putExtra("endTime", endTime )
        intent.putExtra("description", obj.ghichu)
        intent.putExtra("title", obj.phong + " - " + obj.tenMon)
        startActivity(intent)
    }


    private fun dateToMillis(stringDate : String) : Long{
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val mDate = sdf.parse(stringDate)
        return mDate.time
    }

    private fun beginObj(num : Int) : Long{
        return 7 * 60 * 60 * 1000L
    }
}