package com.baokiin.uisptit.ui.schedule

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.ListTableTime
import com.baokiin.uisptit.data.db.model.TimeTable
import com.baokiin.uisptit.databinding.FragmentScheduleBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_mark.tabLayout
import kotlinx.android.synthetic.main.fragment_mark.viewpager
import kotlinx.android.synthetic.main.fragment_schedule.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ScheduleFragment : Fragment() {
    private val viewModel: ScheduleViewModel by viewModel()
    lateinit var list:MutableList<ListTableTime>
    private var tabLayoutMediator:TabLayoutMediator? = null
    private lateinit var dataSpinner:MutableList<String>
    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        val bd: FragmentScheduleBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_schedule,container,false)
        bd.lifecycleOwner=this
        bd.data=viewModel
            viewModel.getData()
        val adapter = Adapter{
        }
        bd.viewpager.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.size <= 1) {
                    Toast.makeText(context, "Chưa có thời khóa biểu", Toast.LENGTH_LONG).show()
                } else {
                    list = mutableListOf()
                    var tmp: MutableList<TimeTable> = mutableListOf()
                    var str = it[0].tuan
                    dataSpinner = mutableListOf()
                    tmp.add(it[0])
                    for (i in 1 until it.size) {
                        if (it[i].tuan == str) {
                            tmp.add(it[i])
                        }
                        else {
                            list.add(ListTableTime(decodeSemester(str), tmp))
                            dataSpinner.add(decodeSemester(str))
                            tmp = mutableListOf()
                            tmp.add(it[i])
                            str = it[i].tuan
                        }
                        if(i == it.size-1){
                            list.add(ListTableTime(decodeSemester(str), tmp))
                            dataSpinner.add(decodeSemester(str))
                        }
                    }
                    adapter.submitList(list)


                    val spinnerAdapter:ArrayAdapter<String> = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,dataSpinner)
                    bd.spinner.adapter = spinnerAdapter
                    bd.spinner.setSelection(getCurrentWeek(dataSpinner))

                }
            }
        })
        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayoutMediator = TabLayoutMediator(
            tabLayout,
            viewpager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = (position + 1).toString()
                //spinner?.setSelection(position)
            }
        )
        tabLayoutMediator!!.attach()

        spinner?.onItemSelectedListener = object :OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewpager.setCurrentItem(0,true)
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewpager.setCurrentItem(position,true)
            }


        }

        fresh_schedule.setWaveRGBColor(99,80,200)
        fresh_schedule.setColorSchemeColors(Color.WHITE)
        fresh_schedule.setShadowRadius(0)
        fresh_schedule.setOnRefreshListener {
            viewpager.setCurrentItem(getCurrentWeek(dataSpinner))
            fresh_schedule.isRefreshing = false

        }

    }
    private fun decodeSemester(str : String) : String{
        return "Tuần "+ str.substring(0,2)+" Từ "+str.substring(2,4)+"/"+str.substring(4,6)+"/"+str.substring(6,10)+" Đến "+str.substring(10,12)+"/"+str.substring(12,14)+"/"+str.substring(14,18)
    }
    private fun getCurrentWeek(data:MutableList<String>) :Int{
        val date = Date()
        var firstWeek= true
        for(i in data){
            val startDate = toDate(i.substring(11,21))
            val endDate = toDate(i.substring(26,36))
            val num = i.substring(5,7)
            if(firstWeek){
                firstWeek = false
                if(date.before(startDate))
                    return 0
            }
            if(date.before(endDate) && (date.after(startDate) || date == startDate))
                return (num.toInt())-1
        }
        return data.size-1
    }
    @SuppressLint("SimpleDateFormat")
    fun toDate(str:String): Date {
        val sdf =
            SimpleDateFormat("dd/MM/yyyy")
        return sdf.parse(str)
    }
}

