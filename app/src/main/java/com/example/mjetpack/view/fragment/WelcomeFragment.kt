package com.example.mjetpack.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.mjetpack.R
import com.example.mjetpack.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * author:chenjian
 * date: 2019-11-28   11:26
 **/

class WelcomeFragment : BaseFragment(), View.OnClickListener {


    override fun getLayoutId(): Int = R.layout.fragment_welcome

    override fun initView() {



        tv_login.setOnTouchListener { v, event ->

            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    startScaleBAnim(v)
                    v.performClick()
                }
                MotionEvent.ACTION_DOWN -> {
                    startScaleSAnim(v)
                }
            }
            return@setOnTouchListener true
        }

        tv_login.setOnClickListener(this)
        tv_register.setOnClickListener(this)
    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.tv_login -> {

                findNavController().navigate(R.id.loginFragment, null, goFragmentAnim())

            }
            R.id.tv_register -> {
                findNavController().navigate(R.id.registerFragment, null, goFragmentAnim())

            }
            else -> return
        }
    }

}