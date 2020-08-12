package com.baokiin.uisptit.ui.info

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.uisptit.R
import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.databinding.ItemExamBinding
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF


class AdapterExam :ListAdapter<ExamTimetable,AdapterExam.ViewHodel>(ExamDIff()) {

    class ViewHodel(val binding:ItemExamBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        fun bind(item:ExamTimetable)
        {
            binding.data=item
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterExam.ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: AdapterExam.ViewHodel, position: Int) {
        holder.bind(getItem(position))
    }
}

class ExamDIff: DiffUtil.ItemCallback<ExamTimetable>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: ExamTimetable, newItem: ExamTimetable): Boolean { // cho máy biết 2 item khi nào giống
    return oldItem.tenMon == newItem.tenMon // dung
}

    override fun areContentsTheSame(oldItem: ExamTimetable, newItem: ExamTimetable): Boolean { // cho biết item khi nào cùng nội dung
        return oldItem == newItem
    }

}
class CustomMarkerView(context: Context?, layoutResource: Int, private val listLabel : MutableList<String>,val widthCard:Int, val heightCard:Int) :
    MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById<TextView>(R.id.tvContent)

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(
        e: Entry,
        highlight: Highlight?
    ) {
        //Log.d("tncnhan", e.describeContents().toString())
        tvContent.text = "Kì "+listLabel[e.x.toInt()] // set the entry-value as the display text
        super.refreshContent(e, highlight)
    }
//    override fun getOffset(): MPPointF? {
//        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
//    }
    override fun draw(canvas: Canvas, posx: Float, posy: Float) {
        // Check marker position and update offsets.
        var posx = posx
        var posy = posy
        val w = width
        val h = height
        if (posx + w/2 > widthCard) posx -= w
        else if (posx >= w/2) posx -= w/2

        if (posy + height > heightCard) posy -= 1.5f*h

        // translate to the correct position and draw
        canvas.translate(posx, posy)
        draw(canvas)
        canvas.translate(-posx, -posy)
    }
}