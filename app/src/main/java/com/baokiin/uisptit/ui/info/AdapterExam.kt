package com.baokiin.uisptit.ui.info

import android.annotation.SuppressLint
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


class AdapterExam(private val onClick:()->Unit) :ListAdapter<ExamTimetable,AdapterExam.ViewHodel>(ExamDIff()) {

    class ViewHodel(val binding:ItemExamBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemExamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        fun bind(item:ExamTimetable,onClick: (() -> Unit)? = null)
        {
            binding.data=item
            onClick?.let {
                itemView.setOnClickListener {
                    onClick()
                }
            }
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHodel, position: Int) {
        holder.bind(getItem(position),onClick)
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
@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context?, layoutResource: Int, private val listLabel : MutableList<String>, private val widthCard:Int, private val heightCard:Int) :
    MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint("SetTextI18n")
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
    override fun draw(canvas: Canvas, x: Float, y: Float) {
        // Check marker position and update offsets.
        var posx = x
        var posy = y
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