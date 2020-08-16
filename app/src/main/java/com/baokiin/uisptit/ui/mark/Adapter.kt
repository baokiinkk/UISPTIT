package com.baokiin.uisptit.ui.mark
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.uisptit.data.db.model.ListMark
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.databinding.ItemMarkBinding
import com.baokiin.uisptit.databinding.ItemMarkInforBinding
import com.baokiin.uisptit.ui.info.AdapterMark
import kotlinx.android.synthetic.main.item_mark.view.*


class Adapter() :ListAdapter<ListMark,Adapter.ViewHodel>(MyDIffMark()) {

    class ViewHodel(val binding:ItemMarkBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemMarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        fun bind(item:ListMark)
        {
            binding.viewmodel=item
            val adapter = AdapterMark(){}
            itemView.recycleViewDiem.adapter = adapter
            itemView.recycleViewDiem.layoutManager = LinearLayoutManager(itemView.context)
            adapter.submitList(item.list)

            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHodel, position: Int) {
        holder.bind(getItem(position))
    }
}

class MyDIffMark: DiffUtil.ItemCallback<ListMark>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: ListMark, newItem: ListMark): Boolean { // cho máy biết 2 item khi nào giống
    return oldItem.hocki == newItem.hocki // dung
}


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ListMark, newItem: ListMark): Boolean { // cho biết item khi nào cùng nội dung
        return oldItem == newItem
    }

}