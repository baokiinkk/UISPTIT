package com.baokiin.uis.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.koin.android.ext.android.inject

// Sử dụng BaseActivity vì có vài thuộc tính giống nhau không cần phải cài đặt lại
// Khi kế thừa Generics để là ViewDataBinding của Activity đó
// Không làm viewModel vì sẽ quản lý dữ liệu bằng Fragment

abstract class BaseActivity<VB: ViewDataBinding> : AppCompatActivity() {

    // Tự động tạo baseViewBinding sau khi implement getLayoutRes
    protected val baseViewBinding: VB by lazy {
        DataBindingUtil.setContentView<VB>(this, getLayoutRes())
    }

    // Return lại layout id
    @LayoutRes abstract fun getLayoutRes(): Int

    // Cài đặt của View ở đây để tránh việc làm rồi onCreate
    abstract fun setUpViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewBinding.root
        setUpViews()
    }
}