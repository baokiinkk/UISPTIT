package com.baokiin.uisptit.ui.info
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentInfoBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class InfoFragment : Fragment(){
    val viewModel: InfoViewModel by viewModel<InfoViewModel>()
    lateinit var currentTime: Date
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel

        viewModel.getData("220192020")
        val adapter = AdapterMark()
        bd.recycleViewDiem.adapter = adapter
        bd.recycleViewDiem.layoutManager = LinearLayoutManager(context)
        currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("HH:mm dd/MM/yy")
        bd.txtTime.text = "Cập nhật lúc: "+sdf.format(currentTime).toString()
        bd.txtBuoi.text = getBuoi()
        viewModel.listData.observe(viewLifecycleOwner, Observer {
               it.let {
                   adapter.submitList(it)
               }
        })

        viewModel.listSemester.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                val entries = ArrayList<Entry>()
                for(i in 0 until it.size){
                    entries.add(Entry(i.toFloat(),it[i].gpa4))
                }
                val hocki = ArrayList<String>()
                for(i in 0 until it.size){
                    hocki.add(it[i].semester)
                }

                val formatter: ValueFormatter =
                    object : ValueFormatter() {
                        override fun getAxisLabel(value: Float, axis: AxisBase): String {
                            return hocki[value.toInt()]
                        }
                    }

                val xAxis = bd.linechart.xAxis
                xAxis.granularity = 1f
                xAxis.labelRotationAngle = -45f
                xAxis.valueFormatter = formatter
                val dataset = LineDataSet(entries, "Điểm Tích Lũy")
                val data: ArrayList<ILineDataSet> = ArrayList()
                data.add(dataset)
                val lineData: LineData = LineData(data)
                bd.linechart.axisRight.isEnabled = false
                bd.linechart.data = lineData
                bd.linechart.invalidate()
            }
        })



        return bd.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        cardTKB.setOnClickListener {
            findNavController().navigate(R.id.to_schedule)
        }
        cardDiem.setOnClickListener {
            findNavController().navigate(R.id.to_mark)
        }
        btnOption.setOnClickListener {
            findNavController().navigate(R.id.to_option)
        }

        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        fresh.setWaveRGBColor(3,218,197)
        fresh.setColorSchemeColors(Color.WHITE)
        fresh.setShadowRadius(0)
        fresh.setOnRefreshListener {
            fresh.postDelayed(
                Runnable {
                    if(isConnected) {
                        viewModel.reload()
                        viewModel.getData("220192020")
                        currentTime = Calendar.getInstance().time
                        val sdf = SimpleDateFormat("HH:mm dd/MM/yy")
                        txtTime.text = "Cập nhật lúc: "+sdf.format(currentTime).toString()
                        Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        viewModel.getData("220192020")
                        Toast.makeText(context,"Không kết nối được!",Toast.LENGTH_SHORT).show()
                    }
                    fresh.isRefreshing = false
                }, 2000
            )
        }
    }
    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { v, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().finish()
                true
            } else false
        }
    }

    fun getBuoi() :String{
        val sdf = SimpleDateFormat("HH")
        val hour = sdf.format(currentTime).toInt()
        if(hour < 12 )
            return "Chúc ngày mới tốt lành!"
        else if(hour < 15)
            return "Chúc buổi trưa vui vẻ!"
        else if(hour < 19)
            return "Chúc buổi chiều mát mẻ!"
        else
            return "Chúc buổi tối ...!"
    }

}

