@file:Suppress("DEPRECATION")

package com.baokiin.uisptit.ui.info

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.data.db.model.TimeTable
import com.baokiin.uisptit.databinding.FragmentInfoBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.android.synthetic.main.fragment_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS"
)
class InfoFragment : Fragment(){
    private val viewModel: InfoViewModel by viewModel()
    private lateinit var currentTime: Date
    private lateinit var sp:SharedPreferences
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        val bd: FragmentInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        viewModel.getData("")
        val adapterExam = AdapterExam{
            findNavController().navigate(R.id.to_exam)
        }
        sp = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        val adapter = AdapterMark {
            findNavController().navigate(R.id.to_mark)
        }

        bd.recycleView.adapter = adapter
        bd.recycleView.layoutManager = LinearLayoutManager(context)


        bd.recycleExam.adapter = adapterExam
        bd.recycleExam.layoutManager = LinearLayoutManager(context)
        currentTime = Calendar.getInstance().time
        bd.txtTime.text = sp.getString("updateTime","")
        bd.txtBuoi.text = getBuoi()
        viewModel.listData.observe(viewLifecycleOwner, Observer {
               if(it != null) {
                   val tmp:MutableList<Mark> = mutableListOf()
                   val str = it[it.size - 1].semester
                   tmp.add(it[it.size - 1])
                   for (i in it.size-2 downTo 0) {
                       if (it[i].semester == str) {
                           tmp.add(it[i])
                       }
                       else break
                   }
                   adapter.submitList(tmp)
                   fresh.isRefreshing = false
               }
        })
        viewModel.listExam.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if(it.isNotEmpty()) {
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
                    val x = tmp.substring(0, 1) + "-" + tmp.substring(3, 5) + "/" + tmp.substring(7, 9)
                    hocki.add(x)
                }

                class DataValueFormatter : ValueFormatter() {
                    private val format = DecimalFormat("###,##0.0")

                    // override this for e.g. LineChart or ScatterChart
                    override fun getPointLabel(entry: Entry?): String {
                        return format.format(entry?.y)
                    }
                }

                val xAxis = bd.linechart.xAxis
                xAxis.granularity = 1f
                xAxis.setDrawLabels(false)
                xAxis.setDrawGridLines(false)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                val yAxisL = bd.linechart.axisLeft
                yAxisL.setDrawGridLines(false)
                yAxisL.mAxisMaximum = 4.0f
                yAxisL.granularity = 0.1f
                val dataset = LineDataSet(entries, "Điểm Tích Lũy")
                dataset.setDrawFilled(true)
                dataset.fillColor = Color.rgb(99, 80, 200)
                dataset.lineWidth = 3f
                dataset.valueFormatter = DataValueFormatter()
                dataset.valueTextSize = 8f
                dataset.circleRadius = 6f
                dataset.circleHoleRadius = 3f
                dataset.cubicIntensity = 10f
                dataset.valueTextColor = Color.BLACK
                dataset.color = Color.rgb(74, 146, 246)
                dataset.setCircleColor(Color.rgb(74, 146, 246))

                dataset.fillFormatter =
                    IFillFormatter { _, _ -> bd.linechart.axisLeft.axisMinimum }

                // set color of filled area

                // set color of filled area
                if (Utils.getSDKInt() >= 18) {
                    // drawables only supported on api level 18 and above
                    val drawable: Drawable? =
                        context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.fade_graph) }
                    dataset.fillDrawable = drawable
                } else {
                    dataset.fillColor = Color.rgb(99, 80, 200)
                }

                val data: ArrayList<ILineDataSet> = ArrayList()
                data.add(dataset)
                val lineData = LineData(data)
                val des = Description()
                des.text =""

                val legend = bd.linechart.legend
                legend.isEnabled = false


                bd.linechart.axisRight.isEnabled = false
                bd.linechart.data = lineData
                bd.linechart.setDrawBorders(false)
