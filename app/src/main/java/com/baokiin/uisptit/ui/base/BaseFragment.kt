package com.baokiin.uisptit.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

// Sử dụng BaseFragment vì có vài thuộc tính giống nhau không cần cài đặt lại

abstract class BaseFragment<VM : ViewModel, VB: ViewDataBinding> :Fragment() {

    // Tạo ra baseBinding để sử dụng
    protected lateinit var baseBinding: VB
    // Tạo baseViewModel bằng các sử dụng Kclass (cái này hơi mông lung)
    protected  val baseViewModel: VM by viewModel<VM>(viewModelClass())
    // // Return lại layout id
    @LayoutRes abstract fun getLayoutRes(): Int
    // Cài đặt của View ở đây để tránh việc làm rồi onCreate
    abstract fun setUpViews()

    // Inflate baseBinding và return root (view) với id đã được cài đặt sẵn
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseBinding = DataBindingUtil.inflate(
            inflater,
            getLayoutRes(),
            container,
            false
        )
        return baseBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    // Tạo Kclass để sử dụng
    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<VM> =
        ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>).kotlin
}