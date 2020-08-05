package com.baokiin.uisptit.ui.info
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
class InfoFragment : Fragment(){
    val viewModel: InfoViewModel by viewModel<InfoViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bd: FragmentInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        viewModel.getData("120192020")

        val adapter = AdapterMark(){

        }
        Toast.makeText(context,adapter.itemCount.toString(),Toast.LENGTH_LONG).show()
        bd.recycleViewDiem.adapter = adapter
        viewModel.listData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                for(i in it)
                    Log.d("quocbaokiin",i.toString())
                adapter.submitList(it)
            }
        })
        bd.cardTKB.setOnClickListener {
            findNavController().navigate(R.id.to_schedule)
        }
        bd.button.setOnClickListener {
            findNavController().navigate(R.id.info_to_login)
        }
        return bd.root
    }
}