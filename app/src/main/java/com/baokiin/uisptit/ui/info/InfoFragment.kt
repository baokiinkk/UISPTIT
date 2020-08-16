package com.baokiin.uisptit.ui.info
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentInfoBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.fragment_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class InfoFragment : Fragment(){
    val viewModel: InfoViewModel by viewModel<InfoViewModel>()
    lateinit var currentTime: Date
    lateinit var sp:SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        val bd: FragmentInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        viewModel.getData("220192020")
        val adapterExam = AdapterExam()
        sp = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        val adapter = AdapterMark {
            findNavController().navigate(R.id.to_mark)
        }

        bd.recycleViewDiem.adapter = adapter
        bd.recycleViewDiem.layoutManager = LinearLayoutManager(context)


        bd.recycleExam.adapter = adapterExam
        bd.recycleExam.layoutManager = LinearLayoutManager(context)
        currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("HH:mm dd/MM/yy")
        bd.txtTime.text = sp.getString("updateTime","")
        bd.txtBuoi.text = getBuoi()
        viewModel.listData.observe(viewLifecycleOwner, Observer {
               if(it != null) {
                   adapter.submitList(it)
               }
        })
        viewModel.listExam.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if(!it.isEmpty()) {
                    adapterExam.submitList(it)
                    bd.trong.visibility = View.GONE
                }
            }

        })
        viewModel.listSemester.observe(viewLifecycleOwner, Observer {
            if(it != null) {
                val entries = ArrayList<Entry>()
                for(i in 0 until it.size){
                    entries.add(Entry(i.toFloat(), it[i].gpa4))
                }
                val hocki = ArrayList<String>()
                for(i in 0 until it.size){
                    val tmp = it[i].semester
                    var x = tmp.substring(0, 1) + "-" + tmp.substring(3, 5) + "/" + tmp.substring(7, 9)
                    hocki.add(x)
                }

                class dataValueFormatter : ValueFormatter() {
                    private val format = DecimalFormat("###,##0.0")

                    // override this for e.g. LineChart or ScatterChart
                    override fun getPointLabel(entry: Entry?): String {
                        return format.format(entry?.y)
                    }
                }

                val xAxis = bd.linechart.xAxis
                xAxis.granularity = 1f
                xAxis.setDrawLabels(false)
                val yAxisL = bd.linechart.axisLeft
                yAxisL.mAxisMaximum = 4.0f
                yAxisL.spaceTop = 0.1f
                yAxisL.spaceBottom = 0.1f
                yAxisL.granularity = 0.1f
                val dataset = LineDataSet(entries, "Điểm Tích Lũy")
                dataset.lineWidth = 4f
                dataset.valueFormatter = dataValueFormatter()
                dataset.valueTextSize = 8f
                dataset.cubicIntensity = 10f
                dataset.valueTextColor = Color.BLACK
                dataset.color = Color.CYAN
                val data: ArrayList<ILineDataSet> = ArrayList()
                data.add(dataset)
                val lineData: LineData = LineData(data)

                val des = Description()
                des.text =""

                val legend = bd.linechart.legend
                legend.form = Legend.LegendForm.CIRCLE
                legend.textSize = 13f
                legend.textColor = Color.RED


                bd.linechart.axisRight.isEnabled = false
                bd.linechart.data = lineData
                bd.linechart.setDrawBorders(true)
                bd.linechart.setBorderColor(Color.BLACK)
                bd.linechart.setBorderWidth(1f)
                bd.linechart.description = des
                bd.linechart.setScaleEnabled(false)
                bd.linechart.isDoubleTapToZoomEnabled = false
                bd.linechart.setTouchEnabled(true)
                bd.linechart.isHighlightPerDragEnabled = false
                bd.linechart.isHighlightPerTapEnabled = true
                bd.linechart.animateX(1000)
                val mv = CustomMarkerView(context, R.layout.axis_label, hocki,bd.linechart.width, bd.linechart.height)
                bd.linechart.marker = mv
                bd.linechart.invalidate()
            }
        })
        viewModel.bool.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.getData("220192020")
                currentTime = Calendar.getInstance().time
                val sdf = SimpleDateFormat("HH:mm dd/MM/yy")
                val str = "Cập nhật lúc: "+sdf.format(currentTime).toString()
                sp.edit().putString("updateTime",str).commit()
                txtTime.text = str
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                viewModel.bool.postValue(false)
                fresh.isRefreshing = false
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
        recycleViewDiem.setOnClickListener {
            findNavController().navigate(R.id.to_mark)
        }

        fresh.setWaveRGBColor(3,218,197)
        fresh.setColorSchemeColors(Color.WHITE)
        fresh.setShadowRadius(0)
        fresh.setOnRefreshListener {
            val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
            val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if(isConnected)
                viewModel.reload()
            else {
                Toast.makeText(context, "Không kết nối được!", Toast.LENGTH_SHORT).show()
                fresh.isRefreshing = false
            }

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


