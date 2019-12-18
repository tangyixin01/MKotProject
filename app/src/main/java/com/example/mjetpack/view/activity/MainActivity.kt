package com.example.mjetpack.view.activity

import android.view.View
import com.example.mjetpack.R
import com.example.mjetpack.base.BaseActivity
import com.example.mjetpack.util.StatusBarUtil
import com.example.mjetpack.util.joinToString
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_activity_layout.*


class MainActivity : BaseActivity() {

    override fun getView(): Int {
        return R.layout.activity_main
    }
    override fun initView() {

        tv_hello.text = StatusBarUtil.getStatusBarHeight(this).toString()
        tv_hello.setOnClickListener {

            showLoading()
        }

        hview.visibility = View.VISIBLE

        joinToString()


    }

    override fun initData() {
    }



}
