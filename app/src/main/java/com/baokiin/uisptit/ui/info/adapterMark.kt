package com.baokiin.uisptit.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.uisptit.data.db.model.Mark
import com.baokiin.uisptit.databinding.ItemMarkInforBinding

class adapterMark(val setBaseClick:((Int)->Unit)) : ListAdapter<Mark, adapterMark.ViewHodel>(MyDIff()) {

        class ViewHodel(val binding:ItemMarkInforBinding) : RecyclerView.ViewHolder(binding.root) {
            companion object {
                fun from(parent: ViewGroup): ViewHodel {
                    val binding =
                        ItemMarkInforBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    return ViewHodel(binding)
                }
            }
            fun bind(item:Mark,baseClick:((Int)->Unit)?=null)
            {
                binding.data=item
                binding.executePendingBindings()
                baseClick?.let {click->
                    itemView.setOnClickListener{
                        click(adapterPosition)
                    }
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterMark.ViewHodel {
            return ViewHodel.from(parent)
        }

        override fun onBindViewHolder(holder: adapterMark.ViewHodel, position: Int) {
            holder.bind(getItem(position),setBaseClick)
        }
    }

    class MyDIff: DiffUtil.ItemCallback<Mark>() {// cung cấp thông tin về cách xác định phần
    override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean { // cho máy biết 2 item khi nào giống
        return oldItem.id == newItem.id // dung
    }

        override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean { // cho biết item khi nào cùng nội dung
            return oldItem == newItem
        }

    }