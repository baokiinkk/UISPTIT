package com.baokiin.uisptit.ui.info
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        viewModel.getData("220192020")
        viewModel.get()
        val adapter = AdapterMark(){

        }

        bd.recycleViewDiem.adapter = adapter
        bd.recycleViewDiem.layoutManager = LinearLayoutManager(context)
        viewModel.listData.observe(viewLifecycleOwner, Observer {
               it.let {
                   adapter.submitList(it)
               }
        })
        viewModel.title.observe(viewLifecycleOwner, Observer {
            it.let {
                bd.endName.text = it
            }
        })
        bd.cardTKB.setOnClickListener {
            findNavController().navigate(R.id.to_schedule)
        }
        bd.cardDiem.setOnClickListener {
            findNavController().navigate(R.id.to_mark)
        }
        bd.button.setOnClickListener {
            findNavController().navigate(R.id.info_to_login)
        }
        return bd.root
    }

}