//                bd.linechart.setBorderColor(Color.BLACK)
//                bd.linechart.setBorderWidth(1f)
                bd.linechart.description = des
                bd.linechart.setScaleEnabled(false)
                bd.linechart.isDoubleTapToZoomEnabled = false
                bd.linechart.setTouchEnabled(true)
                bd.linechart.isHighlightPerDragEnabled = false
                bd.linechart.isHighlightPerTapEnabled = true
                val mv = CustomMarkerView(context, R.layout.axis_label, hocki,bd.linechart.width, bd.linechart.height)
                bd.linechart.marker = mv
                bd.linechart.invalidate()
            }
        })
        viewModel.bool.observe(viewLifecycleOwner, Observer {
            if(it == true){
                viewModel.getData("")
                currentTime = Calendar.getInstance().time
                val sdf = SimpleDateFormat("HH:mm dd/MM/yy")
                val str = "Cập nhật lúc: "+sdf.format(currentTime).toString()
                sp.edit().putString("updateTime",str).apply()
                txtTime.text = str
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
                viewModel.bool.postValue(false)
            }
        })
        viewModel.dataTimeTableTime.observe(viewLifecycleOwner, Observer {
            it?.let {
                val dateCurrent = Date()
                var tmp = getTKBNgay(dateCurrent,it)
                bd.txtP1.text  = tmp[0][0]
                if(tmp[0].size > 1){
                    bd.txtMon1.text = tmp[0][1]
                }
                bd.txtP2.text = tmp[1][0]
                if(tmp[1].size > 1)
                    bd.txtMon2.text = tmp[1][1]

                dateCurrent.time += 1000*60*60*24
                tmp = getTKBNgay(dateCurrent,it)
                bd.txtP3.text  = tmp[0][0]
                if(tmp[0].size > 1){
                    bd.txtMon3.text = tmp[0][1]
                }
                bd.txtP4.text = tmp[1][0]
                if(tmp[1].size > 1)
                    bd.txtMon4.text = tmp[1][1]

            }
        })
        return bd.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)
        tableLayout.setOnClickListener {
            findNavController().navigate(R.id.to_schedule)
        }
        cardDiem.setOnClickListener {
            findNavController().navigate(R.id.to_mark)
        }
        cardLichThi.setOnClickListener {
            findNavController().navigate(R.id.to_exam)
        }
        btnOption.setOnClickListener {
            findNavController().navigate(R.id.to_option)
        }
        frame.setOnClickListener {
            findNavController().navigate(R.id.to_exam)
        }
        trong.setOnClickListener {
            findNavController().navigate(R.id.to_exam)
        }


        fresh.setWaveRGBColor(91, 86, 243)
        fresh.setColorSchemeColors(Color.WHITE)
        fresh.setShadowRadius(0)
        fresh.setOnRefreshListener {
            val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
            val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if(isConnected) {
                viewModel.reload()
            }
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
        requireView().setOnKeyListener { _, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().finish()
                true
            } else false
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getBuoi() :String{
        val sdf = SimpleDateFormat("HH")
        val hour = sdf.format(currentTime).toInt()
        return if(hour < 12 )
            "Chúc ngày mới tốt lành!"
        else if(hour < 15)
            "Chúc buổi trưa vui vẻ!"
        else if(hour < 19)
            "Chúc buổi chiều mát mẻ!"
        else
            "Chúc buổi tối như cc!"
    }


    private fun getTKBNgay(day : Date, tkb : MutableList<TimeTable>) : MutableList<MutableList<String>>{
        val res = mutableListOf<MutableList<String>>()
        var row = mutableListOf<String>()
        row.add("Trống")
        res.add(row)
        res.add(row)
        for (i in tkb){
            val dates = getDate(i.tuan)
            if (day.before(dates[1]) && (day.after(dates[0]) || day == dates[0])){
                if (((day.time-dates[0].time) / (1000*60*60*24)+2) == i.thu.toLong()){
                    row = mutableListOf()
                    row.add(i.phong)
                    row.add(i.tenMon)
                    res[i.buoi.toInt()] = row
                }
            }
        }
        return res
    }
    private fun getDate(stringDate : String) : MutableList<Date>{
        var startDate = stringDate.substring(2,10)
        startDate = startDate.substring(0,2)+"/"+startDate.substring(2,4)+"/"+startDate.substring(4,8)
        var endDate = stringDate.substring(10, 18)
        endDate = endDate.substring(0,2)+"/"+endDate.substring(2,4)+"/"+endDate.substring(4,8)
        val res = mutableListOf<Date>()
        res.add(toDate(startDate))
        res.add(toDate(endDate))
        return res
    }

    @SuppressLint("SimpleDateFormat")
    fun toDate(str:String): Date {
        val sdf =
            SimpleDateFormat("dd/MM/yyyy")
        return sdf.parse(str)
    }



}


