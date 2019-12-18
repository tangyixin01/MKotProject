package com.example.mjetpack.util

import android.app.Activity
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer


/**
 * author:chenjian
 * date: 2019-12-02   17:26
 **/

class PermissionsUtil {

    companion object {

        fun requestPermission(activity: Activity, callBack: PermissionCallBack, vararg permission: String) {

            RxPermissions(activity)
                .requestEachCombined(*permission)
                .subscribe(object : Consumer<Permission> {
                    override fun accept(p: Permission) {
                        if (p.granted) callBack.hasP() else callBack.failP()
                    }
                })


        }
    }



    interface PermissionCallBack {

        abstract fun hasP()
        abstract fun failP()
    }
}