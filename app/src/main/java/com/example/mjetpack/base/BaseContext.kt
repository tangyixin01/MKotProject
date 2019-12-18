package com.example.mjetpack.base

import android.content.Context

/**
 * author:chenjian
 * date: 2019-12-11   15:15
 **/
class BaseContext {
    companion object{
        var sApplication : Context ?= null
        fun setApplication(context: Context){
            sApplication = context
        }
        fun getApplication() : Context{
            return sApplication!!
        }
    }
}