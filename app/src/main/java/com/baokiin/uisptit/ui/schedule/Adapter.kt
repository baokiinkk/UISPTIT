package com.baokiin.uisptit.ui.schedule
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baokiin.uisptit.data.db.model.ListMark
import com.baokiin.uisptit.data.db.model.ListTableTime
import com.baokiin.uisptit.databinding.ItemMarkBinding
import com.baokiin.uisptit.databinding.ItemMarkInforBinding
import com.baokiin.uisptit.databinding.ItemScheduleBinding
import com.baokiin.uisptit.ui.info.AdapterMark
import kotlinx.android.synthetic.main.item_mark.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*


class Adapter(val isClick:(Int)->Unit) :ListAdapter<ListTableTime,Adapter.ViewHodel>(MyDIffMark()) {

    class ViewHodel(val binding:ItemScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHodel {
                val binding =
                    ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ViewHodel(binding)
            }
        }
        fun bind(item:ListTableTime,isClick: ((Int) -> Unit)? = null)
        {
            binding.data=item
            for(i in item.mutableList){
                if(i.thu.equals("2")){
                    if(i.buoi.equals("0")) {
                        itemView.txtTenT2S.text = i.tenMon
                        itemView.txtPT2S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT2C.text = i.tenMon
                        itemView.txtPT2C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu.equals("3")){
                    if(i.buoi.equals("0")) {
                        itemView.txtTenT3S.text = i.tenMon
                        itemView.txtPT3S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT3C.text = i.tenMon
                        itemView.txtPT3C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu.equals("4")){
                    if(i.buoi.equals("0")) {
                        itemView.txtTenT4S.text = i.tenMon
                        itemView.txtPT4S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT4C.text = i.tenMon
                        itemView.txtPT4C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu.equals("5")){
                    if(i.buoi.equals("0")) {
                        itemView.txtTenT5S.text = i.tenMon
                        itemView.txtPT5S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT5C.text = i.tenMon
                        itemView.txtPT5C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu.equals("6")){
                    if(i.buoi.equals("0")) {
                        itemView.txtTenT6S.text = i.tenMon
                        itemView.txtPT6S.text = "Phòng: "+i.phong
                    }
                    else{
                        itemView.txtTenT6C.text = i.tenMon
                        itemView.txtPT6C.text = "Phòng: "+i.phong
                    }
                }
                if(i.thu.equals("7")){
                    if(i.buoi.equals("0")) {
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

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHodel {
        return ViewHodel.from(parent)
    }

    override fun onBindViewHolder(holder: Adapter.ViewHodel, position: Int) {
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