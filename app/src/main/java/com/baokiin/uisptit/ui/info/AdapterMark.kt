package com.baokiin.uisptit.ui.info

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.databinding.ItemMarkInforBinding

class AdapterMark(val click:() -> Unit  ) :ListAdapter<Mark,AdapterMark.ViewHodel>(MyDIff()) {

    class ViewHodel(val binding:ItemMarkInforBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemMarkInforBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        fun bind(item:Mark,click: (() -> Unit)? = null)
        {
            binding.data=item
            binding.executePendingBindings()
            click?.let {click->
                itemView.setOnClickListener {
                    click()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMark.ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: AdapterMark.ViewHodel, position: Int) {
        holder.bind(getItem(position),click)
    }
}

class MyDIff: DiffUtil.ItemCallback<Mark>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean { // cho máy biết 2 item khi nào giống
    return oldItem.objectName == newItem.objectName // dung
}

    override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean { // cho biết item khi nào cùng nội dung
        return oldItem == newItem
    }

}