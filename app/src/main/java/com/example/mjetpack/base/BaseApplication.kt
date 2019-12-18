package com.example.mjetpack.base

import android.app.Application

/**
 * author:chenjian
 * date: 2019-12-11   15:20
 **/
 open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSApplication()
    }
    private fun initSApplication(){
        BaseContext.setApplication(applicationContext)
    }
}