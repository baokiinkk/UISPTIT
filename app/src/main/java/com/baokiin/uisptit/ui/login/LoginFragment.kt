package com.baokiin.uisptit.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.baokiin.uisptit.R
import com.baokiin.uisptit.databinding.LoginFragmentBinding
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class LoginFragment : Fragment(){
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var sp:SharedPreferences
    @SuppressLint("CommitPrefEdits", "SetTextI18n")
    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("quocbaokiin","login")
        val bd: LoginFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        bd.lifecycleOwner = this
        bd.viewmodel = viewModel
        sp = requireActivity().getSharedPreferences("Login", Context.MODE_PRIVATE)
        bd.spin.setIndeterminateDrawable(returnSprite())
        viewModel.bool.observe(viewLifecycleOwner, Observer {
                if(it == true){
                    sp.edit().putBoolean("login",true).apply()
                    findNavController().navigate(R.id.login_to_infor)
                }
                else if(it == false){
                    spin_kit.visibility = View.GONE
                    bd.errorTv.text = "Sai tài khoản hoặc mật khẩu"
                }

        })
        return bd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       login_button.setOnClickListener {
            viewModel.check(username_et.editText?.text.toString(),password_et.editText?.text.toString())
           spin_kit.visibility = View.VISIBLE
           hideKeyboard()
       }

        val span = SpannableString("Bằng cách Đăng Nhập, bạn đồng ý với Chính sách Quyền riêng tư của chúng tôi.")
        span.setSpan(RelativeSizeSpan(1.0f),36,61,0)
        span.setSpan(UnderlineSpan(),36,61,0)
        span.setSpan(StyleSpan(Typeface.BOLD),36,61,0)
        span.setSpan(object :ClickableSpan(){
            override fun onClick(widget: View) {
                var intent: Intent = Intent(Intent.ACTION_VIEW)
                intent.data= Uri.parse("https://itmc-ptithcm.github.io/UISpolicy.html")
                startActivity(intent)
            }
        },36,61,0)
        textView4.movementMethod = LinkMovementMethod.getInstance()
        textView4.text = span
    }

    override fun onResume() {
        super.onResume()
        if (view == null) {
            return
        }
        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { _, keyCode, event ->
            if (event.action === KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().finish()
                true
            } else false
        }
    }
    private fun returnSprite() : Sprite {
        val list:MutableList<Sprite> = mutableListOf()
        list.add(DoubleBounce())
        list.add(ChasingDots())
        list.add(Wave())
        list.add(ThreeBounce())
        list.add(Circle())
        val rnds = (0 .. 4).random()
        return list[rnds]
    }
    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
