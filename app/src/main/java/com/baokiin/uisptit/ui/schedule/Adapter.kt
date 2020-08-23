package com.baokiin.uisptit.ui.schedule
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.uisptit.data.db.model.ListTableTime
import com.baokiin.uisptit.databinding.ItemScheduleBinding
import kotlinx.android.synthetic.main.item_schedule.view.*


class Adapter(private val isClick:(Int)->Unit) :ListAdapter<ListTableTime,Adapter.ViewHodel>(MyDIffMark()) {

    class ViewHodel(val binding:ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(item:ListTableTime, isClick: ((Int) -> Unit)? = null)
        {
            binding.data=item
            defaultItem()
            for(i in item.mutableList){
                if(i.thu == "2"){
                    if(i.buoi == "0") {
                        itemView.txtTenT2S.text = i.tenMon
                        itemView.txtPT2S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT2C.text = i.tenMon
                        itemView.txtPT2C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu == "3"){
                    if(i.buoi == "0") {
                        itemView.txtTenT3S.text = i.tenMon
                        itemView.txtPT3S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT3C.text = i.tenMon
                        itemView.txtPT3C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu == "4"){
                    if(i.buoi == "0") {
                        itemView.txtTenT4S.text = i.tenMon
                        itemView.txtPT4S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT4C.text = i.tenMon
                        itemView.txtPT4C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu == "5"){
                    if(i.buoi == "0") {
                        itemView.txtTenT5S.text = i.tenMon
                        itemView.txtPT5S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT5C.text = i.tenMon
                        itemView.txtPT5C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu == "6"){
                    if(i.buoi == "0") {
                        itemView.txtTenT6S.text = i.tenMon
                        itemView.txtPT6S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT6C.text = i.tenMon
                        itemView.txtPT6C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu == "7"){
                    if(i.buoi == "0") {
                        itemView.txtTenT7S.text = i.tenMon
                        itemView.txtPT7S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT7C.text = i.tenMon
                        itemView.txtPT7C.text = "Phòng: "+i.phong
                    }
                }
            }
            isClick?.let {
                isClick(adapterPosition)
            }
            binding.executePendingBindings()

        }
        fun defaultItem(){
            itemView.txtPT2C.text = ""
            itemView.txtPT2S.text = ""
            itemView.txtPT3C.text = ""
            itemView.txtPT3S.text = ""
            itemView.txtPT4C.text = ""
            itemView.txtPT4S.text = ""
            itemView.txtPT4C.text = ""
            itemView.txtPT4S.text = ""
            itemView.txtPT5C.text = ""
            itemView.txtPT5S.text = ""
            itemView.txtPT6C.text = ""
            itemView.txtPT6S.text = ""
            itemView.txtPT7C.text = ""
            itemView.txtPT7S.text = ""
            itemView.txtPT8C.text = ""
            itemView.txtPT8S.text = ""

            itemView.txtTenT2C.text = ""
            itemView.txtTenT2S.text = ""
            itemView.txtTenT3C.text = ""
            itemView.txtTenT3S.text = ""
            itemView.txtTenT4C.text = ""
            itemView.txtTenT4S.text = ""
            itemView.txtTenT4C.text = ""
            itemView.txtTenT4S.text = ""
            itemView.txtTenT5C.text = ""
            itemView.txtTenT5S.text = ""
            itemView.txtTenT6C.text = ""
            itemView.txtTenT6S.text = ""
            itemView.txtTenT7C.text = ""
            itemView.txtTenT7S.text = ""
            itemView.txtTenT8C.text = ""
            itemView.txtTenT8S.text = ""

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHodel, position: Int) {
        holder.bind(getItem(position),isClick)
    }
}

class MyDIffMark: DiffUtil.ItemCallback<ListTableTime>() {// cung cấp thông tin về cách xác định phần
override fun areItemsTheSame(oldItem: ListTableTime, newItem: ListTableTime): Boolean { // cho máy biết 2 item khi nào giống
    return oldItem.title == newItem.title // dung
}


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: ListTableTime, newItem: ListTableTime): Boolean { // cho biết item khi nào cùng nội dung
        return oldItem == newItem
    }

}