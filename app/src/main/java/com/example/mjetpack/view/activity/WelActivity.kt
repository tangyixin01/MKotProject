package com.example.mjetpack.view.activity

import androidx.navigation.Navigation.findNavController
import com.example.mjetpack.R
import com.example.mjetpack.base.BaseActivity
import com.example.mjetpack.util.StatusBarUtil

class WelActivity : BaseActivity() {

    override fun getView(): Int {
        return R.layout.activity_wel
    }
    override fun initView() {

        StatusBarUtil.darkMode(this)
    }

    override fun onSupportNavigateUp() =
        findNavController(this, R.id.m_nav_h_fragment).navigateUp()


    override fun initData() {
    }

}
