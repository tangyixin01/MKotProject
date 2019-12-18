package com.example.mjetpack.view.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import com.example.mjetpack.R
import com.example.mjetpack.base.BaseActivity
import com.example.mjetpack.util.PermissionsUtil

class SplashActivity : BaseActivity() {

    private var mHandler: WeakHandler? = null

    override fun getView(): Int = R.layout.activity_splash


    override fun initView() {


        mHandler = WeakHandler()


        PermissionsUtil.requestPermission(this, object : PermissionsUtil.PermissionCallBack {
            override fun failP() {
                start()
            }

            override fun hasP() {
                start()
            }
        }, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
    }

    override fun initData() {


    }
    fun start() {


        mHandler?.postDelayed(Runnable {
            goNextPage()
        }, 1500)


    }

    class WeakHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    private fun goNextPage() {
        val intent = Intent(this, WelActivity::class.java)
        startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()

        mHandler?.let {
            it.removeCallbacksAndMessages(null)
            mHandler = null
        }

    }
}
