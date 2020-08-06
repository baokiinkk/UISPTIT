package com.baokiin.uisptit.ui.info
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.FragmentInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class InfoFragment : Fragment(){
    val viewModel: InfoViewModel by viewModel<InfoViewModel>()
    lateinit var sp: SharedPreferences
    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val cm: ConnectivityManager? = activity?.getSystemService(Context.CONNECTIVITY_SERVICE ) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        val bd: FragmentInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        sp = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        viewModel.getData("220192020")
        val adapter = AdapterMark(){

        }

        bd.recycleViewDiem.adapter = adapter
        bd.recycleViewDiem.layoutManager = LinearLayoutManager(context)
        viewModel.listData.observe(viewLifecycleOwner, Observer {
               it.let {
                   adapter.submitList(it)
               }
        })
        bd.cardTKB.setOnClickListener {
            findNavController().navigate(R.id.to_schedule)
        }
        bd.cardDiem.setOnClickListener {
            findNavController().navigate(R.id.to_mark)
        }
        bd.button.setOnClickListener {
            viewModel.deleteLogin()
            val ssp = sp.edit()
            ssp.clear()
            ssp.apply()
            findNavController().navigate(R.id.info_to_login)

        }

        bd.fresh.setWaveRGBColor(3,218,197)
        bd.fresh.setOnRefreshListener {
            bd.fresh.postDelayed(
                Runnable {
                    if(isConnected ==true) {
                        viewModel.reload()
                        viewModel.getData("220192020")
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        viewModel.getData("220192020")
                        Toast.makeText(context,"thiết bị đang offline,đã reload lại dữ liệu",Toast.LENGTH_SHORT).show()
                    }
                    bd.fresh.setRefreshing(false) }, 2000
            )
        }

        return bd.root
    }
    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { v, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().finish()
                true
            } else false
        }
    }

}