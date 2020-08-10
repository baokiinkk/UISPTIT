package com.baokiin.uisptit.ui.info

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.uisptit.data.db.model.ExamTimetable
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.databinding.ItemExamBinding
import com.baokiin.uisptit.databinding.ItemMarkInforBinding

class AdapterExam() :ListAdapter<ExamTimetable,AdapterExam.ViewHodel>(ExamDIff()) {

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