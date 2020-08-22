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
import com.baokiin.uisptit.databinding.FragmentExamScheduleBinding
import com.baokiin.uisptit.ui.info.AdapterExam
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class ExamScheduleFragment :Fragment(){
    val viewModel: ExamScheduleViewModel by viewModel<ExamScheduleViewModel>()
    lateinit var adapter:AdapterExam
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

        bd.btnLich.setOnClickListener {
            val cal = Calendar.getInstance()
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            val listExam = viewModel.listExamTime.value
            if (listExam != null) {
                for(i in listExam) {
                    val beginTime = dateToMillis(i.ngayThi) + beginObj(i.startTime)
                    val endTime = beginTime + 4 * 60 * 60 * 1000
                    intent.putExtra("beginTime", beginTime)
                    intent.putExtra("endTime", endTime )
                    intent.putExtra("title", i.phong + " - " + i.tenMon)
                    startActivity(intent)
                }
            }
        }
        return bd.root
    }

    fun dateToMillis(stringDate : String) : Long{
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(
            "dd/MM/yyyy", Locale.ROOT
        )
        val timeInMilliseconds: Long = OffsetDateTime.parse(stringDate, formatter)
            .toInstant()
            .toEpochMilli()
        return timeInMilliseconds
    }

    fun beginObj(num : Int) : Long{
        var res = 7 * 60 * 60 * 1000L
        return res
    }
}